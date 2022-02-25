package log;

public class LogInfo {

    private String status;
    private String url;
    private String browser;
    private String callTime;
    private String apiServerId;

    public static LogInfo of(String line) {
        String newLine = line.substring(1, line.length()-1); // 앞과 끝의 [] 제거
        String[] tokens = newLine.split("\\]\\["); // ][ 기준으로 쪼개기

        return new LogInfo(tokens[0], tokens[1], tokens[2], tokens[3]);
    }

    public LogInfo(String status, String url, String browser, String callTime) {
        this.status = status;
        this.url = url;
        this.browser = browser;
        this.callTime = callTime;

        this.apiServerId = "";
    }

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
}
