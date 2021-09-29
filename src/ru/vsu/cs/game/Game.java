package ru.vsu.cs.game;

import ru.vsu.cs.graph.GameGraph;
import ru.vsu.cs.player.Detective;
import ru.vsu.cs.player.MrX;
import ru.vsu.cs.player.Ticket;


import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameGraph gameGraph;
    private MrX mrX;
    private List<Detective> detectives = new ArrayList<>();
    private ArrayList<Ticket> mrXTravelLog = new ArrayList<>();
    private Turn currTurn;

    public Game(GameGraph gameGraph) {
        this.gameGraph = gameGraph;
    }

    public void setPlayers(MrX mrX, List<Detective> detectives) {
        this.mrX = mrX;
        this.detectives = detectives;
    }

    public GameGraph getGameGraph() {
        return gameGraph;
    }
}
