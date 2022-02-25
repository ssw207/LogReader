package log;

import util.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogReader {
    private String fileName;
    private List<LogInfo> logInfos = new ArrayList<>();

    public LogReader(String fileName) {
        this.fileName = fileName;
    }

    public List<RequestInfo> getApiServerIdByCntReqUpTo(int i) {
        return null;
    }

    public List<LogInfo> getLogInfos() {
        return logInfos;
    }

    public String getMaxCallApiKey() {
//        Comparator<LogInfo> comp1 = (s1, s2) -> s1.get.compareToIgnoreCase(s2);
//
//        Optional<LogInfo> max = logInfos.stream().max(comp1);
//        return max.get().getApiServerId();
        return null;
    }

    public void load() {
        List<String> lines = FileUtils.readResourcesLines(fileName);

        this.logInfos = lines.stream()
                .map(LogInfo::of)
                .collect(Collectors.toList());
    }

    public List<BrowserInfo> getBrowserUseRatio() {
        return null;
    }
}
