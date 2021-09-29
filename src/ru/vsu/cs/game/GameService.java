package ru.vsu.cs.game;

import ru.vsu.cs.graph.GameGraph;
import ru.vsu.cs.graph.GameNode;
import ru.vsu.cs.player.Detective;
import ru.vsu.cs.player.MrX;
import ru.vsu.cs.player.Ticket;

import java.util.*;

import static ru.vsu.cs.game.ScotlandYardGraphService.readGameGraphFromFile;

public class GameService {
    private final String[] START_POINTS = { "13", "26", "29", "34", "50", "53", "91", "94", "103", "112", "117", "132",
            "138", "141", "155", "174", "197", "198" };

    public GameService() { }

    public Game createGame() {
        Game g = new Game(readGameGraphFromFile("resources/ScotlandYardMap.txt"));


        MrX mrX = new MrX("X", getRandomStartPoint(g), mrxTickets());

        ArrayList<Detective> detectives = new ArrayList<>();
        detectives.add(new Detective("D1", getRandomStartPoint(g), detectiveTickets()));
        detectives.add(new Detective("D2", getRandomStartPoint(g), detectiveTickets()));
        detectives.add(new Detective("D3", getRandomStartPoint(g), detectiveTickets()));
        detectives.add(new Detective("D4", getRandomStartPoint(g), detectiveTickets()));
        detectives.add(new Detective("D5", getRandomStartPoint(g), detectiveTickets()));

        g.setPlayers(mrX, detectives);
        return g;
    }

    private GameNode getRandomStartPoint(Game game) {
        GameNode gNode;
        while (true) {
            Random rnd = new Random();

            gNode = game.getGameGraph().getOrCreateNode(START_POINTS[rnd.nextInt(START_POINTS.length)]);
            if (!gNode.isWithDetective() && !gNode.isWithMrX()) {
                break;
            }
        }
        return gNode;
    }

    private Map<Ticket, Integer> mrxTickets() {
        Map<Ticket, Integer> tickets = new HashMap<>();
        tickets.put(Ticket.TAXI, 4);
        tickets.put(Ticket.BUS, 3);
        tickets.put(Ticket.UNDERGROUND, 3);
        tickets.put(Ticket.DOUBLE, 2);
        tickets.put(Ticket.BLACK, 5);
        return tickets;
    }

    private Map<Ticket, Integer> detectiveTickets() {
        Map<Ticket, Integer> tickets = new HashMap<>();
        tickets.put(Ticket.TAXI, 10);
        tickets.put(Ticket.BUS, 8);
        tickets.put(Ticket.UNDERGROUND, 4);
        tickets.put(Ticket.DOUBLE, 0);
        tickets.put(Ticket.BLACK, 0);
        return tickets;
    }
}
