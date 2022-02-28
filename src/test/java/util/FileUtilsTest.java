package util;

import log.domain.Report;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilsTest {
    private static final String PATH = "src/main/resources/input.log";

    @Test
    public void 파일읽기테스트() throws Exception {
        //when
        Report result = FileUtils.read(PATH, new Report(), (report, line) -> {report.add(line);});

        //then
        assertEquals(result.getTotalCallCount(), 5000);
    }
}