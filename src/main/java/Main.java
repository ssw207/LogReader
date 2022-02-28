import log.LogReader;
import log.domain.Report;

public class Main {
    private static final String DEFAULT_SRC_PATH = "src/main/java/resources/input.log";
    private static final String DEFAULT_DES_PATH = "log-report.txt"; // 현재 프로젝트경로에 생성됨

    public static void main(String[] args) {
        Main main = new Main();
        
        switch (args.length) {
            case 0 : main.proc(DEFAULT_SRC_PATH, DEFAULT_DES_PATH); break;
            case 1 : main.proc(args[0], DEFAULT_DES_PATH); break;
            case 2 : main.proc(args[0], args[1]); break;
            default: throw new IllegalArgumentException("0 ~ 2개의 변수를 입력해주세요 1:로그파일경로, 2:출력파일경로");
        }
    }

    public void proc(String srcPath, String desPath) {
        LogReader logReader = new LogReader();

        Report report = logReader.read(srcPath);
        report.makeResultFile(desPath);
    }
}
