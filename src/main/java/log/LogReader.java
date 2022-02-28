package log;

import log.domain.Report;
import util.FileUtils;

public class LogReader {

    public Report read(String filePath) {
        Report result = FileUtils.read(filePath, new Report(), (report, line) -> {
            report.add(line);
        });
        result.sortAll();
        return result;
    }
}