package log;

import log.domain.LogInfo;
import org.junit.jupiter.api.Test;

import static log.domain.LogInfo.API_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogInfoTest {

    @Test
    public void 로그정보_생성() throws Exception {
        //given
        String line = "[200][http://apis.daum.net/search/knowledge?apikey=23jf&q=daum][IE][2012-06-10 08:00:00]";

        //when
        LogInfo logInfo = new LogInfo();
        logInfo.add(line);

        assertEquals(logInfo.getStatus(), "200");
        assertEquals(logInfo.getUrl(), "http://apis.daum.net/search/knowledge?apikey=23jf&q=daum");
        assertEquals(logInfo.getBrowser(), "IE");
        assertEquals(logInfo.getCallTime(), "2012-06-10 08:00:00");
        assertEquals(logInfo.getApiServerId(), "knowledge");
        assertEquals(logInfo.getParam("apikey"), "23jf");
    }

    @Test
    public void 파라미터가_없는경우_로그정보_생성() throws Exception {
        //given
        String line = "[200][http://apis.daum.net/search/knowledge][IE][2012-06-10 08:00:00]";

        //when
        LogInfo logInfo = new LogInfo();
        logInfo.add(line);

        assertEquals(logInfo.getStatus(), "200");
        assertEquals(logInfo.getUrl(), "http://apis.daum.net/search/knowledge");
        assertEquals(logInfo.getBrowser(), "IE");
        assertEquals(logInfo.getCallTime(), "2012-06-10 08:00:00");
        assertEquals(logInfo.getApiServerId(), "knowledge");
        assertEquals(logInfo.getParam("apikey"), "");
    }

    @Test
    public void URI가_없는경우_로그정보_생성() throws Exception {
        //given
        String line = "[200][http://apis.daum.net/search/knowledge/][IE][2012-06-10 08:00:00]";

        //when
        LogInfo logInfo = new LogInfo();
        logInfo.add(line);

        assertEquals(logInfo.getStatus(), "200");
        assertEquals(logInfo.getUrl(), "http://apis.daum.net/search/knowledge/");
        assertEquals(logInfo.getBrowser(), "IE");
        assertEquals(logInfo.getCallTime(), "2012-06-10 08:00:00");
        assertEquals(logInfo.getApiServerId(), "knowledge");
        assertEquals(logInfo.getParam(API_KEY), "");
    }
}