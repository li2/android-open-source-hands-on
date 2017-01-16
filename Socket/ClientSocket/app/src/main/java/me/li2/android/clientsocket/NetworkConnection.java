package me.li2.android.clientsocket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Socket connection manager, is used to:
 * (1) Connect to server;
 * (2) Disconnect to server;
 * (3) Send packets to server;
 * (4) Receive packets from server;
 *
 * Created by weiyi.li on 18/12/2016.
 * http://li2.me
 */

public class NetworkConnection {
    private static final String TAG = "NetworkConnection";

    private Context mContext;
    private Handler mUIHandler;
    private Socket mClientSocket;
    private DataInputStream mDataInputStream;
    private DataOutputStream mDataOutputStream;
    private Handler mSendHandler;
    private boolean mIsConnected;
    private ConnectionListener mConnectionListener;

    public interface ConnectionListener {
        void onConnected(final boolean isConnected, final String disconnectReason);
        void onDataReceived(final String data);
        void onDataSent(final String data, final boolean succeeded);
    }

    public NetworkConnection(Context context, final ConnectionListener listener) {
        mContext = context;
        mConnectionListener = listener;

        // init send thread handler
        HandlerThread sendHandlerThread;
        sendHandlerThread = new HandlerThread("SendHandlerThread");
        sendHandlerThread.start();
        mSendHandler = new Handler(sendHandlerThread.getLooper());

        // UI handler
        mUIHandler = new Handler(mContext.getMainLooper());
    }

