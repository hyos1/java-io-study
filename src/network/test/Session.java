package network.test;

import network.tcp.SocketCloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.MyLogger.log;

public class Session implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;
    private boolean closed = false;

    private String name;

    public Session(Socket socket, SessionManager sessionManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        this.sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 클라이언트로부터 문자 받기
                String received = input.readUTF();
                log("client -> server: " + received);
                String[] split = received.split("\\|", 2);

                if (!(split[0].startsWith("/")) || split.length < 2) {
                    output.writeUTF("잘못된 형식입니다. 다시 입력해주세요");
                    continue;
                }
                // 접속 종료
                if (split[0].equals("/exit")) {
                    if (name != null) {
                        output.writeUTF(name + "님이 종료하셨습니다.");
                    }
                    break;
                } else if (split[0].equals("/join")) {
                    name = split[1];
                    String joinInfo = name + "님이 입장하셨습니다.";
                    sessionManager.joinUserInfo(this, joinInfo);
//                    sessionManager.sendAllUsers(this, name, "님이 입장하셨습니다.");
                } else if (split[0].equals("/message")) {
                    sessionManager.sendAllUsers(this, name, split[1]);
//                    output.writeUTF(split[1]);
                } else if (split[0].equals("/change")) {
                    name = split[1];
                }
            }
        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(this);
            close();
        }
    }

    // 세션 종료시, 서버 종료시 동시에 호출될 수 있다.
    public synchronized void close() {
        if (closed) {
            return;
        }
        SocketCloseUtil.closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket);
    }

    public synchronized void joinSession(String joinMessage) throws IOException {
//        String joinInfo = name + "님이 입장하셨습니다.";
        output.writeUTF(joinMessage);
        log(joinMessage);
    }

    public synchronized void sendMessage(String from, String message) throws IOException {
        String sendMessage = from + ": " + message;
        output.writeUTF(sendMessage);
        log(sendMessage);
    }



    public synchronized void exitUser(String from) {
        System.out.println(from + "님이 나가셨습니다");
    }
}
