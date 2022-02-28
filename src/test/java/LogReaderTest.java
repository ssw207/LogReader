import log.BrowserInfo;
import log.LogReader;
import log.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogReaderTest {
    private static final String FILE_NAME = "src/main/resources/input.log";

    Report report;

    @BeforeEach // 클래스실행시 1회만 실행됨
    public void init() {
        System.out.println("LogReaderTest.init");
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
        assertEquals(list, 3);
        assertEquals(list.get(0), "blog");
        assertEquals(list.get(1), "vclip");
        assertEquals(list.get(2), "image");
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
        assertEquals(browser2.getName(), "Chrome");
        assertEquals(browser3.getName(), "Safari");
        assertEquals(browser4.getName(), "Firefox");
        assertEquals(browser5.getName(), "Opera");

        assertEquals(browser1.getRto(), 60);
        assertEquals(browser2.getRto(), 20);
        assertEquals(browser3.getRto(), 10);
        assertEquals(browser4.getRto(), 7);
        assertEquals(browser5.getRto(), 3);
    }
}