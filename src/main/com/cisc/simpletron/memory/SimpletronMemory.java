package main.com.cisc.simpletron.memory;

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
