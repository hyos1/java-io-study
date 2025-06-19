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

            // 메세지 받는 스레드
            ReceivedThread receivedThread = new ReceivedThread(input);
            Thread thread = new Thread(receivedThread);
            thread.start();

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("보낼 메세지: ");
                String toSend = sc.nextLine();

                // 서버에게 문자 보내기
                output.writeUTF(toSend);
                log("보낸 메세지: " + toSend);
//                log("client -> server: " + toSend);
                if (toSend.equals("/exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            log(e);
        }
    }

    static class ReceivedThread implements Runnable {

        private final DataInputStream input;

        public ReceivedThread(DataInputStream input) {
            this.input = input;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String received = input.readUTF();
                    System.out.print("\r");
                    log("📩 " + received);
                }
            } catch (IOException e) {
                log("수신 종료" + e);
            }
        }
    }
}
