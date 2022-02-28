package log.dto;

public class LogResultDto {

    public static final String BROWSER = "01";
    public static final String API_SERVICE_ID = "02";
    public static final String API_KEY = "03";

    private String type;
    private String name;
    private Long value;

    public LogResultDto(String type, String name, Long value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public LogResultDto() {
    }

    public String getPrintStr() {
        switch (this.type) {
            case BROWSER: return String.format("%s : %d%%", name, value);
            case API_SERVICE_ID: return String.format("%s : %d", name, value);
            case API_KEY: return String.format("%s : %d", name, value);
            default: throw new IllegalArgumentException("지정된 타입이 아닙니다");
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
