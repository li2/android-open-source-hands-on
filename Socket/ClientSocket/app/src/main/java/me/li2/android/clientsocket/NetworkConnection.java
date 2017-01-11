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
    private static final String SERVER_IP = "";
    private static final int SERVER_PORT = 8080;

    private Context mContext;
    private Handler mUIHandler;
    private Socket mClientSocket;
    private DataInputStream mDataInputStream;
    private DataOutputStream mDataOutputStream;
    private Thread mConnectionThread;
    private Handler mSendHandler;
    private boolean mIsConnected;
    private ConnectionListener mConnectionListener;

    public interface ConnectionListener {
        void onConnected(final boolean isConnected);
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

    /**
     * Connect to socket
     * @param serverIp the server ip
     * @param serverPort the server port
     */
    public void connect(final String serverIp, final int serverPort) {
        if (!isAvailable()) {
            notifyConnected(false);
            return;
        }

        if (mConnectionThread == null) {
            mConnectionThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!openSocket(serverIp, serverPort)) {
                        notifyConnected(false);
                        return;
                    }
                    notifyConnected(true);
                    mConnectionThread = null;
                }
            });
            mConnectionThread.setName("NetworkConnectThread");
            mConnectionThread.start();
        }
    }

    /**
     * Disconnect to server
     */
    public void disconnect() {
        if (mConnectionThread != null) {
            try {
                mConnectionThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mConnectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                closeSocket();
                notifyConnected(false);
                mConnectionThread = null;
            }
        });
        mConnectionThread.setName("NetworkDisconnectThread");
        mConnectionThread.start();
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    private boolean openSocket(String serverIp, int serverPort) {
        if (mClientSocket != null && mDataInputStream != null && mDataOutputStream != null) {
            return true;
        }

        try {
            Log.d(TAG, "open socket " + serverIp + ":" + serverPort);
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

        Log.i(TAG, "Succeed to connect to server: " + SERVER_IP + ":" + SERVER_PORT);
        return true;
    }

    private void closeSocket() {
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
                    // TODO 如果 Server 端 read exception 被当做是 disconnect，
                    // 那么 client 端 write exception 呢？ （read reception 时也做了重连动作，但 write 没有处理！）
                    Log.e(TAG, "Write data exception: " + e.getMessage());
                    notifyDataSent(data, false);
                }
            }
        });
    }

    private void notifyConnected(final boolean isConnected) {
        mIsConnected = isConnected;
        // make callback run on UI thread
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mConnectionListener != null) {
                    mConnectionListener.onConnected(isConnected);
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
}
