package utils;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 将csv转成xlxs
 * @Date:Created in 10:15 AM 2020/11/26
 * @Modified By:
 */
public class CsvToExcel {
    public static void main(String[] args) {
        File file = new File("/Users/chengyingzhang/Downloads/shangcheng.csv");
        FileReader reader = FileReader.create(file, StandardCharsets.UTF_8);
        List<String> lines = reader.readLines();
        List<List<String>> rows = new ArrayList<>();
        for (String line : lines) {
            List<String> row =  Arrays.asList(line.split(","));
            rows.add(row);
        }
        ExcelWriter excelWriter = ExcelUtil.getWriter("/Users/chengyingzhang/Downloads/shangcheng.xlsx");
        excelWriter.write(rows);
        excelWriter.close();
    }
}
