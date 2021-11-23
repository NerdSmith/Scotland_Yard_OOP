package ru.vsu.cs.graph;

import ru.vsu.cs.player.Ticket;

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

    public static void main(String[] args) {
        GameGraph gameGraph = new GameGraph();
        GameGraphService gameGraphService = new GameGraphService();
        GameNode n1 = gameGraphService.getOrCreateNode(gameGraph,"1");
        GameNode n2 = gameGraphService.getOrCreateNode(gameGraph,"2");
        gameGraphService.addEdge(n1, n2, Ticket.BUS);
        gameGraphService.addEdge(n1, n2, Ticket.BLACK);
    }
}
