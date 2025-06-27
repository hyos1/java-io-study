package review.test.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static review.network.tcp.SocketCloseUtil.close;
import static review.network.tcp.SocketCloseUtil.closeAll;
import static review.util.MyLogger.log;

public class Client {

    private static final int PORT = 12345;
    private static final String DELIMITER = "\\|";
//    private String name;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            log("소캣 연결: " + socket);
            Thread thread = new Thread(new ReadHandler(input));
            thread.start();

            Scanner sc = new Scanner(System.in);
            // 이름 입력
            System.out.print("이름을 입력하세요: ");
            String username = sc.nextLine();
            output.writeUTF("/join|" + username);

            while (true) {
                System.out.print("보낼 메세지:");
                String sendMessage = sc.nextLine();

                // 종료할 때
                if (sendMessage.equals("/exit")) {
                    output.writeUTF(sendMessage);
                    break;
                }

                if (sendMessage.startsWith("/")) {
                    output.writeUTF(sendMessage);
                } else {
                    output.writeUTF("/message|" + sendMessage);
                }
            }
        } catch (IOException e) {
            log(e);
        }
        }
}
