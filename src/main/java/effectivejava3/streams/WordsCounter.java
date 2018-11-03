package effectivejava3.streams;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 12:22 2018/11/3
 * @Modified By:
 */
public class WordsCounter {
    private static Integer counting(List<String> strings){
        return strings.size();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(WordsCounter.class.getResource("/").toURI()).resolve("words.txt").toFile();
        try (Stream<String> words = Arrays.stream(Files.readAllLines(file.toPath()).get(0).split("\\s"))){
//            words.collect();
        }
    }
}
