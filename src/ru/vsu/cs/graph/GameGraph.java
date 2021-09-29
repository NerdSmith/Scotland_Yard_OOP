package ru.vsu.cs.graph;

import java.util.Map;
import java.util.TreeMap;

public class GameGraph {
    private Map<String, GameNode> nodes;

    public GameGraph() {
        this.nodes = new TreeMap<>();
    }

    public GameNode getOrCreateNode(String nodeName) {
        if (nodes.containsKey(nodeName)) {
            return nodes.get(nodeName);
        }
        nodes.put(nodeName, new GameNode(nodeName));
        return nodes.get(nodeName);
    }

    public static void main(String[] args) {
        GameGraph gameGraph = new GameGraph();
        GameNode n1 = gameGraph.getOrCreateNode("1");
        GameNode n2 = gameGraph.getOrCreateNode("2");
        n1.addEdge(n2, "изолента");
        n1.addEdge(n2, "скотч");
    }
}
