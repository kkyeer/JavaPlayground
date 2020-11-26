package utils;

import cn.hutool.core.io.file.FileReader;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: kkyeer
 * @Description: 过滤两个字符串不重叠的部分
 * @Date:Created in 10:14 AM 2020/11/26
 * @Modified By:
 */
public class CsvFilter {
    public static void main(String[] args) {
        File file1 = new File("/Users/chengyingzhang/Downloads/yilingyong.csv");
        File file2 = new File("/Users/chengyingzhang/Downloads/yinggailing.csv");
        List<String> yiLingYong = FileReader.create(file1).readLines();
        List<String> yinggailing = FileReader.create(file2).readLines();
        Set<String> yinggaiSet = new HashSet<>();
        for (String s : yinggailing) {
            yinggaiSet.add(s);
        }
        for (String s : yiLingYong) {
            yinggaiSet.remove(s);
        }
        for (String s : yinggaiSet) {
            System.out.println(s);
        }
    }
}
