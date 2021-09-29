package ru.vsu.cs.player;

import ru.vsu.cs.graph.GameNode;

import java.util.Map;

public abstract class Person {
    private String name;
    private GameNode pos;
    private Map<Ticket, Integer> tickets;

    public Person(String name, GameNode startPos, Map<Ticket, Integer> startTickets) {
        this.name = name;
        this.pos = startPos;
        this.tickets = startTickets;
    }

    public String getName() {
        return name;
    }

    public GameNode getPos() {
        return pos;
    }

    public Map<Ticket, Integer> getTickets() {
        return tickets;
    }
}
