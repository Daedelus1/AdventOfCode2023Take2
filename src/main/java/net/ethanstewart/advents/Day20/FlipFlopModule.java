package net.ethanstewart.advents.Day20;

import java.util.List;
import java.util.PriorityQueue;

import static net.ethanstewart.advents.Day20.State.HIGH;
import static net.ethanstewart.advents.Day20.State.LOW;

public class FlipFlopModule extends Module {
    private FlipFlopModule.State state = State.OFF;

    public FlipFlopModule(String name, List<String> recipientList) {
        super(name, recipientList);
    }


    private enum State {
        ON, OFF;

        State getInverse() {
            return this == ON ? OFF : ON;
        }

    }

    @Override
    public void receive(Pulse pulse, PriorityQueue<Pulse> eventQueue) {
        this.state = state.getInverse();
        if (pulse.state() == LOW) {
            return;
        }
        if (this.state == State.ON) {
            this.recipientList.forEach(recipient ->
                    eventQueue.add(new Pulse(recipient, HIGH, pulse.degree() + 1)));
        }
    }

    @Override
    public String toString() {
        return "FlipFlopModule{" +
                "state=" + state +
                ", name='" + name + '\'' +
                ", recipientList=" + recipientList +
                '}';
    }

    public static final String MODULE_TYPE_MATCHING_REGEX = "%.*";

}
