package net.ethanstewart.advents.Day20;

import java.util.Comparator;
import java.util.PriorityQueue;

public class CountingPriorityQueue extends PriorityQueue<Pulse> {
    private int numHighPulses;
    private int numLowPulses;

    public int getNumHighPulses() {
        return numHighPulses;
    }

    public int getNumLowPulses() {
        return numLowPulses;
    }

    public CountingPriorityQueue(Comparator<? super Pulse> comparator) {
        super(comparator);
    }

    @Override
    public boolean add(Pulse pulse) {
        switch (pulse.state()) {
            case HIGH -> numHighPulses++;
            case LOW -> numLowPulses++;
        }
        return super.add(pulse);
    }

}
