package me.li2.android.clientsocket;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.connectBtn)
    void connectToServer() {

    }

    @OnClick(R.id.sendBtn)
    void sendToServer() {
        HandlerThread thread = new HandlerThread("SocketThread");
        thread.start();
        Handler handler = new Handler(thread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "send to server", Toast.LENGTH_LONG).show();
                send(HELLO_WORLD);
            }
        });
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(MainActivity.this, "sendToServer", Toast.LENGTH_LONG).show();
                // RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
                // NOTE21: Call toast in this thread will cause exception!
                send(HELLO_WORLD);
            }
        }, "SocketThread").start();
        */
    }

    private static final String SERVER_IP = "192.168.0.102";
    private static int SERVER_PORT = 3000;

    private void send(String packet) {
        try {
            Log.d(TAG, "client socket");
            String ip = mIpEditText.getText().toString();
            int port = Integer.valueOf(mPortEditText.getText().toString());

            Socket socket = new Socket(ip, port);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(packet);
            dataOutputStream.flush();
            //dataOutputStream.close();
            //socket.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed to open socket, exception: " + e);
        }
    }
}
