package review.test.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static review.network.tcp.SocketCloseUtil.closeAll;
import static review.util.MyLogger.log;

public class Session implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;
    private boolean closed = false;
    private String name;

    private static final String DELIMITER = "\\|";

    public Session(Socket socket, SessionManager sessionManager) throws IOException {
        this.socket = socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        this.sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String received = input.readUTF();
                if (received.equals("/exit")) {
                    String exitMessage = name + "님이 퇴장하셨습니다.";
                    sessionManager.sendAllUser(exitMessage);
                    sessionManager.remove(this);
                    close();
                    break;
                }
                String[] split = received.split(DELIMITER, 2);

                if (split[0].equals("/join")) {
                    name = split[1];
                    String joinInfo = name + "님이 입장하셨습니다.";
                    sessionManager.sendJoinInfo(joinInfo);
                }

                if (split[0].equals("/message")) {
                    String sendMessage = name + ": " + split[1];
                    sessionManager.sendAllUser(sendMessage);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public synchronized void sendJoinInfo(String sendJoinInfo) throws IOException {
        output.writeUTF(sendJoinInfo);
    }

    public void sendMessage(String message) throws IOException {
        output.writeUTF(message);
        System.out.println(message);
    }
}
