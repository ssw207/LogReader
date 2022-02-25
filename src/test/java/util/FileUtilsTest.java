package util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilsTest {
    private static final String PATH = "";
    @Test
    public void 파일_읽기_테스트() throws Exception {
        //given
        List<String> lines = FileUtils.readLines(PATH);

        //then
        assertEquals(lines.size(), 500);
    }

}