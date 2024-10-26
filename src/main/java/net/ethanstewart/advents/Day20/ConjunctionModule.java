package net.ethanstewart.advents.Day20;

import java.util.List;
import java.util.PriorityQueue;

public class ConjunctionModule extends Module {

    private State stateOfLastPulse = State.LOW;

    public ConjunctionModule(String name, List<String> recipientList) {
        super(name, recipientList);
    }

    @Override
    void receive(Pulse pulse, PriorityQueue<Pulse> eventQueue) {
        State outboundState = stateOfLastPulse == State.HIGH && pulse.state() == State.HIGH ? State.HIGH : State.LOW;
        recipientList.forEach(recipient -> eventQueue.add(new Pulse(recipient, outboundState, pulse.degree() + 1)));
        stateOfLastPulse = pulse.state();
    }

    @Override
    public String toString() {
        return "ConjunctionModule{" +
                "stateOfLastPulse=" + stateOfLastPulse +
                ", name='" + name + '\'' +
                ", recipientList=" + recipientList +
                '}';
    }

    public static final String MODULE_TYPE_MATCHING_REGEX = "&.*";
}
