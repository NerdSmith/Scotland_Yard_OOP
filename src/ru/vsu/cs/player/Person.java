package ru.vsu.cs.player;

import java.util.Map;

public abstract class Person {
    private String name;
    private int pos;
    private Map<Ticket, Integer> tickets;

    public Person(String name, int startPos, Map<Ticket, Integer> startTickets) {
        this.name = name;
        this.pos = startPos;
        this.tickets = startTickets;
    }


}
