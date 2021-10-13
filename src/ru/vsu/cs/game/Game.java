package ru.vsu.cs.game;

import ru.vsu.cs.graph.GameGraph;
import ru.vsu.cs.graph.GameNode;
import ru.vsu.cs.player.Person;
import ru.vsu.cs.player.Ticket;


import java.util.*;

public class Game {
    private GameGraph gameGraph;
    private Queue<Person> players;
    private Map<Person, GameNode> playerPos;
    private Map<Person, List<Ticket>> playerTickets;
    private ArrayList<Ticket> mrXTravelLog = new ArrayList<>();
    private Turn currTurn;

    public Game(GameGraph gameGraph, Queue<Person> players) {
        this.gameGraph = gameGraph;
        this.players = players;
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
}
