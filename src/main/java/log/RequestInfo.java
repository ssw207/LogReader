package log;

public class RequestInfo {
    private String apiServerId;
    private Integer countRequest;

    public RequestInfo(String apiServerId, Integer countRequest) {
        this.apiServerId = apiServerId;
        this.countRequest = countRequest;
    }

    public String getApiServerId() {
        return apiServerId;
    }

    public Integer getCountRequest() {
        return countRequest;
    }
}
