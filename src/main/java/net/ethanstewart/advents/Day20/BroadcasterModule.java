package net.ethanstewart.advents.Day20;

import java.util.List;
import java.util.PriorityQueue;

public class BroadcasterModule extends Module {
    public BroadcasterModule(String name, List<String> recipientList) {
        super(name, recipientList);
    }

    @Override
    void receive(Pulse pulse, PriorityQueue<Pulse> eventQueue) {
        recipientList.forEach(recipient -> eventQueue.add(new Pulse(recipient, pulse.state(), pulse.degree() + 1)));
    }

    @Override
    public String toString() {
        return "BroadcasterModule{" +
                "name='" + name + '\'' +
                ", recipientList=" + recipientList +
                '}';
    }

    public static final String MODULE_TYPE_MATCHING_REGEX = "broadcaster";
}
