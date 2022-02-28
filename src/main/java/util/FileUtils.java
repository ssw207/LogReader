package util;

import java.io.*;
import java.util.function.BiConsumer;

public class FileUtils {
    
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
}
