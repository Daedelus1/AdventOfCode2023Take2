package net.ethanstewart.advents.Day8;

public record Node(String id, String nextLeft, String nextRight) {

    public static Node parseNode(String nodeString) {
        String[] parts = nodeString.substring(0, nodeString.length() - 1).split("( = \\()|(, )");
        return new Node(parts[0], parts[1], parts[2]);
    }
}
