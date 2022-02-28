package log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LogInfo {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static final String API_KEY = "apikey";

    private String status;
    private String url;
    private String browser;
    private String callTime;
    private String apiServerId;
    private Map<String,String> params = new HashMap<>();
    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public String getApiServerId() {
        return apiServerId;
    }

    public String getBrowser() {
        return browser;
    }

    public String getCallTime() {
        return callTime;
    }

    public void add(String line) {
        String newLine = line.substring(1, line.length()-1); // 앞과 끝의 [] 제거
        String[] tokens = newLine.split("]\\["); // ][ 기준으로 쪼개기

        this.status = tokens[0];
        this.url = tokens[1];
        this.browser = tokens[2];
        this.callTime = tokens[3];

        String[] split = this.url.split("\\?");

        this.apiServerId = extractApiServerId(split[0]);

        if (split.length == 2) { // 길이가 1인경우 파라미터가 없음
            initParams(split[1]);
        }
    }

    /**
     * URL에서 apiServerId 추출
     * @param url ex)http://apis.daum.net/search/knowledge/
     * @return apiServerId
     */
    private String extractApiServerId(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }

        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1); // "/" 로 끝나는경우 마지막 문자열제거
        }

        // 마지만 "/" ~ 끝까지 추출
        return url.substring(url.lastIndexOf("/")+1);
    }

    /**
     * 파라미터 세팅
     * @param paramStr ex)apikey=23jf&q=daum
     */
    void initParams(String paramStr) {
        String[] tmpParams = paramStr.split("&");

        this.params = Arrays.stream(tmpParams)
                .map(str -> str.split("=")) // 요소를 꺼낸뒤로 "=" 로 쪼개 배열로 만듬
                .collect(Collectors.toMap(e1 -> e1[0], e2 -> e2[1])); //배열의 첫번째 요소를 map의 key 두번째요소를 value로 세팅
    }

    public String getParam(String key) {
        return params.getOrDefault(key, "");
    }

    public void clear() {
        this.status = null;
        this.url = null;
        this.browser = null;
        this.callTime = null;
        this.apiServerId = null;
        this.params.clear();
    }
}
