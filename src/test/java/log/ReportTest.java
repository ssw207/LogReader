package log;

import log.domain.Report;
import log.dto.LogResultDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.FileUtils;

import java.util.List;
import java.util.function.BiConsumer;

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
        String maxCallApiKey = report.getApiKeyCallCountByLimit(1).get(0).getName();

        //then
        assertEquals( "e3ea", maxCallApiKey);
    }

    @Test
    public void 최대호출_상위_3개_api_server_id() throws Exception {
        //when
        List<LogResultDto> list = report.getApiServerCallCountIdByLimit(3);

        //then
        assertEquals(3, list.size() );
        assertEquals("knowledge", list.get(0).getName());
        assertEquals("news", list.get(1).getName());
        assertEquals("blog", list.get(2).getName());
    }
    
    @Test
    public void 웹브라우저_사용비율() throws Exception {
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
    public void 결과_출력_테스트() throws Exception {
        //given
        String resultPath = "log-report.txt";
        String resultStr = "최다호출 API KEYe3ea : 493상위 3개의 API Service ID와 각각의 요청 수knowledge : 836news : 834blog : 826웹브라우저별 사용 비율IE : 85%Firefox : 7%Opera : 3%Chrome : 3%Safari : 2%";
        StringBuilder result = new StringBuilder();

        BiConsumer<StringBuilder, String> func = (sb, line) -> {
            sb.append(line);
        };

        //when
        report.makeResultFile(resultPath);

        //then
        FileUtils.read(resultPath, result, func);
        assertEquals(resultStr, result.toString());
    }
}