package net.ethanstewart.advents.Day15;

enum Operation {
    SET, REMOVE;

    static Operation parseOperation(String command) {
        if (command.contains("=")) {
            return SET;
        } else if (command.contains("-")) {
            return REMOVE;
        } else {
            throw new IllegalArgumentException("Unexpected input: " + command);
        }
    }
}
