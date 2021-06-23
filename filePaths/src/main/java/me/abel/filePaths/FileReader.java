package me.abel.filePaths;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileReader {

    public static void main(String[] args) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("/Users/lizhen/Desktop/update.txt"), StandardCharsets.UTF_8);
//            BufferedWriter writer = Files.newBufferedWriter(Paths.get("C:\\Members3.txt"), StandardCharsets.UTF_8);

            String str = null;
            Set<String> stringSet = new HashSet<>();
            while ((str = reader.readLine()) != null) {
//                if (str != null && str.indexOf(", CAST(0x") != -1 && str.indexOf("AS DateTime)") != -1) {
//                    String newStr = str.substring(0, str.indexOf(", CAST(0x")) + ")";
//                    writer.write(newStr);
//                    writer.newLine();
//                }
                if (str.startsWith("###   @2=")) {
                    str = str.substring(str.indexOf("'") + 1, str.lastIndexOf("'"));
                    System.out.println(str);
                    if (str.contains("1003")){
                        stringSet.add(str);
                    }
                }
            }
            System.out.println(stringSet);
//            writer.flush();
//            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
