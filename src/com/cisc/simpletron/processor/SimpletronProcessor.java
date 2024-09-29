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

}
