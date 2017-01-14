package me.li2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * This class is used to receive and handle packet from client.
 */
public class ClientSocketHandler implements Runnable
{
  private boolean          mIsConnected;
  private Socket           mSocket;
  private DataInputStream  mDataInputStream;
  private DataOutputStream mDataOutputStream;
  private String           mClientIp;
  private OnClientActionListener mOnClientActionListener;
  
  public interface OnClientActionListener {
    void onReceived(final String clientIp, final String data);
    void onDisconnect(final String clientIp);
  }
  
  public ClientSocketHandler(Socket socket, OnClientActionListener listener)
  {
    super();
    assert(socket != null);
    mIsConnected = true;
    mSocket = socket;
    mClientIp = mSocket.getRemoteSocketAddress().toString();
    mOnClientActionListener = listener;

    try
    {
      mDataInputStream = new DataInputStream(mSocket.getInputStream());
      mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());
    } catch (IOException e)
    {
      System.out.println("Error opening DataInputStream");
      e.printStackTrace();
    }
  }

  @Override
  public void run()
  {
    while (mIsConnected)
    {
      try
      {
        String data = mDataInputStream.readUTF();
        System.out.println("Receiving from client " + mClientIp + ":  " + data);
        if (mOnClientActionListener != null)
        {
          mOnClientActionListener.onReceived(mClientIp, data);
        }
      } catch (IOException e)
      {
        // we have to close stream and socket when disconnect.
        // and the right way to detect disconnect is the IOException.
        // refer to http://stackoverflow.com/a/10241044/2722270
        System.out.println("Error reading data, close socket");
        disconnect();
      }
    }
    System.out.println(mClientIp + " disconnect");
  }
  
  public void sendData(final String data)
  {
    try
    {
      System.out.println("Sending to client " + mClientIp + ":  " + data);
      mDataOutputStream.writeUTF(data);
      mDataOutputStream.flush();
    } catch (IOException e)
    {
      System.out.println("Error writing data, close socket");
    }
  }

  public String getIp()
  {
    return mClientIp;
  }
  
  private void disconnect()
  {
    mIsConnected = false;
    try
    {
      if (mDataInputStream != null)
      {
        mDataInputStream.close();
        mDataInputStream = null;
      }
    } catch (IOException e)
    {
      System.out.println("Error closing InputStream");
    }

    try
    {
      if (mSocket != null)
      {
        mSocket.close();
        mSocket = null;
      }
    } catch (IOException e)
    {
      System.out.println("Error closing Socket");
    }
    
    if (mOnClientActionListener != null)
    {
      mOnClientActionListener.onDisconnect(mClientIp);
    }
  }
}
