package net.ethanstewart.advents.Day8;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Iterator;

public record Network(ImmutableMap<String, Node> nodeMap, ImmutableList<Direction> traversalInstructions) {
    public static Network parseNetwork(String networkString) {
        ImmutableMap.Builder<String, Node> builder = ImmutableMap.builder();
        Arrays.stream(networkString.substring(networkString.indexOf("A")).split("\\n"))
                .map(Node::parseNode)
                .forEach(node -> builder.put(node.id(), node));
        ImmutableList<Direction> instructions = Arrays.stream(
                        networkString.substring(0, networkString.indexOf("\n")).split(""))
                .map(Direction::parseDirection)
                .collect(ImmutableList.toImmutableList());
        return new Network(builder.build(), instructions);
    }

    public long distanceToMatchingId(Node startingNode, String matcherRegex) {
        Iterator<Node> iterator = traversingIterator(startingNode);
        long count = 0;
        while (iterator.hasNext()) {
            count++;
            Node currentNode = iterator.next();
            if (currentNode.id().matches(matcherRegex)) {
                break;
            }
        }
        return count;
    }

    public Iterator<Node> traversingIterator(Node startingNode) {
        return new Iterator<Node>() {
            private int iteration = 0;
            private Node currentNode = startingNode;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Node next() {
                Direction direction = traversalInstructions.get(iteration % traversalInstructions.size());
                iteration++;
                currentNode = switch (direction) {
                    case LEFT -> nodeMap.get(currentNode.nextLeft());
                    case RIGHT -> nodeMap.get(currentNode.nextRight());
                };
                return currentNode;
            }
        };
    }


}
