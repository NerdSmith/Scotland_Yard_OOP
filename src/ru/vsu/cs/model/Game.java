package ru.vsu.cs.model;


import ru.vsu.cs.model.graph.GameGraph;
import ru.vsu.cs.model.graph.GameNode;
import ru.vsu.cs.model.person.MrX;
import ru.vsu.cs.model.person.Person;

import java.util.*;

public class Game {
    private GameGraph gameGraph;
    private Queue<Person> players;
    private int playersNb;
    private Map<Person, GameNode> playerPos;
    private Map<Person, List<Ticket>> playerTickets;
    private int globalTurn = 0;
    private final MrX MrXref;

    public Game(GameGraph gameGraph, Queue<Person> players, MrX MrXref) {
        this.gameGraph = gameGraph;
        this.players = players;
        this.MrXref = MrXref;
        this.playersNb = players.size();
    }

    public Queue<Person> getPlayers() {
        return players;
    }

    public Map<Person, GameNode> getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(Map<Person, GameNode> playerPos) {
        this.playerPos = playerPos;
    }

    public void setPlayerTickets(Map<Person, List<Ticket>> playerTickets) {
        this.playerTickets = playerTickets;
    }

    public GameGraph getGameGraph() {
        return gameGraph;
    }

    public Map<Person, List<Ticket>> getPlayerTickets() {
        return playerTickets;
    }

    public void incGlobalTurn() {
        globalTurn++;
    }

    public int getGlobalTurn() {
        return globalTurn;
    }

    public int getTurn() {
        return globalTurn / playersNb + 1;
    }

    public int getPlayersNb() {
        return playersNb;
    }

    public MrX getMrXref() {
        return MrXref;
    }
}
