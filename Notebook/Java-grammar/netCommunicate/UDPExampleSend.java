import java.io.IOException;
import java.net.*;

public class UDPExampleSend {
    public static void main(String[] args) throws IOException {

        // 初始化IP, socket 数据包
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramSocket socket = new DatagramSocket();
        String str = "Ciallo World";
        byte[] buf = str.getBytes();

        // 构建数据包
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 8888);

        // 发送
        socket.send(packet);

        // 释放资源
        socket.close();
    }

}
