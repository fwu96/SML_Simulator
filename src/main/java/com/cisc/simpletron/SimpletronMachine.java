package com.cisc.simpletron;

import com.cisc.simpletron.processor.SimpletronProcessor;
import com.cisc.simpletron.scanner.ScannerHelper;

/**
 * The main class there Simpletron Machine runs
 *
 * @author Feifan Wu
 * @version 3.0
 */
public class SimpletronMachine {

    private static final SimpletronProcessor processor = new SimpletronProcessor(100, new ScannerHelper());

    public static void main(String[] args) {
        printWelcomeMessage();
        processor.process();
    }

    private static void printWelcomeMessage() {
        System.out.println("""
            *** Welcome to Simpletron! ***
            *** Please enter your program one instruction ***
            *** (or data word) at a time. I will type the ***
            *** text field. I will display the location   ***
            *** number and a question mark (?). You then  ***
            *** type the word for that location. Enter    ***
            *** -99999 to stop entering your program.     ***
            """);
    }
}
