import com.cisc.simpletron.processor.SimpletronProcessor;
import com.cisc.simpletron.scanner.ScannerHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ProcessorTests {

    private final ScannerHelper scanner = mock(ScannerHelper.class);
    private final SimpletronProcessor processor = new SimpletronProcessor(100, scanner);

    @Test
    void testProcessFileInput() {
        when(scanner.inputString()).thenReturn("src/test/resources/AddTwoNumbers.txt");
        when(scanner.inputInt()).thenReturn(2);
        int res = processor.process();
        Assertions.assertEquals(4, res);
    }

}
