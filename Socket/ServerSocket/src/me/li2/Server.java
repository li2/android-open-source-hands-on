package me.li2;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
    private static final int LISTEN_PORT = 3000;
    private ArrayList<Socket> mSocketSessionList;
    
    public static void main(String[] args) {
      Server server = new Server();
      server.addShutdownHook();
      
      Thread thread = new Thread(new Runnable()
      {
        @Override
        public void run()
        {
          server.openSocket(LISTEN_PORT);
          server.startReceiveThread();;
        }
      });
      thread.start();
    }
    
    
    ///////////////////////////////////////////////////////////////////////////
    //                        Server Socket                                  //
    ///////////////////////////////////////////////////////////////////////////
    
    private ServerSocket mServerSocket;
    private Socket mSocket;
    private DataInputStream mDataInputStream;
    private boolean bExit = false;
    
    private void openSocket(int port)
    {
      try {
        mServerSocket = new ServerSocket(port);
        mSocket = mServerSocket.accept();
        mDataInputStream = new DataInputStream(mSocket.getInputStream());
        System.out.println("Server is running and listening ...");
        System.out.println("ip: " + mServerSocket.getInetAddress() + ":" + LISTEN_PORT);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void closeSocket()
    {
      try {
        if (mSocket != null && !mSocket.isClosed()) {
          mSocket.close();
        }
        if (mServerSocket != null && !mServerSocket.isClosed()) {
          mServerSocket.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    private void startReceiveThread()
    {
      if (mReceiveThread != null) {
        mReceiveThread.start();
      }
    }
    
    private Thread mReceiveThread = new Thread(new Runnable()
    {
      @Override
      public void run() {
        while (true && !bExit) {
          try {
            System.out.println("Received from client: " + mDataInputStream.readUTF());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
    
    private void addShutdownHook()
    {     
      Runtime.getRuntime().addShutdownHook(new Thread()
      {
        public void run() {
          System.out.println("Shouting down, close socket.");
          bExit = true;
          closeSocket();
        }
      });
    }
}
