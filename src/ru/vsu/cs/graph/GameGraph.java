package ru.vsu.cs.graph;

import java.util.Map;
import java.util.TreeMap;

public class GameGraph {
    private Map<String, GameNode> nodes;

    public GameGraph() {
        this.nodes = new TreeMap<>();
    }

    public Map<String, GameNode> getNodes() {
        return nodes;
    }
}
