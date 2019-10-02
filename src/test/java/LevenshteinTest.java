import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LevenshteinTest {

    @Test
    public void CanCalculateLevenshtein() {
        var testString = "apples";
        var compareString = "spelling";
        var distance = Levenshtein.distance(testString, compareString);

        assertEquals(6, distance);
    }
}
