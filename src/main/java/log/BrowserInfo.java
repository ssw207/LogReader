package log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BrowserInfo {

    private String name;
    private Integer browserCallCount;
    private double rto;

    public BrowserInfo(String name, Integer browserCallCount, Integer totalCallCount) {
        this.name = name;
        this.browserCallCount = browserCallCount;

        calcRto(totalCallCount);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    private void calcRto(Integer totalCallCount) {
        this.rto = (Double.valueOf(browserCallCount) / Double.valueOf(totalCallCount));
    }

    public String getName() {
        return name;
    }

    public Integer getBrowserCallCount() {
        return browserCallCount;
    }

    public double getRto() {
        return rto;
    }

    public long getRtoPerCent() {
        return Math.round(rto * 100);
    }
}
