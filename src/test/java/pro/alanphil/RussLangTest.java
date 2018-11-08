package pro.alanphil;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static pro.alanphil.CommonTasks.checkType;


/**
 * Unit test for simple RussLang.
 */
class RussLangTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    void checkTypeTest() {
        assertTrue(checkType(new ArrayList<>(Arrays.asList("1", "2", "3")), new ArrayList<>(), "1"));
    }
}
