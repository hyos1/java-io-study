package network.test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class Client {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");
        try (Socket socket = new Socket("localhost", PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            log("소캣 연결: " + socket);

            Scanner sc = new Scanner(System.in);
            System.out.print("이름을 입력하세요: ");
            String userName = sc.nextLine();
            output.writeUTF("/join|" + userName);
            while (true) {
                System.out.print("전송할 문자: ");
                String toSend = sc.nextLine();

                // 서버에게 전송
                output.writeUTF(toSend);
                log("client -> server: " + toSend);

                if (toSend.equals("exit")) {
                    break;
                }

                // 서버로부터 문자 받기
                String received = input.readUTF();
                log("client <- server: " + received);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
