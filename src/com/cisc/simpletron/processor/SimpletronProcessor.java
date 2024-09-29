package com.cisc.simpletron.processor;

import com.cisc.simpletron.memory.SimpletronMemory;
import com.cisc.simpletron.operation.Operations;

import java.util.Scanner;

public class SimpletronProcessor {
    private final SimpletronMemory memory;
    private int instructionCounter;
    private int instructionRegister;
    private int operationCode;
    private int operand;
    private int accumulator;

    private final Scanner scanner = new Scanner(System.in);

    public SimpletronProcessor(int size) {
        this.memory = new SimpletronMemory(size);
        this.instructionRegister = 0;
        this.instructionCounter = 0;
        this.operationCode = 0;
        this.operand = 0;
        this.accumulator = 0;
    }

    public void process() {
        loadMemory();
        executeOperations();
        dumpSummary();
    }

    private void loadMemory() {
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

        System.out.println("*** Program loading completed ***");
        System.out.println("*** Program execution begins ***\n");
    }

    private void executeOperations() {
        while (instructionCounter < memory.getSize()
                && memory.getVal(instructionCounter) != -99999) {
            instructionRegister = memory.getVal(instructionCounter);
            operationCode = instructionRegister / 100;
            operand = instructionRegister % 100;
            switch (Operations.getOperation(operationCode)) {
                case READ:
                    System.out.print("Enter an integer: ");
                    int val = scanner.nextInt();
                    while (val < -9999 || val > 9999) {
                        System.out.print("Input must be in range [-9999, 9999]");
                        System.out.print("Enter an integer: ");
                        val = scanner.nextInt();
                    }
                    memory.saveVal(operand, val);
                    break;
                case WRITE:
                    System.out.println(memory.getVal(operand));
                    break;
                case LOAD:
                    accumulator = memory.getVal(operand);
                    break;
                case STORE:
                    memory.saveVal(operand, accumulator);
                    break;
                case ADD:
                    accumulator += memory.getVal(operand);
                    break;
                case SUBTRACT:
                    accumulator -= memory.getVal(operand);
                    break;
                case DIVIDE:
                    if (memory.getVal(operand) == 0) {
                        System.out.println();
                        System.out.println("*** Attempt to divide by zero ***");
                        System.out.println("*** Simpletron execution terminated abnormally ***");
                        System.exit(1);
                    } else {
                        accumulator /= memory.getVal(operand);
                        break;
                    }
                case MULTIPLY:
                    accumulator *= memory.getVal(operand);
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
                    return;
                case null:
                default:
                    System.out.println("*** Invalid operation code ***");
                    System.out.println("*** Simpletron execution terminated abnormally ***");
                    return;
            }
            instructionCounter++;
        }
    }



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

    private int validateInstruction(int instruction, String prompt) {
        try {
            instruction = scanner.nextInt();
            while (instruction != -99999 && (instruction < -9999 || instruction > 9999)) {
                System.out.println("Please input instruction in range [-9999, 9999] or -99999");
                System.out.print(prompt);
                instruction = scanner.nextInt();
            }
        } catch (Exception e) {
            System.out.println("*** Exception while loading instruction to memory: " + e.getMessage() + " ***");
            System.out.println("*** Simpletron execution terminated abnormally ***");
            System.exit(1);
        }
        return instruction;
    }

    private int loadInstruction(int addr, int instruction) {
        if (instruction != -99999) {
            memory.saveVal(addr, instruction);
            addr++;
        }
        return addr;
    }

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


}
