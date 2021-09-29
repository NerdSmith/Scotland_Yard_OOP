package ru.vsu.cs.player;

import ru.vsu.cs.graph.GameNode;

import java.util.Map;

public class MrX extends Person {
    public MrX(String name, GameNode startPos, Map<Ticket, Integer> startTickets) {
        super(name, startPos, startTickets);
    }
}
