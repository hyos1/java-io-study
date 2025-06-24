package review.io.text;

import review.io.buffered.BufferedConst;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.*;
import static review.io.text.TextConst.*;

public class ReaderWriterMainV1 {

    public static void main(String[] args) throws IOException {
        String writeString = "ABC";
        // 문자 -> byte UTF-8 인코딩
        byte[] writeBytes = writeString.getBytes(UTF_8);
        System.out.println("writeString = " + writeString);
        System.out.println("writeBytes = " + Arrays.toString(writeBytes));

        // 파일에 쓰기
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        fos.write(writeBytes);
        fos.close();

        // 파일에서 읽기
        FileInputStream fis = new FileInputStream(FILE_NAME);
        byte[] bytes = fis.readAllBytes();
        fis.close();

        // byte -> String UTF-8 디코딩
        String readString = new String(bytes, UTF_8);
        System.out.println("read bytes: " + Arrays.toString(writeBytes));
        System.out.println("readString = " + readString);
    }
}
