package com.cisc.simpletron.memory;

/**
 * The class work as memory for Simpletron machine
 * It includes the memory structure as an integer array, and size
 * The memory size could be specified by the constructor at the time of initialization
 * <p>
 * Providing three helper methods:
 * 1) save value into target address
 * 2) get value at a specific address
 * 3) get memory size
 *
 * @author Feifan Wu
 * @version 3.0
 */
public class SimpletronMemory {
    private final int[] memory;
    private final int size;

    public SimpletronMemory(int size) {
        this.size = size;
        memory = new int[size];
    }

    public void saveVal(int address, int value) {
        memory[address] = value;
    }

    public int getVal(int address) {
        return memory[address];
    }

    public int getSize() {
        return size;
    }
}
