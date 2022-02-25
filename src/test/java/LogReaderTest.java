import log.BrowserInfo;
import log.LogInfo;
import log.LogReader;
import log.RequestInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogReaderTest {
    private static final String FILE_NAME = "/input.log";

    LogReader logReader;

    @BeforeEach
    public void init() {
        logReader = new LogReader(FILE_NAME);
        logReader.load(); // 로그파일을 읽는다.
    }

    @Test
    public void 파일_읽기_테스트() throws Exception {
        //when
        List<LogInfo> list = logReader.getLogInfos();

        //then
        assertEquals(list.size(), 5000);
    }
    
    @Test
    public void 최다호출_apikey_조회() throws Exception {
        //when
        String key = logReader.getMaxCallApiKey();

        //then
        assertEquals(key, "f83e");
    }
    
    @Test
    public void 최대호출_상위_3개_api_server_id() throws Exception {

        //when
        List<RequestInfo> list = logReader.getApiServerIdByCntReqUpTo(3);

        RequestInfo req1 = list.get(0);
        RequestInfo req2 = list.get(1);
        RequestInfo req3 = list.get(2);

        //then
        assertEquals(list, 3);

        assertEquals(req1.getApiServerId(), "blog");
        assertEquals(req2.getApiServerId(), "vclip");
        assertEquals(req3.getApiServerId(), "image");

        assertEquals(req1.getCountRequest(), 1224);
        assertEquals(req2.getCountRequest(), 871);
        assertEquals(req3.getCountRequest(), 705);
    }
    
    @Test
    public void 웹브라우저_사용비율() throws Exception {
        //when
        List<BrowserInfo> lists = logReader.getBrowserUseRatio();

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