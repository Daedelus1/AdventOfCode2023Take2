package net.ethanstewart.advents.Day20;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public abstract class Module {
    protected final String name;

    protected final List<String> recipientList;


    public Module(String name, List<String> recipientList) {
        this.name = name;
        this.recipientList = recipientList;
    }


    public String getName() {
        return name;
    }

    abstract void receive(Pulse pulse, PriorityQueue<Pulse> eventQueue);

    public static Module parseModule(String line) {
        String[] parts = line.trim().split("\\s*->\\s*");
        String name = parts[0].trim();
        List<String> recipients = Arrays.stream(parts[1].trim().split(",\\s+")).toList();
        if (name.matches(BroadcasterModule.MODULE_TYPE_MATCHING_REGEX)) {
            return new BroadcasterModule(name, recipients);
        } else if (name.matches(ConjunctionModule.MODULE_TYPE_MATCHING_REGEX)) {
            return new ConjunctionModule(name.substring(1), recipients);
        } else if (name.matches(ButtonModule.MODULE_TYPE_MATCHING_REGEX)) {
            return new ButtonModule(name, recipients);
        } else if (name.matches(FlipFlopModule.MODULE_TYPE_MATCHING_REGEX)) {
            return new FlipFlopModule(name.substring(1), recipients);
        } else {
            throw new IllegalArgumentException("Unrecognized module name: " + name);
        }
    }
}
