package com.my.log;

import com.my.log.domain.LogInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogInfoTest {

    @Test
    void 로그정보_생성() {
        //given
        String line = "[200][http://apis.daum.net/search/knowledge?apikey=23jf&q=daum][IE][2012-06-10 08:00:00]";

        //when
        LogInfo logInfo = new LogInfo();
        logInfo.add(line);

        assertEquals( "200",logInfo.getStatus());
        assertEquals( "http://apis.daum.net/search/knowledge?apikey=23jf&q=daum",logInfo.getUrl());
        assertEquals( "IE",logInfo.getBrowser());
        assertEquals( "2012-06-10 08:00:00",logInfo.getCallTime());
        assertEquals( "knowledge",logInfo.getApiServerId());
        assertEquals("23jf",logInfo.getParam("apikey"));
    }

    @Test
    void 파라미터가_없는경우_로그정보_생성() {
        //given
        String line = "[200][http://apis.daum.net/search/knowledge][IE][2012-06-10 08:00:00]";

        //when
        LogInfo logInfo = new LogInfo();
        logInfo.add(line);

        assertEquals("200",logInfo.getStatus());
        assertEquals("http://apis.daum.net/search/knowledge",logInfo.getUrl());
        assertEquals("IE",logInfo.getBrowser());
        assertEquals("2012-06-10 08:00:00",logInfo.getCallTime());
        assertEquals("knowledge",logInfo.getApiServerId());
        assertEquals("", logInfo.getParam("apikey"));
    }

    @Test
    void URI가_없는경우_로그정보_생성() {
        //given
        String line = "[200][http://apis.daum.net/search/knowledge/][IE][2012-06-10 08:00:00]";

        //when
        LogInfo logInfo = new LogInfo();
        logInfo.add(line);

        assertEquals("200",logInfo.getStatus());
        assertEquals("http://apis.daum.net/search/knowledge/",logInfo.getUrl());
        assertEquals("IE",logInfo.getBrowser());
        assertEquals("2012-06-10 08:00:00",logInfo.getCallTime());
        assertEquals("knowledge",logInfo.getApiServerId());
        assertEquals("", logInfo.getParam("apikey"));
    }
}