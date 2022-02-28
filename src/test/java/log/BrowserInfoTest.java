package log;

import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrowserInfoTest {
    @Disabled
    public void 브라우저_() throws Exception {
        //given
        String name = "IE";
        int browserCallCount = 123;
        int totalCallCount = 1455;

        //when
        BrowserLog browserInfo = new BrowserLog("IE", browserCallCount, totalCallCount);

        //then
        assertEquals(browserInfo.getRtoPerCent(), 8);
    }
}