package util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> readResourcesLines(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path가 null또는 공백값");
        }
        
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("path가 /로 시작하지 않음");
        }

        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(path)))) {
            String line;

            while ((line = br.readLine()) != null) {
                list.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
