package review.test.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private List<Session> sessions = new ArrayList<>();

    public synchronized void add(Session session) {
        sessions.add(session);
    }

    public synchronized void remove(Session session) {
        sessions.remove(session);
    }

    public synchronized void closeAll() {
        for (Session session : sessions) {
            session.close();
        }
        sessions.clear();
    }

    public synchronized void sendJoinInfo(String sendMessage) throws IOException {
        for (Session session : sessions) {
            session.sendJoinInfo(sendMessage);
        }
    }

    public synchronized void sendAllUser(String message) throws IOException {
        for (Session session : sessions) {
            session.sendMessage(message);
        }
    }
}
