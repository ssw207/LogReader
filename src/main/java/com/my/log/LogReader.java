package com.my.log;

import com.my.log.domain.Report;
import com.my.log.dto.LogResultDto;
import com.my.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class LogReader {

    private static final Logger log = LoggerFactory.getLogger(LogReader.class);

    public static final String DEFAULT_SRC_PATH = "src/main/resources/input.log";
    public static final String DEFAULT_DES_PATH = "result/output.log";

    private Report report;

    public Report getReport() {
        return report;
    }

    public void read(String filePath) {
        log.info("\n");
        log.info("============= 1. 로그 파일 읽기 시작, 경로 [{}]============", filePath);
        Report result = FileUtils.read(filePath, new Report(), (rp, line) -> rp.add(line));
        result.sortAll();
        this.report = result;
        log.info("============= 로그파일 읽기 종료 ========================");
        log.info("\n");
    }

    public void makeResultFile(String resultPath) {
        log.info("\n");
        log.info("============= 3. 결과 파일 생성 시작, 경로 [{}]============", resultPath);
        FileUtils.write(resultPath, this.report, (bw, rp) -> {
            try {
                printLog(bw, rp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        log.info("============= 결과 파일 생성 종료 =========================");
        log.info("\n");
    }

    private void printLog(BufferedWriter bw, Report report) throws IOException {
        FileUtils.println(bw, "최다호출 API KEY");
        printValue(bw, report.getApiKeyCallCountByLimit(1), "%s");
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