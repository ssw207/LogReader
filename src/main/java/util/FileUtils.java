package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class FileUtils {
    public static List<String> readLines(String path) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(path)))) {
            String line;
            while ((line = br.readLine()) != null) {

            }
        } catch (Exception e) {

        }


        return null;
    }
}
