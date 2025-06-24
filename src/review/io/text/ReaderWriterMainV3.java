package review.io.text;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.*;
import static review.io.text.TextConst.*;

public class ReaderWriterMainV3 {

    public static void main(String[] args) throws IOException {
        String writerString = "ABC";
        System.out.println("write String: " + writerString);

        // 파일에 쓰기
        FileWriter fw = new FileWriter(FILE_NAME, UTF_8);
        fw.write(writerString);
        fw.close();

        // 파일에서 읽기
        StringBuilder content = new StringBuilder();
        FileReader fr = new FileReader(FILE_NAME, UTF_8);
        int ch;
        while ((ch = fr.read()) != -1) {
            content.append((char) ch);
        }
        fr.close();

        System.out.println("read String: " + content);
    }
}
