package log;

import log.domain.Report;
import log.dto.LogResultDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportTest {
    private static final String FILE_NAME = "src/main/resources/input.log";

    static Report report;

    @BeforeAll // 클래스실행시 1회만 실행됨
    static void init() {
        report = LogReader.read(FILE_NAME);
    }

    @Test
    public void 최다호출_apikey_조회() throws Exception {
        //when
        String maxCallApiKey = report.getApiKeyByLimit(1).get(0).getName();

        //then
        assertEquals(maxCallApiKey, "e3ea");
    }

    @Test
    public void 최대호출_상위_3개_api_server_id() throws Exception {
        //when
        List<LogResultDto> list = report.getApiServerIdByLimit(3);

        //then
        assertEquals(list.size(), 3);
        assertEquals(list.get(0).getName(), "knowledge");
        assertEquals(list.get(1).getName(), "news");
        assertEquals(list.get(2).getName(), "blog");
    }
    
    @Test
    public void 웹브라우저_사용비율() throws Exception {
        //when
        List<LogResultDto> list = report.getBrowserUseRatio();

        //then
        assertEquals(list.get(0).getName(), "IE");
        assertEquals(list.get(1).getName(), "Firefox");
        assertEquals(list.get(2).getName(), "Opera");
        assertEquals(list.get(3).getName(), "Chrome");
        assertEquals(list.get(4).getName(), "Safari");
        assertEquals(list.get(5).getValue(), 85);
        assertEquals(list.get(6).getValue(), 7);
        assertEquals(list.get(7).getValue(), 3);
        assertEquals(list.get(8).getValue(), 3);
        assertEquals(list.get(9).getValue(), 2);
    }

    @Test
    public void 결과_출력_테스트() throws Exception {
        //given
        String resultPath = "log-report.txt";

        //when
        report.makeResultFile(resultPath);

        //then
    }
}