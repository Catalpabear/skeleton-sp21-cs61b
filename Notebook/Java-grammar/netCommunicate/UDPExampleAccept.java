import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPExampleAccept {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(8888);

        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        System.out.println("Waiting for UDP response...");
        socket.receive(packet);

        String received = new String(packet.getData(),0,packet.getLength());

        System.out.println(received);


    }
}
