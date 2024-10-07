package com.cisc.simpletron.operation;

/**
 * The enum class contains all operations supported by the Simpletron program,
 * assigned with associated value of operation code
 * <p>
 * Provides a method to get operation by given operation code
 *
 * @author Feifan Wu
 * @version 3.0
 */
public enum Operations {
    READ(10), WRITE(11),
    LOAD(20), STORE(21),
    ADD(30), SUBTRACT(31), DIVIDE(32), MULTIPLY(33),
    BRANCH(40), BRANCHNEG(41), BRANCHZERO(42), HALT(43),
    REMAINDER(50), EXPO(51),
    NEWLINE(60);

    private final int value;

    Operations(int value) {
        this.value = value;
    }

    public static Operations getOperation(int operationCode) {
        for (Operations operation : Operations.values()) {
            if (operation.value == operationCode) {
                return operation;
            }
        }
        return null;
    }
}
