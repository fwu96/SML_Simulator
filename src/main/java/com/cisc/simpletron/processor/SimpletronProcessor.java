package com.cisc.simpletron.processor;

import com.cisc.simpletron.memory.SimpletronMemory;
import com.cisc.simpletron.operation.Operations;
import com.cisc.simpletron.scanner.ScannerHelper;

import java.io.File;
import java.util.Scanner;

/**
 * The core class for the Simpletron machine to load instructions and execute operations
 *
 * @author Feifna Wu
 * @version 3.0
 */
public class SimpletronProcessor {
    private final SimpletronMemory memory;
    private int instructionCounter;
    private int instructionRegister;
    private int operationCode;
    private int operand;
    private int accumulator;
    private final ScannerHelper scanner;

    public SimpletronProcessor(int size, ScannerHelper scanner) {
        this.memory = new SimpletronMemory(size);
        this.instructionRegister = 0;
        this.instructionCounter = 0;
        this.operationCode = 0;
        this.operand = 0;
        this.accumulator = 0;
        this.scanner = scanner;
    }

    /**
     * Main process method to call execution steps in order
     * based on selected mode - user input or file input
     */
    public int process() {
        MachineMode mode = selectLoadingSource();
        switch (mode) {
            case USER_INPUT:
                loadMemoryFromUserInput();
                break;
            case READ_FILE:
                loadMemoryFromFile();
                break;
        }
        int res = executeOperations();
        System.out.println("Final Result: " + res);
        dumpSummary();
        scanner.closeScanner();
        return res;
    }

    /**
     * Loading instructions to memory by user input
     */
    private void loadMemoryFromUserInput() {
        System.out.println("Simpletron program will load from user input. Please follow the prompts.");
        System.out.println();
        int addr = 0;
        int instruction = 0;
        System.out.print("00 ? ");
        instruction = validateInstruction(instruction, "00 ? ");
        addr = loadInstruction(addr, instruction);

        while (instruction != -99999 && addr < memory.getSize()) {
            if (addr < 10) {
                System.out.print("0" + addr + " ? ");
                instruction = validateInstruction(instruction, "0" + addr + " ? ");
            } else {
                System.out.print(addr + " ? ");
                instruction = validateInstruction(instruction, addr + " ? ");
            }
            addr = loadInstruction(addr, instruction);
        }
        System.out.println("""
                *** Program loading completed ***
                *** Program execution begins ***
                """);
    }

    /**
     * Loading instructions to memory by file input
     */
    private void loadMemoryFromFile() {
        System.out.print("Simpletron program will load from file. Enter file path: ");
        try {
            int addr = 0;
            scanner.inputString();
            String path = scanner.inputString();
            Scanner fileSC = new Scanner(new File(path));
            while (fileSC.hasNextLine()) {
                loadInstruction(addr, Integer.parseInt(fileSC.nextLine()));
                addr++;
            }
            System.out.println("""
                *** Program loading completed ***
                *** Program execution begins ***
                """);
            fileSC.close();
        } catch (Exception e) {
            System.out.println("*** Load file failed: " + e.getMessage() + " ***");
            System.out.println("*** Simpletron execution terminated abnormally ***");
            System.exit(1);
        }
    }

    /**
     * Executing operations after memory loading finished
     */
    private int executeOperations() {
        int res = 0;
        while (instructionCounter < memory.getSize()
                && memory.getVal(instructionCounter) != -99999) {
            instructionRegister = memory.getVal(instructionCounter);
            operationCode = instructionRegister / 100;
            operand = instructionRegister % 100;
            var currVal = memory.getVal(operand);
            switch (Operations.getOperation(operationCode)) {
                case READ:
                    System.out.print("Enter an integer: ");
                    int val = scanner.inputInt();
                    // ask user reenter number if input is invalid
                    while (val < -9999 || val > 9999) {
                        System.out.println("Input must be in range [-9999, 9999]");
                        System.out.print("Enter an integer: ");
                        val = scanner.inputInt();
                    }
                    memory.saveVal(operand, val);
                    break;
                case WRITE:
                    res = currVal;
                    break;
                case LOAD:
                    accumulator = currVal;
                    break;
                case STORE:
                    memory.saveVal(operand, accumulator);
                    break;
                case ADD:
                    accumulator += currVal;
                    break;
                case SUBTRACT:
                    accumulator -= currVal;
                    break;
                case DIVIDE:
                    if (currVal == 0) {
                        System.out.println("""
                                *** Attempt to divide by zero ***
                                *** Simpletron execution terminated abnormally ***
                                """);
                        System.exit(1);
                    } else {
                        accumulator /= currVal;
                        break;
                    }
                case MULTIPLY:
                    accumulator *= currVal;
                    break;
                case BRANCH:
                    instructionCounter = operand;
                    continue;
                case BRANCHNEG:
                    if (accumulator < 0) {
                        instructionCounter = operand;
                        continue;
                    }
                    break;
                case BRANCHZERO:
                    if (accumulator == 0) {
                        instructionCounter = operand;
                        continue;
                    }
                    break;
                case HALT:
                    System.out.println("*** Simpletron execution terminated normally ***");
                    return res;
                case REMAINDER:
                    if (currVal == 0) {
                        System.out.println("""
                                *** Attempt to divide by zero ***
                                *** Simpletron execution terminated abnormally ***
                                """);
                        System.exit(1);
                    }
                    accumulator %= currVal;
                    break;
                case EXPO:
                    accumulator = (int) Math.pow(accumulator, currVal);
                    break;
                case NEWLINE:
                    System.out.println();
                    break;
                case null:
                default:
                    System.out.println("""
                            *** Invalid operation code ***
                            *** Simpletron execution terminated abnormally ***
                            """);
                    return res;
            }
            instructionCounter++;
        }
        return res;
    }

