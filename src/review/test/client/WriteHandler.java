package review.test.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static review.util.MyLogger.log;

public class WriteHandler implements Runnable {

    private final DataOutputStream output;

    public WriteHandler(DataOutputStream output) {
        this.output = output;
    }

    @Override
    public void run() {

    }
}
