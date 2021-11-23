package ru.vsu.cs.service;

import ru.vsu.cs.model.graph.GameGraph;
import ru.vsu.cs.model.graph.GameNode;
import ru.vsu.cs.model.Ticket;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameGraphService {
    public GameNode getOrCreateNode(GameGraph gGraph, String nodeName) {
        Map<String, GameNode> nodes = gGraph.getNodes();
        if (nodes.containsKey(nodeName)) {
            return nodes.get(nodeName);
        }
        nodes.put(nodeName, new GameNode(nodeName));
        return nodes.get(nodeName);
    }

    private void addNeighbor(GameNode gNode1, GameNode gNode2, Ticket type) {
        Map<GameNode, Set<Ticket>> neighbors = gNode1.getNeighbors();
        if (neighbors.get(gNode2) == null) {
            Set<Ticket> types = new HashSet<>();
            types.add(type);
            neighbors.put(gNode2, types);
        }
        else {
            neighbors.get(gNode2).add(type);
        }
    }

    public void addEdge(GameNode gNode1, GameNode gNode2, Ticket type) {
        this.addNeighbor(gNode1, gNode2, type);
        this.addNeighbor(gNode2, gNode1, type);
    }
}
