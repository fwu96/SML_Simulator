import com.cisc.simpletron.processor.SimpletronProcessor;
import com.cisc.simpletron.helper.ScannerHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ProcessorTests {

    private final ScannerHelper scanner = mock(ScannerHelper.class);
    private final SimpletronProcessor processor = new SimpletronProcessor(100, scanner);

    @Test
    void testProcessFileInput_add() {
        when(scanner.inputString()).thenReturn("src/test/resources/AddTwoNumbers.txt");
        when(scanner.inputInt()).thenReturn(2);
        int res = processor.process();
        Assertions.assertEquals(4, res);
    }

    @Test
    void testProcessFileInput_average() {
        when(scanner.inputString()).thenReturn("src/test/resources/AverageOfNumbers.txt");
        when(scanner.inputInt()).thenReturn(2);
        int res = processor.process();
        Assertions.assertEquals(2, res);
    }

    @Test
    void testProcessFileInput_expo() {
        when(scanner.inputString()).thenReturn("src/test/resources/ExponentialOfNumber.txt");
        when(scanner.inputInt()).thenReturn(2);
        int res = processor.process();
        Assertions.assertEquals(4, res);
    }

    @Test
    void testProcessFileInput_findGrater() {
        when(scanner.inputString()).thenReturn("src/test/resources/GraterNumber_branchNeg.txt");
        when(scanner.inputInt()).thenReturn(2);
        int res = processor.process();
        Assertions.assertEquals(2, res);
    }

    @Test
    void testProcessFileInput_multiply() {
        when(scanner.inputString()).thenReturn("src/test/resources/MultiplyTwoNumbers.txt");
        when(scanner.inputInt()).thenReturn(2);
        int res = processor.process();
        Assertions.assertEquals(4, res);
    }

    @Test
    void testProcessFileInput_division() {
        when(scanner.inputString()).thenReturn("src/test/resources/NumbersDivision.txt");
        when(scanner.inputInt()).thenReturn(2);
        int res = processor.process();
        Assertions.assertEquals(1, res);
    }

    @Test
    void testProcessFileInput_minus() {
        when(scanner.inputString()).thenReturn("src/test/resources/NumbersSubtraction.txt");
        when(scanner.inputInt()).thenReturn(2);
        int res = processor.process();
        Assertions.assertEquals(0, res);
    }

    @Test
    void testProcessFileInput_module() {
        when(scanner.inputString()).thenReturn("src/test/resources/RemainderOfTwoNumbers.txt");
        when(scanner.inputInt()).thenReturn(2);
        int res = processor.process();
        Assertions.assertEquals(0, res);
    }

}
