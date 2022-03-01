package com.my.log;

import com.my.log.domain.Report;
import com.my.log.dto.LogResultDto;
import com.my.util.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportTest {
    private static final String SRC_PATH = LogReader.DEFAULT_SRC_PATH;
    private static final String DESC_PATH = LogReader.DEFAULT_DES_PATH;

    private static Report report;
    private static LogReader logReader;

    @BeforeAll // 클래스실행시 1회만 실행됨
    static void init() {
        LogReader lr = new LogReader();
        lr.read(SRC_PATH);

        report = lr.getReport();
        logReader = lr;
    }

    @Test
    void 최다호출_apikey_조회() {
        //when
        String maxCallApiKey = report.getApiKeyCallCountByLimit(1).get(0).getName();

        //then
        assertEquals( "e3ea", maxCallApiKey);
    }

    @Test
    void 최대호출_상위_3개_api_server_id() {
        //when
        List<LogResultDto> list = report.getApiServerCallCountIdByLimit(3);

        //then
        assertEquals(3, list.size() );
        assertEquals("knowledge", list.get(0).getName());
        assertEquals("news", list.get(1).getName());
        assertEquals("blog", list.get(2).getName());
    }
    
    @Test
    void 웹브라우저_사용비율() {
        //when
        List<LogResultDto> list = report.getBrowserUseRatio();

        //then
        assertEquals( "IE", list.get(0).getName());
        assertEquals( "Firefox", list.get(1).getName());
        assertEquals( "Opera", list.get(2).getName());
        assertEquals( "Chrome", list.get(3).getName());
        assertEquals( "Safari", list.get(4).getName());
        assertEquals(85, list.get(0).getValue());
        assertEquals(7, list.get(1).getValue());
        assertEquals(3, list.get(2).getValue());
        assertEquals(3, list.get(3).getValue());
        assertEquals(2, list.get(4).getValue());
    }

    @Test
    void 결과_출력_테스트() {
        //given
        String resultStr = "최다호출 API KEYe3ea 493상위 3개의 API Service ID와 각각의 요청 수knowledge : 836news : 834blog : 826웹브라우저별 사용 비율IE : 85%Firefox : 7%Opera : 3%Chrome : 3%Safari : 2%";
        StringBuilder result = new StringBuilder();

        //when
        logReader.makeResultFile(DESC_PATH);

        //then
        FileUtils.read(DESC_PATH, result, (sb, line) -> sb.append(line));
        assertEquals(resultStr, result.toString());
    }
}