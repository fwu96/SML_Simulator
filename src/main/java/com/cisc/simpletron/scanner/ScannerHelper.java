package com.cisc.simpletron.scanner;

import java.util.Scanner;

public class ScannerHelper {

    private final Scanner scanner = new Scanner(System.in);

    public String inputString() {
        return scanner.nextLine();
    }

    public int inputInt() {
        return scanner.nextInt();
    }

    public void closeScanner() {
        scanner.close();
    }
}
