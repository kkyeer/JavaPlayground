package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: kkyeer
 * @Description: 文件工具
 * @Date:Created in 16:30 2024/1/1
 * @Modified By:
 */
public class FileUtils {
    public static void main(String[] args) {
        String left = "/Users/kkyeer/Downloads";
        String right = "/Users/kkyeer/Downloads";
        Map<String, Set<Path>> hashToFileNamesLeft = getFromPath(left);
        Map<String, Set<Path>> hashToFileNamesRight = getFromPath(right);
        compare(hashToFileNamesLeft, hashToFileNamesRight);
        compare(hashToFileNamesRight,hashToFileNamesLeft);
    }

    private static void compare(Map<String, Set<Path>> leftMap, Map<String, Set<Path>> rightMap) {
        List<Path> leftExistRightNot = new ArrayList<>();
        List<Path> leftDuplicate = new ArrayList<>();
        int leftDuplicateCount = 0;
        for (String leftKey : leftMap.keySet()) {
            if (!rightMap.containsKey(leftKey)) {
                leftExistRightNot.addAll(leftMap.get(leftKey));
            }
            Set<Path> paths = leftMap.get(leftKey);
            if (paths.size() > 1) {
                leftDuplicate.addAll(paths);
                leftDuplicateCount += (paths.size() - 1);
            }
        }
        System.out.println("leftExistRightNot:" + leftExistRightNot);
        System.out.println("leftDuplicateCount" + leftDuplicateCount);
        System.out.println("leftDuplicate:" + leftDuplicate);
    }

    private static Map<String, Set<Path>> getFromPath(String path) {
        System.out.println("Scanning path:" + path);
        Path dirPath = Paths.get(path);
        Map<String, Set<Path>> result = new HashMap<>();
        AtomicLong count = new AtomicLong(0L);
        try {
            Files.walkFileTree(dirPath, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toFile().isFile()) {
                        long current = count.incrementAndGet();
                        System.out.println("Got " + current + " files:" + file.toAbsolutePath());
                        String hashId = Hash.MD5.checksum(file.toFile());
                        if (result.containsKey(hashId)) {
                            result.get(hashId).add(file);
                        }else {
                            Set<Path> fileNameSet = new HashSet<>();
                            fileNameSet.add(file);
                            result.put(hashId,fileNameSet);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    enum Hash {
        MD5("MD5"),
        SHA1("SHA1"),
        SHA256("SHA-256"),
        SHA512("SHA-512");
        private String name;
        Hash(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String checksum(File input) {
            try (InputStream in = Files.newInputStream(input.toPath())) {
                MessageDigest digest = MessageDigest.getInstance(getName());
                byte[] block = new byte[4096];
                int length;
                while ((length = in.read(block)) > 0) {
                    digest.update(block, 0, length);
                }
                byte[] digest1 = digest.digest();
                StringBuilder result = new StringBuilder();
                for (byte b : digest1) {
                    result.append(String.format("%02x", b));
                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
