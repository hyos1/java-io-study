package chat.server.command;

import chat.server.Session;

import java.io.IOException;

public interface Command {
    void execute(String[] arg, Session session) throws IOException;
}
