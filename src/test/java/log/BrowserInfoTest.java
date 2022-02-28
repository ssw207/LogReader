package log;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrowserInfoTest {
    @Test
    public void 브라우저_() throws Exception {
        //given
        String name = "IE";
        int browserCallCount = 123;
        int totalCallCount = 1455;

        //when
        BrowserInfo browserInfo = new BrowserInfo("IE", browserCallCount, totalCallCount);

        //then
        assertEquals(browserInfo.getRtoPerCent(), 8);
    }
}