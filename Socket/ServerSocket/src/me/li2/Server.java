package me.li2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.li2.ClientSocketHandler.OnClientActionListener;

public class Server
{
    private int mListenPort;
    private int mConnectionIndex;
    private ServerSocket mServerSocket;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(10);
    private HashMap<String, ClientSocketHandler> mClients = new HashMap<>();
    
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
        
        while (true && !bExit)
        {
          System.out.println("Waiting for new client connection");
          try {
            // wait for a client to connect
            Socket clientSocket = mServerSocket.accept();
            String clientIp = clientSocket.getInetAddress().toString();
            System.out.println("Processing connection #" + (mConnectionIndex++) + " " + clientIp);

            // create a new client socket handler which running in background thread
            ClientSocketHandler clientSocketHandler = new ClientSocketHandler(clientSocket, mOnClientActionListener);
            mExecutorService.submit(clientSocketHandler);
            mClients.put(clientIp, clientSocketHandler);
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
    
    private OnClientActionListener mOnClientActionListener = new OnClientActionListener()
    {
      @Override
      public void onReceived(String clientIp, String data)
      {
        for (ClientSocketHandler clientSocketHandler : mClients.values())
        {
          if (clientSocketHandler.getIp().equals(clientIp))
          {
            // send response to this client
            clientSocketHandler.sendData("Response: server has received " + data);
          }
          else
          {
            // send to other clients
            clientSocketHandler.sendData("From client " + clientIp + ": " + data);
          }
        }
      }

      @Override
      public void onDisconnect(String clientIp)
      {
        mClients.remove(clientIp);
        for (ClientSocketHandler clientSocketHandler : mClients.values())
        {
          clientSocketHandler.sendData("Client " + clientIp + " disconnect!");
        }
      }
    };
    
    // -------- Main ----------------------------------------------------------
    
    private static final int LISTEN_PORT = 3000;
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
