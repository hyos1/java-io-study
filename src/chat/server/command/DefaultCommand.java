package chat.server.command;

import chat.server.Session;
import chat.server.SessionManager;

import java.io.IOException;
import java.util.Arrays;

public class DefaultCommand implements Command {

    @Override
    public void execute(String[] arg, Session session) throws IOException {
            session.send("처리할 수 없는 명령어입니다: " + Arrays.toString(arg));
    }
}
