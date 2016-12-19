package me.li2.android.clientsocket;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ClientSocket";
    private static final String HELLO_WORLD = "Hello World, I'm an Android Client-Side Socket";
    private TextView mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMessageView = (TextView) findViewById(R.id.messageText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        send(HELLO_WORLD);
                    }
                }, "SocketThread").start();
            }
        });
    }

    private static final String NETWORK_IP = "192.168.0.100";
    private static int NETWORK_PORT = 3000;

    private void send(String packet)
    {
        try {
            Log.d(TAG, "client socket");
            Socket socket = new Socket(NETWORK_IP, NETWORK_PORT);
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
