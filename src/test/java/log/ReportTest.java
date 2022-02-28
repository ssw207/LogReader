package log;

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
        String maxCallApiKey = report.getMaxCallApiKey();

        //then
        assertEquals(maxCallApiKey, "e3ea");
    }

    @Test
    public void 최대호출_상위_3개_api_server_id() throws Exception {
        //when
        List<String> list = report.getApiServerIdByCntReqUpTo(3);

        //then
        assertEquals(list.size(), 3);
        assertEquals(list.get(0), "knowledge");
        assertEquals(list.get(1), "news");
        assertEquals(list.get(2), "blog");
    }
    
    @Test
    public void 웹브라우저_사용비율() throws Exception {
        //when
        List<BrowserInfo> lists = report.getBrowserUseRatio();

        BrowserInfo browser1 = lists.get(0);
        BrowserInfo browser2 = lists.get(1);
        BrowserInfo browser3 = lists.get(2);
        BrowserInfo browser4 = lists.get(3);
        BrowserInfo browser5 = lists.get(4);

        //then
        assertEquals(browser1.getName(), "IE");
        assertEquals(browser2.getName(), "Firefox");
        assertEquals(browser3.getName(), "Opera");
        assertEquals(browser4.getName(), "Chrome");
        assertEquals(browser5.getName(), "Safari");

        assertEquals(browser1.getRtoPerCent(), 85);
        assertEquals(browser2.getRtoPerCent(), 7);
        assertEquals(browser3.getRtoPerCent(), 3);
        assertEquals(browser4.getRtoPerCent(), 3);
        assertEquals(browser5.getRtoPerCent(), 2);
    }

    @Test
    public void 결과_출력_테스트() throws Exception {
        //given
        String resultPath = "";

        //when
        report.makeResultFile(resultPath);

        //then
    }
}