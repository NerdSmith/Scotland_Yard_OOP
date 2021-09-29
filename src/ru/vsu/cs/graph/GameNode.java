package ru.vsu.cs.graph;

import java.util.*;

public class GameNode implements Comparable<GameNode> {
    private String stationName;
    private Map<GameNode, Set<String>> neighbors;
    private boolean isWithDetective = false;
    private boolean isWithMrX = false;

    public GameNode(String stationName) {
        this.stationName = stationName;
        this.neighbors = new TreeMap<>();
    }

    private void addNeighbor(GameNode gNode, String type) {
        if (this.neighbors.get(gNode) == null) {
            Set<String> types = new HashSet<>();
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

    public boolean isWithDetective() {
        return isWithDetective;
    }

    public boolean isWithMrX() {
        return isWithMrX;
    }

    public void setDetectiveNodeStatus(boolean withDetective) {
        isWithDetective = withDetective;
    }

    public void setMrXNodeStatus(boolean withMrX) {
        isWithMrX = withMrX;
    }

    @Override
    public int compareTo(GameNode o) {
        return stationName.compareTo(o.stationName);
    }
}
