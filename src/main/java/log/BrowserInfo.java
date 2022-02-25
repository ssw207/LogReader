package log;

public class BrowserInfo {
    private String name;
    private Double rto;

    public BrowserInfo(String name, Double rto) {
        this.name = name;
        this.rto = rto;
    }

    public String getName() {
        return name;
    }

    public Double getRto() {
        return rto;
    }
}
