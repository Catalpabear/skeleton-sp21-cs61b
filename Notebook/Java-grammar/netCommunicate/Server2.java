import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class Server2 {
    public static void main(String[] args)throws IOException {

        String saveDir = new File(System.getProperty("user.dir")).getAbsolutePath();

        ServerSocket serverSocket = new ServerSocket(10000);

        System.out.println("waiting for client");
        Socket socket = serverSocket.accept();//waiting connection
        System.out.println("client connected to "+socket.getInetAddress());

        String fileName= UUID.randomUUID().toString()+".txt";
        File file = new File(saveDir,fileName);

        // 接收文件并写入到 Server.java 所在目录
        BufferedInputStream socketIn = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(file));

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = socketIn.read(buffer)) != -1) {
            fileOut.write(buffer, 0, bytesRead);
        }

        fileOut.flush();
        fileOut.close();
        socket.close();
        serverSocket.close();
    }
}
