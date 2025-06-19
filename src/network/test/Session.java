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
                String received = input.readUTF();
                if (received.equals("/exit")) {
                    break;
                }

                String[] part = received.split("\\|", 2);
                if (part.length < 2) {
                    output.writeUTF("명령어 형식이 잘못되었습니다.");
                    continue;
                }
                switch (part[0]) {
                    case "/join":
                        name = part[1];
                        output.writeUTF(name + "님이 입장하셨습니다.");
                        break;
                    case "/message":
                        if (name == null) {
                            output.writeUTF("[오류] 이름을 등록하세요.");
                        }
                        sessionManager.sendAllUsers(name, part[1]);
                        break;
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

    public synchronized void sendMessage(String from, String message) throws IOException {
        String sendMessage = from + ": " + message;
        output.writeUTF(sendMessage);
        log(sendMessage);
    }
}
