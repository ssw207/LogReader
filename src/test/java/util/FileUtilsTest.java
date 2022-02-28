package util;

import log.Report;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileUtilsTest {

    private static final String ERROR_PATH = "input.log";
    private static final String PATH = "src/main/resources/input.log";

    @Disabled
    public void 파일_읽기_테스트1() throws Exception {
        //given
        InputStream InputStream = FileUtilsTest.class.getResourceAsStream(PATH);
        InputStreamReader inputStreamReader = new InputStreamReader(InputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        //when
        String line = bufferedReader.readLine();

        //then
        assertNotNull(line);
    }
    
    @Test
    public void 파일읽기테스트() throws Exception {
        //when
        Report result = FileUtils.read(PATH, new Report(), (report, line) -> {report.add(line);});

        //then
        assertEquals(result.getTotalCallCount(), 5000);
    }
}