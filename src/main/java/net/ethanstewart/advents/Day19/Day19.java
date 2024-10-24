package net.ethanstewart.advents.Day19;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day19 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            px{a<2006:qkq,m>2090:A,rfg}
                            pv{a>1716:R,A}
                            lnx{m>1548:A,A}
                            rfg{s<537:gd,x>2440:R,A}
                            qs{s>3448:A,lnx}
                            qkq{x<1416:A,crn}
                            crn{x>2662:A,R}
                            in{s<1351:px,qqz}
                            qqz{s>2770:qs,m<1801:hdj,R}
                            gd{a>3333:R,R}
                            hdj{m>838:A,pv}
                            
                            {x=787,m=2655,a=1222,s=2876}
                            {x=1679,m=44,a=2067,s=496}
                            {x=2036,m=264,a=79,s=2244}
                            {x=2461,m=1339,a=466,s=291}
                            {x=2127,m=1623,a=2188,s=1013}""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day19\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static long part1() {
        String INPUT = INPUT_MANAGER.getInput();
        HashMap<String, Workflow> nameWorkflowMap = new HashMap<>();
        INPUT.substring(0, INPUT.indexOf("\n\n"))
                .lines()
                .map(workflowString -> Workflow.parseWorkflow(workflowString, nameWorkflowMap))
                .forEach(workflow -> nameWorkflowMap.put(workflow.name(), workflow));
        return INPUT.substring(INPUT.indexOf("\n\n"))
                .trim()
                .lines()
                .map(Rating::parseRating)
                .filter(rating -> nameWorkflowMap.get("in").evaluate(rating) == Workflow.Evaluation.ACCEPTED)
                .mapToLong(rating -> rating.m() + rating.a() + rating.x() + rating.s())
                .sum();
    }

    public static long part2() {
        throw new IllegalStateException("TODO: PART 2");
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
