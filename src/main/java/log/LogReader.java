package log;

import util.FileUtils;

public class LogReader {

    public static Report read(String fileName) {
        Report result = FileUtils.read(fileName, new Report(), (report, line) -> {
            report.add(line);
        });

        result.sortAll();
        return result;
    }
}
