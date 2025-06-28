package review.chat.server.command;

import review.chat.server.Session;
import review.chat.server.SessionManager;

public class MessageCommand implements Command {
    private final SessionManager sessionManager;

    public MessageCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public void execute(String[] args, Session session) {
        // 클라이언트 전체에게 문자 전송
        String message = args[1];
        sessionManager.sendAll("[" + session.getUsername() + "] " + message);
    }
}
