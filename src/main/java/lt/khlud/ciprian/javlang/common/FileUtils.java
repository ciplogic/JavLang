package lt.khlud.ciprian.javlang.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {
    public static Res<String> readFile(String path) {
        var file = new File(path);
        var sb = new StringBuilder();
        try {
            var bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) break;
                sb.append(line).append("\n");
            }
            return Res.ok(sb.toString());
        } catch (Exception e) {
            return Res.err(e.getMessage());
        }
    }

    public static List<String> filesInDir(String dirPath, String extension) {
        List<String> result = new ArrayList<>();
        populateDirectory(dirPath, extension, result);
        return result;
    }

    private static void populateDirectory(String dirPath, String extension, List<String> files) {
        var dir = new File(dirPath);
        File[] filesArray = dir.listFiles();
        for (var file : filesArray) {
            if (file.isDirectory()) {
                var subDir = dirPath + "/" + file.getName();
                populateDirectory(subDir, extension, files);
                continue;
            }
            if (file.getName().endsWith(extension)) {
                files.add(file.getAbsolutePath());
            }
        }
    }
}
