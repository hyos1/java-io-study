package chat.server.command;

import chat.server.Session;
import chat.server.SessionManager;

public class ChangeCommand implements Command {

    private final SessionManager sessionManager;

    public ChangeCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] arg, Session session) {
        String changeName = arg[1];
        sessionManager.sendAll(session.getUsername() + "님이 " + changeName + "로 닉네임을 변경했습니다.");
        session.setUsername(changeName);
    }
}
