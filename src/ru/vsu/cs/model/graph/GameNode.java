package ru.vsu.cs.model.graph;

import ru.vsu.cs.model.Ticket;

import java.util.*;

public class GameNode implements Comparable<GameNode> {
    private String stationName;
    private Map<GameNode, Set<Ticket>> neighbors;

    public GameNode(String stationName) {
        this.stationName = stationName;
        this.neighbors = new TreeMap<GameNode, Set<Ticket>>();
    }

    public Map<GameNode, Set<Ticket>> getNeighbors() {
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
