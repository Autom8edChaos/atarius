import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SoundexTest {

    @Test
    public void firstCharacterIsRetained() {
        var testString = "Bubbles";
        String result = Soundex.soundex(testString);
        assertEquals("B", result.substring(0,1));
    }
}
