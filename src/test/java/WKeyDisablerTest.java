import org.junit.jupiter.api.Test;
import windows.WKeyDisabler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WKeyDisablerTest {

    @Test
    public void testCommand() {
        new WKeyDisabler();
    }
}