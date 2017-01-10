package me.li2.android.clientsocket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;

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
    private Socket mClientSocket;
    private DataInputStream mDataInputStream;
    private DataOutputStream mDataOutputStream;
    private Thread mConnectionThread;
    private Handler mSendHandler;

    public NetworkConnection(Context context) {
        mContext = context;

        // init send thread handler
        HandlerThread sendHandlerThread;
        sendHandlerThread = new HandlerThread("SendHandlerThread");
        sendHandlerThread.start();
        mSendHandler = new Handler(sendHandlerThread.getLooper());
    }

    /**
     * Connect to socket
     * @param serverIp the server ip
     * @param serverPort the server port
     */
    public void connect(final String serverIp, final int serverPort) {
        if (mConnectionThread == null) {
            mConnectionThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    openSocket(serverIp, serverPort);
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
                mConnectionThread = null;
            }
        });
        mConnectionThread.setName("NetworkDisconnectThread");
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
                e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            Log.i(TAG, "Network is available.");
            return true;
        } else {
            Log.e(TAG, "Network is not available!");
            return false;
        }
    }

    /**
     * Send packet to server
     * @param packet the packet to be sent
     */
    public void sendPacket(final String packet) {
        mSendHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "send to server", Toast.LENGTH_LONG).show();
                try {
                    mDataOutputStream.writeUTF(packet);
                    mDataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
