package log.domain;

import log.dto.LogResultDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;


import static log.domain.LogInfo.API_KEY;

public class Report {

    private static final Logger log = LoggerFactory.getLogger(Report.class);

    private long totalCallCount = 0L; // 전체 api 호출 횟수
    private Map<String, Long> apiCallCountByApikey = new HashMap<>(); // apikey별 호출횟수
    private Map<String, Long> apiCallCountByApiServerId = new HashMap<>(); // apiserverId별 호출횟수
    private Map<String, Long> apiCallCountByBrowser = new HashMap<>(); // 브라우저별 호출 횟수
    private final LogInfo logInfo = new LogInfo(); // 로그정보

    public long getTotalCallCount() {
        return totalCallCount;
    }

    public void add(String line) {
        if (StringUtils.isEmpty(line)) {
            log.debug("입력값이 없음");
            return;
        }

        logInfo.add(line); // 로그정보 입력

        addCount(apiCallCountByApikey, logInfo.getParam(API_KEY));
        addCount(apiCallCountByApiServerId, logInfo.getApiServerId());
        addCount(apiCallCountByBrowser, logInfo.getBrowser());
        totalCallCount++;

        logInfo.clear(); // 로그정보 초기화
    }

    public void sortAll() {
        apiCallCountByApikey = getSortedLinkedHashMap(apiCallCountByApikey);
        apiCallCountByApiServerId = getSortedLinkedHashMap(apiCallCountByApiServerId);
        apiCallCountByBrowser = getSortedLinkedHashMap(apiCallCountByBrowser);

        log.debug("====================== Report 결과 정렬 시작 ==================");
        log.debug("apiCallCountByApikey [{}]",apiCallCountByApikey);
        log.debug("apiCallCountByApiServerId [{}]",apiCallCountByApiServerId);
        log.debug("apiCallCountByBrowser [{}]",apiCallCountByBrowser);
        log.debug("====================== Report 결과 정렬 종료 ==================");
    }

    private Map<String, Long> getSortedLinkedHashMap(Map<String, Long> unsortMap) {
        Comparator<Map.Entry<String, Long>> comparator = (e1,e2) -> Long.compare(e2.getValue(), e1.getValue());

        return unsortMap.entrySet().stream()
                .sorted(comparator) // DESC 정렬
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public void addCount(Map<String, Long> map, String key) {
        if (StringUtils.isEmpty(key)) { // key가 없는경우 {key : "", value:160} 이런식으로 체크되므로 수정처리
            return;
        }

        long cnt = map.getOrDefault(key, 0L);
        map.put(key, cnt + 1);
    }

    /**
     * 최다호출 apikey 조회
     * - [200][http://apis.daum.net/search/knowledge?apikey=23jf&q=daum][IE][2012-06-10 08:00:00]
     * - url에서 파라미터 apikey 값을 추출해 apikey별 갯수를 count함 (map이용)
     * - 람다식 이용
     */
    public List<LogResultDto> getApiKeyByLimit(int limit) {
        return toDtoByLimit(apiCallCountByApikey, limit);
    }

    public List<LogResultDto> getApiServerIdByLimit(int limit) {
        return toDtoByLimit(apiCallCountByApiServerId, limit);
    }

    private List<LogResultDto> toDtoByLimit(Map<String, Long> map, int limit) {
        return map.entrySet().stream()
                .limit(limit)
                .map(e -> new LogResultDto(LogResultDto.API_SERVICE_ID, e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * 브라우저 사용비율 조회
     * - 브라우저 사용비율은 = 브라우저로그수/전체로그수
     * - 최초 로그를 읽어올때 브라우저를 카운팅 (Map이용)
     * - 전체 카운팅필요
     *
     */
    public List<LogResultDto> getBrowserUseRatio() {
        return apiCallCountByBrowser.entrySet()
                .stream()
                .map(e -> new LogResultDto(LogResultDto.BROWSER, e.getKey(), getBrowserUseRto(e.getValue(), totalCallCount)))
                .collect(Collectors.toList());
    }

    public void makeResultFile(String resultPath) {
        BiConsumer<BufferedWriter, Report> func = (bw, report) -> {
            try {
                printLog(bw, report);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        FileUtils.write(resultPath, this, func);
    }

    private void printLog(BufferedWriter bw, Report report) throws IOException {
        FileUtils.println(bw, "최다호출 API KEY");
        printValue(bw, report.getApiKeyByLimit(1));
        bw.newLine();

        int limit = 3;
        FileUtils.println(bw, String.format("상위 %d개의 API Service ID와 각각의 요청 수", limit));
        printValue(bw, report.getApiServerIdByLimit(limit));
        bw.newLine();

        FileUtils.println(bw, "웹브라우저별 사용 비율");
        printValue(bw, report.getBrowserUseRatio(), "%s : %d%%");
        bw.newLine();
    }

    private void printValue(BufferedWriter bw, List<LogResultDto> list) {
        printValue(bw, list, "%s : %d");
    }

    private void printValue(BufferedWriter bw, List<LogResultDto> list, String format) {
        list.forEach(dto -> FileUtils.println(bw, String.format(format, dto.getName(), dto.getValue())));
    }

    public long getBrowserUseRto(Long browserCallCount, Long totalCallCount) {
        double rto = Double.valueOf(browserCallCount) / Double.valueOf(totalCallCount);
        return Math.round(rto * 100);
    }
}
