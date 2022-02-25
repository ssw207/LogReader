package util;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    private static final String ERROR_PATH = "input.log";
    private static final String PATH = "/input.log";

    @Test
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
    public void 파일_읽기_테스트2() throws Exception {
        //given
        List<String> lines = FileUtils.readResourcesLines(PATH);

        //then
        assertEquals(lines.size(), 5000);
    }
    
    @Test
    public void 파일_읽기_실패_테스트() throws Exception {
        //given
        assertThrows(IllegalArgumentException.class, () -> {
            FileUtils.readResourcesLines(ERROR_PATH);
        });
    }
}