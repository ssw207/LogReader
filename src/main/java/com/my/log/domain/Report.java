package com.my.log.domain;

import com.my.log.dto.LogResultDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Report {

    private static final Logger log = LoggerFactory.getLogger(Report.class);

    private static final String API_KEY = "apikey";

    private long totalCallCount = 0L; // 전체 api 호출 횟수
    private Map<String, Long> apiCallCountByApikey = new HashMap<>(); // apikey별 호출횟수
    private Map<String, Long> apiCallCountByApiServerId = new HashMap<>(); // apiserverId별 호출횟수
    private Map<String, Long> apiCallCountByBrowser = new HashMap<>(); // 브라우저별 호출 횟수
    private final LogInfo logInfo = new LogInfo(); // 로그정보

    public long getTotalCallCount() {
        return totalCallCount;
    }

    //=============================================== Report 생성로직 시작 ===============================

    /**
     * 로그정보 추가
     * @param line
     */
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

    /**
     * 로그정보 key별로 카운팅
     * @param map
     * @param key
     */
    private void addCount(Map<String, Long> map, String key) {
        if (StringUtils.isEmpty(key)) { // key가 없는경우 {key : "", value:160} 이런식으로 체크되므로 수정처리
            return;
        }

        long cnt = map.getOrDefault(key, 0L);
        map.put(key, cnt + 1);
    }

    /**
     * 로그정보 정렬
     */
    public void sortAll() {
        apiCallCountByApikey = getSortedLinkedHashMap(apiCallCountByApikey);
        apiCallCountByApiServerId = getSortedLinkedHashMap(apiCallCountByApiServerId);
        apiCallCountByBrowser = getSortedLinkedHashMap(apiCallCountByBrowser);

        log.info("\n");
        log.info("====================== 2. 로그정보 분석 결과 ==================");
        log.info("apikey 호출 횟수 [{}]",apiCallCountByApikey);
        log.info("ApiServerId 호출 횟수 [{}]",apiCallCountByApiServerId);
        log.info("Browser 호출 횟수 [{}]",apiCallCountByBrowser);
        log.info("=======================================================");
        log.info("\n");
    }

    /**
     * 로그정보 Map을 정렬후 리턴
     * @param unsortMap
     * @return
     */
    private Map<String, Long> getSortedLinkedHashMap(Map<String, Long> unsortMap) {
        Comparator<Map.Entry<String, Long>> comparator = (e1,e2) -> Long.compare(e2.getValue(), e1.getValue());

        return unsortMap.entrySet().stream()
                .sorted(comparator) // DESC 정렬
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    //=============================================== Report 생성로직 끝 ===============================

    //=============================================== Report 조회로직 시작 ===============================
    /**
     * 브라우저 사용비율 조회
     * - 브라우저 사용비율은 = 브라우저로그수/전체로그수
     * - 최초 로그를 읽어올때 브라우저를 카운팅
     */
    public List<LogResultDto> getBrowserUseRatio() {
        return apiCallCountByBrowser.entrySet()
                .stream()
                .map(e -> new LogResultDto(e.getKey(), getBrowserUseRto(e.getValue(), totalCallCount)))
                .collect(Collectors.toList());
    }

    private long getBrowserUseRto(Long browserCallCount, Long totalCallCount) {
        double rto = Double.valueOf(browserCallCount) / Double.valueOf(totalCallCount);
        return Math.round(rto * 100);
    }

    /**
     * apikey 호출수 DESC 조회
     * - [200][http://apis.daum.net/search/knowledge?apikey=23jf&q=daum][IE][2012-06-10 08:00:00]
     * - url에서 파라미터 apikey 값을 추출해 apikey별 건수를 리턴
     */
    public List<LogResultDto> getApiKeyCallCountByLimit(int limit) {
        return toDtoByLimit(apiCallCountByApikey, limit);
    }

    /**
     * apiServer 호출수 DESC 조회
     * @param limit
     * @return
     */
    public List<LogResultDto> getApiServerCallCountIdByLimit(int limit) {
        return toDtoByLimit(apiCallCountByApiServerId, limit);
    }

    private List<LogResultDto> toDtoByLimit(Map<String, Long> map, int limit) {
        return map.entrySet().stream()
                .limit(limit)
                .map(e -> new LogResultDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    //=============================================== Report 조회로직 끝 ===============================
}
