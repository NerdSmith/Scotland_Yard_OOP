package ru.vsu.cs.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GameNode implements Comparable<GameNode> {
    private String stationName;
    private Map<GameNode, List<String>> neighbors;
    private boolean isWithDetective = false;
    private boolean isWithMrX = false;

    public GameNode(String stationName) {
        this.stationName = stationName;
        this.neighbors = new TreeMap<>();
    }

    private void addNeighbor(GameNode gNode, String type) {
        if (this.neighbors.get(gNode) == null) {
            List<String> types = new ArrayList<>();
            types.add(type);
            this.neighbors.put(gNode, types);
        }
        else {
            this.neighbors.get(gNode).add(type);
        }
    }

    public void addEdge(GameNode gNode, String type) {
        this.addNeighbor(gNode, type);
        gNode.addNeighbor(this, type);
    }

    @Override
    public int compareTo(GameNode o) {
        return stationName.compareTo(o.stationName);
    }
}
