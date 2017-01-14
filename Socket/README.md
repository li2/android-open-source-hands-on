## Server-Client Socket

### ClientSocket

An Android App which implements the client-side socket.

`NetworkConnection` is used to manage the socket connection:

 1. Connect to server: `public void connect(final String serverIp, final int serverPort)`
 1. Disconnect to server: `public void disconnect(final String disconnectReason)`
 1. Send data to server: `public void sendData(final String data)`
 1. Receive data from server through a thread.

It defines a callback for UI to update:

```java
public interface ConnectionListener {
    void onConnected(final boolean isConnected, final String disconnectReason);
    void onDataReceived(final String data);
    void onDataSent(final String data, final boolean succeeded);
}
```

It has a boolean flag `mIsConnected` to record connection status. When connection status changed, et: open/close socket, network disconnect, it updates this flag, and invokes the callback to notify UI.

So UI don't need to care about the details of connection (socket, input/output stream), just register this callback and use the public method to connect/disconnect/send data to server. commit #2f452ea


### ServerSocket

A Java Project which implements the server-side socket. When server is running, it waits new client connection, receive data from client, send response to this client, and broadcast the data to other clients. To run the server:

```sh
$ ./runServer.sh
```

To support multiple clients socket connection, create a thread pool (through ExecutorService), commit #aac37a0. 


### Issues：

- Server can only receive the first message from client. commit #65796b8
- Java socket API: How to tell if a connection has been closed? We have to close stream and socket when disconnect. The right way to detect disconnect is to catch the IOException. commit #dee5bce


![Server](https://github.com/li2/Learning_Android_Open_Source/blob/master/Socket/DemoServerSocket.png)

![Client1](https://github.com/li2/Learning_Android_Open_Source/blob/master/Socket/DemoClientSocket1.png)

![Client2](https://github.com/li2/Learning_Android_Open_Source/blob/master/Socket/DemoClientSocket2.png)

------

weiyi.li, 2017-01-14, http://li2.me