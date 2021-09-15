package ru.vsu.cs.player;

import java.util.Map;

public class Detective extends Person{
    public Detective(String name, int startPos, Map<Ticket, Integer> startTickets) {
        super(name, startPos, startTickets);
    }
}
