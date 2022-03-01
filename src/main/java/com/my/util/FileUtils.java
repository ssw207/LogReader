package com.my.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.BiConsumer;

public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static <T> T read (String path, T returnObj, BiConsumer<T, String> func) {
        File file = new File(path);

        try (BufferedReader br = new BufferedReader (new InputStreamReader(new FileInputStream(file)))) {
            String line;

            while ((line = br.readLine()) != null) {
                func.accept(returnObj,line);
            }

            return returnObj;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnObj;
    }

    public static <T> void write(String descPath, T infoObj, BiConsumer<BufferedWriter, T> func) {
        if (StringUtils.isEmpty(descPath)) {
            log.debug("descPath가 없으므로 중단");
            return;
        }

        try {
            Files.createDirectories(Paths.get(descPath).getParent()); // 상위경로의 폴더가 없으면 생성

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(descPath))) {
                func.accept(bw, infoObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void println(BufferedWriter bw, String value) {
        try {
            bw.append(value);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
