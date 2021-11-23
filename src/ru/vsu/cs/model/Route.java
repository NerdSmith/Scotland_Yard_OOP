package ru.vsu.cs.model;

import ru.vsu.cs.model.graph.GameNode;

public class Route {
    private final GameNode dest;
    private final Ticket ticket;

    public Route(GameNode dest, Ticket ticket) {
        this.dest = dest;
        this.ticket = ticket;
    }

    public GameNode getDest() {
        return dest;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
