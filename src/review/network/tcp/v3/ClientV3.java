package review.network.tcp.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static review.util.MyLogger.log;

public class ClientV3 {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", PORT);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        log("소캣 연결: " + socket);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("전송 문자: ");
            String toSend = sc.nextLine();

            // 서버에게 문자 보내기
            output.writeUTF(toSend);
            log("client -> server: " + toSend);

            if (toSend.equals("exit")) {
                break;
            }

            // 서버로부터 문자 받기
            String received = input.readUTF();
            log("client <- server: " + received);
        }

        // 자원정리
        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();
    }
}
