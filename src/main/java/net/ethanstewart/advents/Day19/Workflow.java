package net.ethanstewart.advents.Day19;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

import static net.ethanstewart.advents.Day19.Workflow.Evaluation.ACCEPTED;
import static net.ethanstewart.advents.Day19.Workflow.Evaluation.REJECTED;

public record Workflow(String name, Command... commands) {

    public static Workflow parseWorkflow(String workflowString, HashMap<String, Workflow> workflows) {
        String[] sections = workflowString.split("[{}]");
        String name = sections[0];
        String[] conditionStrings = sections[1].split(",");
        return new Workflow(name,
                Arrays.stream(conditionStrings)
                        .map(string -> Command.parseCommand(string, workflows))
                        .toArray(Command[]::new));
    }

    public Evaluation evaluate(Rating rating) {
        return Arrays.stream(commands)
                .filter(command -> command.condition().apply(rating))
                .findFirst()
                .orElseThrow()
                .evaluate(rating);

    }

    public enum Evaluation {
        ACCEPTED, REJECTED
    }

    private record Command(Function<Rating, Boolean> condition, Function<Rating, Evaluation> evaluator) {

        public static Command parseCommand(String commandString, HashMap<String, Workflow> workflows) {
            String[] parts = commandString.split(":");
            if (parts.length == 1) {
                return parseCommandEndpoint(parts[0], _ -> true, workflows);
            }
            return parseCommandEndpoint(parts[1], parseCondition(parts[0]), workflows);
        }

        private static @NotNull Function<Rating, Boolean> parseCondition(String conditionString) {
            Function<Rating, Long> valueExtractor = rating -> switch (conditionString.charAt(0)) {
                case 'x' -> rating.x();
                case 'm' -> rating.m();
                case 'a' -> rating.a();
                case 's' -> rating.s();
                default -> throw new IllegalStateException("Unexpected value: " + conditionString.charAt(0));
            };
            return rating -> Operator.parseOperator(conditionString.charAt(1)).operate(
                    valueExtractor.apply(rating),
                    Long.parseLong(conditionString.substring(2))
            );
        }
//        private record Evaluator(ImmutableMap<Condition,String>)

        private static @NotNull Command parseCommandEndpoint(String endpointString, Function<Rating, Boolean> condition, HashMap<String, Workflow> workflows) {
            return switch (endpointString) {
                case "A" -> new Command(condition, _ -> ACCEPTED);
                case "R" -> new Command(condition, _ -> REJECTED);
                case null -> throw new IllegalStateException("Unexpected value: " + null);
                default -> new Command(condition, rating -> workflows.get(endpointString).evaluate(rating));
            };
        }

        public Evaluation evaluate(Rating rating) {
            return evaluator.apply(rating);
        }

        private enum Operator {
            LESS_THAN, GREATER_THAN, TRUE;

            public static Operator parseOperator(char operatorChar) {
                return switch (operatorChar) {
                    case '<' -> LESS_THAN;
                    case '>' -> GREATER_THAN;
                    default -> throw new IllegalStateException("Unexpected value: " + operatorChar);
                };
            }

            public boolean operate(long a, long b) {
                return switch (this) {
                    case LESS_THAN -> a < b;
                    case GREATER_THAN -> a > b;
                    case TRUE -> true;
                };
            }
        }

        private record Condition(Rating.Category category, Operator operator, long threshold) {
            public boolean evaluate(Rating rating) {
                return operator.operate(rating.getValue(category), threshold);
            }
        }
    }
}
