package server;

import com.github.shiayanga.common.time.TimeFormatUtil;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Date;

/**
 * @author LiYang
 */
public class WebSocketServerImpl extends WebSocketServer {
    public WebSocketServerImpl(int port){
        super(new InetSocketAddress(port));
    }
    public WebSocketServerImpl(InetSocketAddress address){
        super(address);
    }
    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        webSocket.send("Welcome to the WebSocket Server");
        broadcast("new Connection:" + clientHandshake.getResourceDescriptor());
        System.out.println(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        broadcast(webSocket + " has left the room");
        System.out.println(webSocket + " has left the room");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        broadcast(TimeFormatUtil.dateToString(new Date()) +s);
        System.out.println(webSocket.getRemoteSocketAddress().getHostName() + "-" + webSocket.getRemoteSocketAddress().getAddress() + ": " + s);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
        System.out.println(webSocket + ": " + e.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }
}
