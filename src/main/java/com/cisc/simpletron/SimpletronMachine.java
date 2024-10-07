package com.cisc.simpletron;

import com.cisc.simpletron.processor.SimpletronProcessor;
import com.cisc.simpletron.scanner.ScannerHelper;

/**
 * Main class to launch the Simpletron Machine
 *
 * @author Feifan Wu
 * @version 3.0
 */
public class SimpletronMachine {

    private static final SimpletronProcessor processor = new SimpletronProcessor(100, new ScannerHelper());

    public static void main(String[] args) {
        printWelcomePrompts();
        processor.process();
    }

    private static void printWelcomePrompts() {
        System.out.println();
        System.out.println("*** Welcome to Simpletron! ***");
        System.out.println("*** Please enter your program one instruction ***");
        System.out.println("*** (or data word) at a time. I will type the ***");
        System.out.println("*** location number and a question mark (?). ***");
        System.out.println("*** You then type the word for that location. ***");
        System.out.println("*** Type the sentinel -99999 to stop entering ***");
        System.out.println("*** your program. ***");
        System.out.println();
    }
}
