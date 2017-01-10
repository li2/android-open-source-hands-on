package me.li2.android.clientsocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ClientSocket";
    private static final String HELLO_WORLD = "Hello World, I'm an Android Client-Side Socket";

    @BindView(R.id.messageText) TextView mMessageView;
    @BindView(R.id.serverIpEditText) EditText mIpEditText;
    @BindView(R.id.serverPortEditText) EditText mPortEditText;

    private NetworkConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mConnection = new NetworkConnection(this);
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
        String ip = mIpEditText.getText().toString();
        int port = Integer.valueOf(mPortEditText.getText().toString());
        mConnection.connect(ip, port);
    }

    @OnClick(R.id.sendBtn)
    void sendToServer() {
        mConnection.sendPacket(HELLO_WORLD);
    }

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
