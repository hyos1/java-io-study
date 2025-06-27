package review.test.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static review.util.MyLogger.log;

public class Server {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        SessionManager sessionManager = new SessionManager();

        ShutdownHook shutdownHook = new ShutdownHook(serverSocket, sessionManager);
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "shutdown"));

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                log("소캣 연결: " + socket);

                Session session = new Session(socket, sessionManager);
                Thread thread = new Thread(session);
                thread.start();

            } catch (IOException e) {
                log("서버 소캣 종료: " + e);
            }
        }
    }

    static class ShutdownHook implements Runnable{
        private final ServerSocket serverSocket;
        private final SessionManager sessionManager;

        public ShutdownHook(ServerSocket serverSocket, SessionManager sessionManager) {
            this.serverSocket = serverSocket;
            this.sessionManager = sessionManager;
        }

        @Override
        public void run() {
            try {
                sessionManager.closeAll();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("e = " + e);
            }
        }
    }
}