    private Thread mConnectThread;
    /**
     * Connect to socket
     * @param serverIp the server ip
     * @param serverPort the server port
     */
    public void connect(final String serverIp, final int serverPort) {
        if (!isAvailable()) {
            notifyConnected(false, "Network unavailable");
            return;
        }

        if (mConnectThread == null) {
            mConnectThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!openSocket(serverIp, serverPort)) {
                        notifyConnected(false, "Open server socket failed");
                        // 也要在这里置空，否则第一次打开失败后，第二次打开就不会执行这段代码
                        mConnectThread = null;
                        return;
                    }
                    notifyConnected(true, "");
                    mConnectThread = null;
                    startReceiveThread();
                }
            });
            mConnectThread.setName("NetworkConnectThread");
            mConnectThread.start();
        }
    }

    private Thread mDisConnectThread;
    /**
     * Disconnect to server
     */
    public void disconnect(final String disconnectReason) {
        Log.d(TAG, "disconnect: " + disconnectReason);
        if (mConnectThread != null) {
            try {
                mConnectThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (mDisConnectThread == null) {
            mDisConnectThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    closeSocket();
//                    stopReceiveThread();
                    notifyConnected(false, disconnectReason);
                    mDisConnectThread = null;
                    Log.d(TAG, "disconnect succeed");
                }
            });
            mDisConnectThread.setName("NetworkDisConnectThread");
            mDisConnectThread.start();
        } else {
            Log.d(TAG, "already in disconnect operation");
        }
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    private boolean openSocket(String serverIp, int serverPort) {
        if (mClientSocket != null && mDataInputStream != null && mDataOutputStream != null) {
            Log.d(TAG, "Server already connected");
            return true;
        }

        try {
            Log.d(TAG, "Open socket " + serverIp + ":" + serverPort);
            mClientSocket = new Socket(serverIp, serverPort);
            mClientSocket.setKeepAlive(true);
            mDataInputStream = new DataInputStream(mClientSocket.getInputStream());
            mDataOutputStream = new DataOutputStream(mClientSocket.getOutputStream());
        } catch (IOException e) {
            Log.e(TAG, "Failed to connect server, exception: " + e);
            // 在图书馆遇到下述问题，但在公司的网络和家庭网络里问题就没了：
            // Failed to open socket, exception: java.net.ConnectException:
            // failed to connect to /192.168.12.183 (port 3000): connect failed: EHOSTUNREACH (No route to host)
            // client ip: 192.168.15.169
            return false;
        }
        Log.d(TAG, "Succeed to connect server");
        return true;
    }

    private void closeSocket() {
        Log.d(TAG, "closeSocket");
        closeStreams();

        if (mClientSocket != null) {
            try {
                mClientSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close socket exception: " + e.getMessage());
            }
        }
    }

    private void closeStreams() {
        Log.d(TAG, "closeStreams");
        try {
            if (mDataInputStream != null) {
                mDataInputStream.close();
                mDataInputStream = null;
            }
            if (mDataOutputStream != null) {
                mDataOutputStream.close();
                mDataOutputStream = null;
            }
        } catch (IOException e) {
            Log.e(TAG, "close streams exception: " + e.getMessage());
        }
    }

    private boolean isAvailable() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            return true;
        } else {
            Log.e(TAG, "Network is not available!");
            return false;
        }
    }

    /**
     * Send packet to server
     * @param data the packet to be sent
     */
    public void sendData(final String data) {
        mSendHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mDataOutputStream.writeUTF(data);
                    mDataOutputStream.flush();
                    notifyDataSent(data, true);
                } catch (IOException e) {
                    Log.e(TAG, "Write data exception: " + e.getMessage());
                    notifyDataSent(data, false);
                }
            }
        });
    }


    // ------------------------ Receive Thread --------------------------------

    /**
     * A boolean flag to mark the receive thread is interrupted or not.
     * 连接一旦中断，必须终止 receive thread，这个 flag 就是用来退出 while loop，
     * 对 disconnect 清理工作很重要，如果仅用 isConnected 做判断，很容易因为清理的顺序导致无法退出线程。
     */
    private boolean mReceiveThreadInterrupted = false;
    private Thread mReceiveThread;

    private void startReceiveThread() {
        if (mReceiveThread != null) {
            Log.d(TAG, "Receive thread already start");
            return;
        }
        if (mDataOutputStream == null) {
            Log.d(TAG, "mDataOutputStream not opened");
            return;
        }

        mReceiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Receive thread is running");
                try {
                    while (isConnected() && !mReceiveThreadInterrupted) {
                        String data = mDataInputStream.readUTF();
                        notifyDataReceived(data);
                    }
                } catch (IOException e) {
                    // read 是阻塞操作，一旦与 Server 失去连接，read exception 就会被抛出，所以，
                    // 可以仅在 read 线程判断 disconnect，把 write exception 当做普通异常处理。
                    Log.e(TAG, "Read data exception: " + e.getMessage());
                    mReceiveThread = null;
                    disconnect("Read exception then disconnect");
                }
            }
        });
        mReceiveThreadInterrupted = false;
        mReceiveThread.setName("ReceiveThread");
        mReceiveThread.start();
    }

    // Socket should be closed firstly, to make any threads blocked in socket stream operations to be unblocked.
    // Otherwise, ReceiveThread.join() will block the current thread, and the disconnect operation will be blocked.
    // http://stackoverflow.com/a/4426050/2722270
    // http://stackoverflow.com/a/1024501/2722270
    //
    // however, if close socket firstly, the receive thread will be interrupted by io exception, and
    // the method stopReceiveThread become to be useless.

    private void stopReceiveThread() {
        Log.d(TAG, "stopReceiveThread");
        if (mReceiveThread != null) {
            try {
                mReceiveThreadInterrupted = true;
                mReceiveThread.join();
            } catch (InterruptedException e) {
                Log.e(TAG, "Stop received thread exception: " + e.getMessage());
            }
            mReceiveThread = null;
        }
    }

    private void notifyConnected(final boolean isConnected, final String disconnectReason) {
        Log.d(TAG, "notifyConnected " + isConnected + ", " + disconnectReason);
        mIsConnected = isConnected;
        // make callback run on UI thread
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mConnectionListener != null) {
                    mConnectionListener.onConnected(isConnected, disconnectReason);
                }
            }
        });
    }

    private void notifyDataSent(final String data, final boolean succeeded) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mConnectionListener != null) {
                    mConnectionListener.onDataSent(data, succeeded);
                }
            }
        });
    }

    private void notifyDataReceived(final String data) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mConnectionListener != null) {
                    mConnectionListener.onDataReceived(data);
                }
            }
        });
    }
}
