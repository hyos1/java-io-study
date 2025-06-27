package review.test.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static review.util.MyLogger.log;

public class ReadHandler implements Runnable {
    private final DataInputStream input;

    public ReadHandler(DataInputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String received = input.readUTF();
                System.out.println(received);
            }
        } catch (IOException e) {
            log("수신 종료: " + e);
        }
    }
}
