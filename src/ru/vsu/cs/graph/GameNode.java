package ru.vsu.cs.graph;

import java.util.*;

public class GameNode implements Comparable<GameNode> {
    private String stationName;
    private Map<GameNode, Set<String>> neighbors;

    public GameNode(String stationName) {
        this.stationName = stationName;
        this.neighbors = new TreeMap<>();
    }

    public Map<GameNode, Set<String>> getNeighbors() {
        return neighbors;
    }

    public String getStationName() {
        return stationName;
    }

    @Override
    public int compareTo(GameNode o) {
        return stationName.compareTo(o.stationName);
    }
}
