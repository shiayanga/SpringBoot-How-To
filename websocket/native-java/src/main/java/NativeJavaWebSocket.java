import server.WebSocketServerImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author LiYang
 */
public class NativeJavaWebSocket {
    public static void main(String[] args) throws InterruptedException {
        int port = 8887;

        WebSocketServerImpl webSocketServer = new WebSocketServerImpl(port);
        webSocketServer.start();
        System.out.println("WebSocket Server started on port:" + webSocketServer.getPort());

        int loop = 100;
        for (int i = 0; i < loop; i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
            webSocketServer.broadcast(simpleDateFormat.format(new Date()) + ",第 " + (i + 1) + " 次推送消息");
            Random random = new Random();
            int nextInt = random.nextInt(2000);
            Thread.sleep(nextInt);
        }
    }
}
