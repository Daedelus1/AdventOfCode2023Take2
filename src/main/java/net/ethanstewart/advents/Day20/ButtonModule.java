package net.ethanstewart.advents.Day20;

import java.util.List;
import java.util.PriorityQueue;

public class ButtonModule extends Module {
    public ButtonModule(String name, List<String> recipientList) {
        super(name, recipientList);
    }

    @Override
    void receive(Pulse pulse, PriorityQueue<Pulse> eventQueue) {
        throw new IllegalStateException("BUTTON MODULE WAS PINGED");
    }


    @Override
    public String toString() {
        return "ButtonModule{" +
                "name='" + name + '\'' +
                ", recipientList=" + recipientList +
                '}';
    }

    public static final String MODULE_TYPE_MATCHING_REGEX = "broadcaster";
    void press(PriorityQueue<Pulse> eventQueue) {
        recipientList.forEach(recipient -> eventQueue.add(new Pulse(recipient, State.LOW, 0)));
    }
}
