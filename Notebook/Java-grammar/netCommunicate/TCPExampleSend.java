import java.io.IOException;
import java.net.Socket;

public class TCPExampleSend {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8888);

        socket.getOutputStream().write("Hello World".getBytes());

        socket.close();

    }
}
