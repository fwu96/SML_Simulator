package main.com.cisc.simpletron.operation;

public enum Operations {
    READ(10), WRITE(11),
    LOAD(20), STORE(21),
    ADD(30), SUBTRACT(31), DIVIDE(32), MULTIPLY(33),
    BRANCH(40), BRANCHNEG(41), BRANCHZERO(42), HALT(43);

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
