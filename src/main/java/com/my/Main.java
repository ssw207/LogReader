package com.my;

import com.my.log.LogReader;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        
        switch (args.length) {
            case 0 : main.proc(LogReader.DEFAULT_SRC_PATH, LogReader.DEFAULT_DES_PATH); break;
            case 1 : main.proc(args[0], LogReader.DEFAULT_DES_PATH); break;
            case 2 : main.proc(args[0], args[1]); break;
            default: throw new IllegalArgumentException("0 ~ 2개의 변수를 입력해주세요 1:로그파일경로, 2:출력파일경로");
        }
    }

    public void proc(String srcPath, String desPath) {
        LogReader logReader = new LogReader();
        logReader.read(srcPath);
        logReader.makeResultFile(desPath);
    }
}
