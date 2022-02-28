package util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnObj;
    }

    public static void write(String resultPath) {
        if (StringUtils.isEmpty(resultPath)) {
            return;
        }

        File file = new File(resultPath);

    }
}