    /**
     * Helper method for validating input instructions
     * @param instruction the instruction to load into memory
     * @param prompt string showed to user
     * @return loaded instruction
     */
    private int validateInstruction(int instruction, String prompt) {
        try {
            instruction = scanner.inputInt();
            while (instruction != -99999 && (instruction < -9999 || instruction > 9999)) {
                System.out.println("Please input instruction in range [-9999, 9999] or -99999");
                System.out.print(prompt);
                instruction = scanner.inputInt();
            }
        } catch (Exception e) {
            System.out.println("*** Exception while loading instruction to memory: " + e.getMessage() + " ***");
            System.out.println("*** Simpletron execution terminated abnormally ***");
            System.exit(1);
        }
        return instruction;
    }

    /**
     * Helper method to load instruction into memory at specific address
     * @param addr the location where instruction is stored
     * @param instruction instruction to store
     * @return next address of memory
     */
    private int loadInstruction(int addr, int instruction) {
        if (instruction != -99999) {
            memory.saveVal(addr, instruction);
            addr++;
        }
        return addr;
    }

    /**
     * Method to print memory dump at the end of program execution
     */
    private void dumpSummary() {
        System.out.println();
        System.out.println("REGISTERS:");
        System.out.printf("%-30s%5s\n", "accumulator", formatInteger(accumulator, 4, true));
        System.out.printf("%-30s%5s\n", "instructionCounter", formatInteger(instructionCounter, 2, false));
        System.out.printf("%-30s%5s\n", "instructionRegister", formatInteger(instructionRegister, 4, true));
        System.out.printf("%-30s%5s\n", "operationCode", formatInteger(operationCode, 2, false));
        System.out.printf("%-30s%5s\n", "operand", formatInteger(operand, 2, false));
        System.out.println();

        System.out.println("MEMORY:");
        System.out.printf("%3s", "");
        for (int i = 0; i < 10; i++) {
            System.out.printf("%8d", i);
        }
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                System.out.println();
                System.out.printf("%3d", i);
            }
            System.out.printf("%8s", formatInteger(memory.getVal(i), 4, true));
        }
        System.out.println();
    }

    /**
     * A helper method to format values those are going to be printed by memory dump
     * @param word the value
     * @param len the expected length to print
     * @param isSigned if including sign when printing
     * @return formatted string to print
     */
    private String formatInteger(int word, int len, boolean isSigned) {
        String sign = isSigned ? word < 0 ? "-" : "+" : "";
        String str = Integer.toString(word);
        int diff = len - str.length();
        if (diff == 0) {
            return sign + str;
        } else if (diff == 1) {
            return sign + "0" + str;
        } else if (diff == 2) {
            return sign + "00" + str;
        } else if (diff == 3) {
            return sign + "000" + str;
        }
        return sign + str;
    }

    /**
     * A helper method to ask user select instruction loading mode
     * 1) User Input
     * 2) File Input
     * @return the mode selected
     */
    private MachineMode selectLoadingSource() {
        System.out.println("""
            This Simpletron program loads from two sources:
            1) User Input
            2) File Input
            """);
        System.out.print("Please select input source: ");
        int input = scanner.inputInt();
        MachineMode mode = MachineMode.getMode(input);
        while (mode == null) {
            System.out.println("""
            Invalid source select. Available mode:
            1) User Input
            2) File input
            """);
            System.out.print("Please select input source: ");
            input = scanner.inputInt();
            mode = MachineMode.getMode(input);
        }
        return mode;
    }
}
