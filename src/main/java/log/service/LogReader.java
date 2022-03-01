package log.service;

import log.domain.Report;
import log.dto.LogResultDto;
import util.FileUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class LogReader {

    private Report report;

    public Report getReport() {
        return report;
    }

    public void read(String filePath) {
        Report result = FileUtils.read(filePath, new Report(), (rp, line) -> report.add(line));
        result.sortAll();

        this.report = result;
    }

    public void makeResultFile(String resultPath) {
        FileUtils.write(resultPath, this.report, (bw, rp) -> {
            try {
                printLog(bw, rp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void printLog(BufferedWriter bw, Report report) throws IOException {
        FileUtils.println(bw, "최다호출 API KEY");
        printValue(bw, report.getApiKeyCallCountByLimit(1));
        bw.newLine();

        int limit = 3;
        FileUtils.println(bw, String.format("상위 %d개의 API Service ID와 각각의 요청 수", limit));
        printValue(bw, report.getApiServerCallCountIdByLimit(limit));
        bw.newLine();

        FileUtils.println(bw, "웹브라우저별 사용 비율");
        printValue(bw, report.getBrowserUseRatio(), "%s : %d%%");
    }

    private void printValue(BufferedWriter bw, List<LogResultDto> list) {
        printValue(bw, list, "%s : %d");
    }

    private void printValue(BufferedWriter bw, List<LogResultDto> list, String format) {
        list.forEach(dto -> FileUtils.println(bw, String.format(format, dto.getName(), dto.getValue())));
    }
}