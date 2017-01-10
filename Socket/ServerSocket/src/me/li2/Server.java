package me.li2;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sun.nio.cs.ext.MSISO2022JP;

public class Server
{
    private static final int LISTEN_PORT = 3000;
    private int mListenPort;
    private int mConnectionIndex;
    private ServerSocket mServerSocket;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(10);
    
    public Server(int listenPort)
    {
      mListenPort = listenPort;
    }
    
    /**
     * Create a thread pool through {@link ExecutorService} to support multiple client socket connection. 
     * Refer to http://stackoverflow.com/questions/12588476/multithreading-socket-communication-client-server
     */
    private void startServer() {
      try {
        System.out.println("Starting Server");
        mServerSocket = new ServerSocket(mListenPort);
        
        while (true)
        {
          System.out.println("Waiting for new client connection");
          try {
            // wait for a client to connect
            Socket clientSocket = mServerSocket.accept();
            System.out.println("Processing connection #" + mConnectionIndex++);
            // create a new client socket handler which running in background thread
            mExecutorService.submit(new ClientSocketHandler(clientSocket));
          } catch (IOException e) {
            System.out.println("Error processing connection");
            e.printStackTrace();
          }
        }
      } catch (IOException e) {
        System.out.println("Error starting Server on " + mListenPort);
        e.printStackTrace();
      }
    }
    
    /**
     * Shutdown the thread pool and close the server socket.
     */
    private void stopServer()
    {
      mExecutorService.shutdown();
      try {
        if (mServerSocket != null && !mServerSocket.isClosed()) {
          mServerSocket.close();
        }
      } catch (IOException e) {
        System.out.println("Error closing ServerSocket");
        e.printStackTrace();
      }
    }
    
    /**
     * This class is used to receive and handle packet from client.  
     */
    class ClientSocketHandler implements Runnable
    {
      private Socket mSocket;
      private DataInputStream mDataInputStream;
      private String mClientIp;
      
      public ClientSocketHandler(Socket socket)
      {
        super();
        assert (socket != null);
        mSocket = socket;
        mClientIp = mSocket.getRemoteSocketAddress().toString();
        
        try {
          mDataInputStream = new DataInputStream(mSocket.getInputStream());
        } catch (IOException e) {
          System.out.println("Error opening DataInputStream");
          e.printStackTrace();
        }
      }

      @Override
      public void run()
      {
        while (true) {
          try {
            System.out.println("Receiving from client " + mClientIp + ":  " + mDataInputStream.readUTF());
          } catch (IOException e) {
            System.out.println("Error reading packet");
            e.printStackTrace();
          }
        }
      }
    }
    
    
    private boolean bExit = false;
    
    private void addShutdownHook()
    {     
      Runtime.getRuntime().addShutdownHook(new Thread()
      {
        public void run() {
          System.out.println("Shouting down, close socket.");
          bExit = true;
          stopServer();
        }
      });
    }
    
    public static void main(String[] args) {
      Server server = new Server(LISTEN_PORT);
      server.addShutdownHook();
      server.startServer();
    }
}
