package network.test;

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

    public synchronized void joinUserInfo(Session joinSession, String joinMessage) throws IOException {
        for (Session session : sessions) {
            if (session != joinSession) {
                session.joinSession(joinMessage);
            }
        }
    }

    public synchronized void sendAllUsers(Session sendSession, String from, String message) throws IOException {
        for (Session session : sessions) {
            if (session != sendSession) {
                session.sendMessage(from, message);
            }
        }
    }
}
