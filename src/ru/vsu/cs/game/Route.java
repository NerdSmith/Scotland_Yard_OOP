package ru.vsu.cs.game;

import ru.vsu.cs.graph.GameNode;
import ru.vsu.cs.player.Ticket;

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
