package chat.server.command;

import chat.server.Session;
import chat.server.SessionManager;

public class MessageCommand implements Command {

    private final SessionManager sessionManager;

    public MessageCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] arg, Session session) {
        String message = arg[1];
        sessionManager.sendAll("[" + session.getUsername() + "] " + message);
    }
}
