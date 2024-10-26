package net.ethanstewart.advents.Day20;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day20 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.PART1;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;

    static {
        try {
            INPUT_MANAGER = new InputManager("""
                    broadcaster -> a, b, c
                    %a -> b
                    %b -> c
                    %c -> inv
                    &inv -> a""", """
                    """, new BufferedReader(new FileReader("C:\\Users\\ethan\\Documents\\GitHub\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day20\\Input.txt")).lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static long part1() {
        CountingPriorityQueue eventQueue = new CountingPriorityQueue(Comparator.comparing(Pulse::degree));
        List<Module> modules = INPUT_MANAGER.getInput().trim().lines()
                .map(Module::parseModule).toList();
        ButtonModule button =new ButtonModule("button", List.of("broadcaster"));
        HashMap<String, Module> nameModuleMap = new HashMap<>();
        modules.forEach(module -> nameModuleMap.put(module.name, module));
        button.press(eventQueue);
        while (!eventQueue.isEmpty()) {
            Pulse pulse = eventQueue.poll();
            Module recipient = nameModuleMap.get(pulse.nameOfRecipient());
            System.out.println(recipient);
            recipient.receive(pulse, eventQueue);
            System.out.println(eventQueue);
        }
        throw new IllegalStateException("TODO: PART 1");
    }

    private static long part2() {
        throw new IllegalStateException("TODO: PART 2");
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
        //        System.out.printf("Part 2 : %s\n", part2());

    }

}
