package log;

import org.junit.jupiter.api.Test;

import static log.LogInfo.API_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogInfoTest {
    @Test
    public void 정규식_테스트() throws Exception {
        //given
        String line = "[200][http://apis.daum.net/search/knowledge?apikey=23jf&q=daum][IE][2012-06-10 08:00:00]";

        //when
        line = line.substring(1, line.length()-1); // 앞과 끝의 [] 제거
        String[] split = line.split("\\]\\["); // ][ 기준으로 쪼개기

        //then
        assertEquals(split.length, 4);
        assertEquals(split[0], "200");
    }

    @Test
    public void LogInfo_생성_테스트() throws Exception {
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
    public void LogInfo_생성_테스트2() throws Exception {
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
    public void LogInfo_생성_테스트3() throws Exception {
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