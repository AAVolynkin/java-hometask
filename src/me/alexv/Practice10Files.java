package me.alexv;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Practice10Files {
    //1.	Написать метод, который читает текстовый файл и возвращает его в виде списка строк.
    public static List<String> method1(String fileFullPath) {
        List<String> arrayList = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(fileFullPath))) {
            String line;
            while ((line = in.readLine()) != null) {
                arrayList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    //2.	Написать метод, который записывает в файл строку, переданную параметром.
    public static void method2(String fileFullPath, String str) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileFullPath, true))) {
            bw.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //3.	Используя решение 1 и 2, напишите метод, который склеивает два текстовый файла один.
    public static void method3 (String[] srcFiles, String outputFile) {
        for (String filePath: srcFiles) {
            //read
            List<String> srcStrings = method1(filePath);
            //write
            for (String str: srcStrings) {
                method2(outputFile, str + "\n");
            }
        }
    }

    //4.	Написать метод для копирования файла (побайтно, или массивами байт).
    public static void method4 (String srcFile, String dstFile) {
        byte[] buffer = new byte[1];
        int len;

        try (FileInputStream fin = new FileInputStream(srcFile)) {
            try (FileOutputStream fout = new FileOutputStream(dstFile, true)) {
                while ((len = fin.read(buffer)) != -1) {
                    fout.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //5.	Написать метод, который в каталоге ищет файлы, в имени которых содержится определенная строка, и который возвращает список имен таких файлов.
    public static List<String> method5 (String dir, String substring) {
        List<String> listFiles = new ArrayList<>();

        try (Stream<Path> paths = Files.find( Path.of(dir),
                Integer.MAX_VALUE,
                (file, attr) -> !attr.isDirectory() && file.getFileName().toString().contains(substring))) {
            listFiles = paths.map(f -> f.getFileName().toString()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listFiles;
    }

    //6.	Написать метод, который в каталоге ищет текстовые файлы, в которых содержится определенная строка, и которая возвращает список имен таких файлов. (FilenameFilter)
    public static List<String> method6 (String dir, String substring) {
        File directory = new File(dir);
        List<String> listFiles = new ArrayList<>();

        try {
            File[] files = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains(substring);
                }
            });
            listFiles = Arrays.stream(files).map(File::getName).collect(Collectors.toList());
        } catch (NullPointerException ignore) {
        }

        return listFiles;
    }

}
