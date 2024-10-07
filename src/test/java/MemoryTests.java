import com.cisc.simpletron.memory.SimpletronMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MemoryTests {

    private final SimpletronMemory memory = new SimpletronMemory(100);

    @Test
    void testSaveVal() {
        Assertions.assertDoesNotThrow(() ->
                memory.saveVal(0, 1));
        Assertions.assertEquals(1, memory.getVal(0));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                memory.saveVal(100, 0));
    }

    @Test
    void testGetVal() {
        Assertions.assertEquals(0, memory.getVal(0));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                memory.getVal(100));
    }

    @Test
    void testGetSize() {
        Assertions.assertEquals(100, memory.getSize());
    }

}
