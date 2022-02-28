package log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;

import static log.LogInfo.API_KEY;

public class Report {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    private int totalCallCount = 0; // 전체 api 호출 횟수
    private Map<String, Integer> apiCallCountByApikey = new HashMap<>(); // apikey별 호출횟수
    private Map<String, Integer> apiCallCountByApiServerId = new HashMap<>(); // apiserverId별 호출횟수
    private Map<String, Integer> apiCallCountByBrowser = new HashMap<>(); // 브라우저별 호출 횟수
    private LogInfo logInfo = new LogInfo(); // 로그정보

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
        log.debug("apiCallCountByApikey [{}]",apiCallCountByApikey.toString());
        log.debug("apiCallCountByApiServerId [{}]",apiCallCountByApiServerId.toString());
        log.debug("apiCallCountByBrowser [{}]",apiCallCountByBrowser.toString());
        log.debug("====================== Report 결과 정렬 종료 ==================");
    }

    private Map<String, Integer> getSortedLinkedHashMap(Map<String, Integer> unsortMap) {
        Comparator<Map.Entry<String, Integer>> comparator = (e1,e2) -> Integer.compare(e2.getValue(), e1.getValue());

        return unsortMap.entrySet().stream()
                .sorted(comparator) // DESC 정렬
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public void addCount(Map<String, Integer> map, String key) {
        if (StringUtils.isEmpty(key)) { // key가 없는경우 {key : "", value:160} 이런식으로 체크되므로 수정처리
            return;
        }

        Integer cnt = map.getOrDefault(key, 0);
        map.put(key, cnt + 1);
    }

    /****************************************************************************************
     * 통계정보 계산
     */

    public int getTotalCallCount() {
        return totalCallCount;
    }

    /**
     * 최다호출 apikey 조회
     * - [200][http://apis.daum.net/search/knowledge?apikey=23jf&q=daum][IE][2012-06-10 08:00:00]
     * - url에서 파라미터 apikey 값을 추출해 apikey별 갯수를 count함 (map이용)
     * - 람다식 이용
     *
     * @return
     */
    public String getMaxCallApiKey() {
        return apiCallCountByApikey.entrySet().stream().findFirst().map(Map.Entry::getKey).orElse("");
    }

    public List<String> getApiServerIdByCntReqUpTo(int limit) {
        return apiCallCountByApiServerId.entrySet().stream().limit(limit).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * 브라우저 사용비율 조회
     * - 브라우저 사용비율은 = 브라우저로그수/전체로그수
     * - 최초 로그를 읽어올때 브라우저를 카운팅 (Map이용)
     * - 전체 카운팅필요
     *
     * @return
     */
    public List<BrowserInfo> getBrowserUseRatio() {
        return apiCallCountByBrowser.entrySet()
                .stream()
                .map(e -> new BrowserInfo(e.getKey(), e.getValue(), totalCallCount))
                .collect(Collectors.toList());
    }
    
    //============================================ 삭제예정 ==========================================

    @Deprecated
    public Map<String, Integer> getKeysDescByValue(Map<String, Integer> map, int limit) {
        return map.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(limit)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())); //배열의 첫번째 요소를 map의 key 두번째요소를 value로 세팅
    }

    public void makeResultFile(String resultPath) {
        FileUtils.write(resultPath);
    }
}
