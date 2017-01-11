package me.li2.android.clientsocket;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ClientSocket";
    private static final String HELLO_WORLD = "Hello World, I'm an Android Client-Side Socket";

    @BindView(R.id.logText) TextView mLogView;
    @BindView(R.id.serverIpEditText) EditText mIpEditText;
    @BindView(R.id.serverPortEditText) EditText mPortEditText;
    @BindView(R.id.sendEditText) EditText mSendEditText;
    @BindView(R.id.connectStatusView) View mConnectStatusView;
    @BindView(R.id.connectBtn) Button mConnectButton;

    private NetworkConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        // Create connection
        mConnection = new NetworkConnection(this, mConnectionListener);
        // 如果不在onCreate的时候初始化控件（调用下面的方法），那么此后更新控件无效。
        // 不知道这算不算 ButterKnife 的 bug.
        updateConnectStatusView(mConnection.isConnected());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnection != null)
        {
            mConnection.disconnect();
        }
    }

    @OnClick(R.id.connectBtn)
    void connectToServer() {
        if (!mConnection.isConnected()) {
            String ip = mIpEditText.getText().toString();
            int port = Integer.valueOf(mPortEditText.getText().toString());
            mConnection.connect(ip, port);
        } else {
            mConnection.disconnect();
        }
    }

    @OnClick(R.id.sendBtn)
    void sendToServer() {
        if (mConnection.isConnected()) {
            mConnection.sendData(buildSentData());
        }
    }

    private NetworkConnection.ConnectionListener mConnectionListener =
            new NetworkConnection.ConnectionListener() {
                @Override
                public void onConnected(boolean isConnected) {
                    Log.d(TAG, "onConnected " + isConnected);
                    updateConnectStatusView(isConnected);
                }

                @Override
                public void onDataReceived(String data) {

                }

                @Override
                public void onDataSent(String data, boolean succeeded) {
                    updateLogView(true, data);
                }
            };


    private void updateConnectStatusView(boolean isConnected) {
        GradientDrawable bgShape = (GradientDrawable)mConnectStatusView.getBackground();
        int colorResId = isConnected ? android.R.color.holo_green_dark : android.R.color.holo_red_dark;
        bgShape.setColor(ContextCompat.getColor(this, colorResId));

        mConnectButton.setText(isConnected ? "Disconnect" : "Connect");
    }

    private void updateLogView(boolean isSent, String log) {
        String prefix = isSent ? " <    " : " >    ";
        String suffix = "\n";
        mLogView.append(prefix + log + suffix);
    }

    private String buildSentData() {
        String text = mSendEditText.getText().toString();
        if (text == null || text.isEmpty()) {
            text = HELLO_WORLD;
        }

        text += buildCurrentTimeStamp();
        return text;
    }

    private String buildCurrentTimeStamp() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return "@" + sdf.format(new Date());
    }


    ///////////////////////////////////////////////////////////////////////////
    //              Deprecated Method                                        //
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 最开始的做法：每次调用 send，建立 socket 连接，然后发送。
     * 但 Server 只能成功收到一次，第二次 send 发送，Server 就收不到了。
     *
     * 尝试的办法，都没用：
     * (1) 在包尾加 \n，因为有人讲 Server readline() 找不到换行符号"\n"而堵塞，但我在 Server 端使用了 read();
     * (2) 使用 HandlerThread 代替 Thread 发送。
     *
     * 后来，只在初始化时建立连接，send 只通过 OutputStream 发送数据包，就没这个问题了。
     * 因为 Client 端每次 new Socket，都建立了一个新的 socket session，
     * 而Server 端并没有实现 socket session 线程池，mDataInputStream 是旧的 socket session，也就无法继续读到数据。
     */
    @SuppressWarnings("unused")
    private void send(String packet) {
        try {
            Log.d(TAG, "client socket");
            String ip = mIpEditText.getText().toString();
            int port = Integer.valueOf(mPortEditText.getText().toString());
            Socket socket = new Socket(ip, port);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(packet + "\r\n");
            dataOutputStream.flush();
        } catch (IOException e) {
            Log.e(TAG, "Failed to open socket, exception: " + e);
        }
    }
}
