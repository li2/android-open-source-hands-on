package me.li2;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args) {
      Server server = new Server();
      server.addShutdownHook();
      
      Thread thread = new Thread(new Runnable()
      {
        @Override
        public void run()
        {
          server.openSocket(3000);
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
        System.out.println("Server is running and listening ...");
        mServerSocket = new ServerSocket(port);
        mSocket = mServerSocket.accept();
        mDataInputStream = new DataInputStream(mSocket.getInputStream());
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
