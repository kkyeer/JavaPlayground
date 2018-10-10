package effectivejava3.littletaste;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/3 11:37
 */
public class TasteTryWith {
    public static void main(String[] args) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("test.txt")))) {
            bufferedWriter.write("hello try with");
        }
    }
}
