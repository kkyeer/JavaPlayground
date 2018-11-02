package effectivejava3.streams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author kkyeer@gmail.com
 * @date 2018/11/1 15:59
 */
public class Anagrams {
    public static void main(String[] args) throws IOException, URISyntaxException {
        File dictionary = Paths.get(Anagrams.class.getResource("/").toURI()).resolve("dictionary.txt").toFile();
        int minGroupSize = 2;
//        normalForm(dictionary,minGroupSize);
        streamForm(dictionary,minGroupSize);
//        "hello world".chars().forEach(System.out::print);
    }

    private static void normalForm(File dictionary,int minGroupSize) throws URISyntaxException, FileNotFoundException {
        Map<String, Set<String>> groups = new HashMap<>(10);
        try (Scanner s = new Scanner(dictionary)) {
            while (s.hasNext()) {
                String word = s.next();
//                下面这段代码共两步，
//              1.如果alphabetize(word)值对应的key在groups里不存在，则新建一个TreeSet并返回，否则直接返回key对应的TreeSet,
//              2.返回的TreeSet中add(word)
                groups.computeIfAbsent(alphabetize(word),(unused) -> new TreeSet<>()).add(word);
            }
        }
        for (Set<String> group : groups.values()){
            if (group.size() >= minGroupSize) {
                System.out.println(group.size() + ": " + group);
            }
        }
    }

    private static void streamForm(File dictionary, int minGroupSize){
        try (Stream<String> words = Arrays.stream(Files.readAllLines(dictionary.toPath()).get(0).split("\\s"))) {
//            过程 1.word变成List<String>
//            2.filter过滤大于小于阈值的Set
//            3.返回大于小于阈值的Set
            Collectors.groupingBy(Anagrams::alphabetize);
            words.collect(Collectors.groupingBy(Anagrams::alphabetize))
                    .values().parallelStream()
                    .filter((list)->list.size()>=minGroupSize)
                    .forEach((group)-> System.out.println(group.size() + ": " + group));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
