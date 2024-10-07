package com.cisc.simpletron.processor;

public enum MachineMode {
    USER_INPUT(1),
    READ_FILE(2);

    private final int input;
    MachineMode(int input) {
        this.input = input;
    }

    public static MachineMode getMode(int input) {
        for (MachineMode mode : MachineMode.values()) {
            if (mode.input == input) {
                return mode;
            }
        }
        return null;
    }
}
