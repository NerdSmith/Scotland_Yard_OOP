package ru.vsu.cs.game;

import ru.vsu.cs.graph.GameGraphService;
import ru.vsu.cs.graph.GameNode;
import ru.vsu.cs.player.Detective;
import ru.vsu.cs.player.Person;
import ru.vsu.cs.player.Ticket;

import java.util.*;


public class GameService {
    private final String[] START_POINTS = { "13", "26", "29", "34", "50", "53", "91", "94", "103", "112", "117", "132",
            "138", "141", "155", "174", "197", "198" };
    private final GameGraphService gameGraphService = new GameGraphService();

    public GameService() { }

    public Game createGame(Queue<Person> players) {
        Game g = new Game(new ScotlandYardGraphService().readGameGraphFromFile("resources/ScotlandYardMap.txt"),
                players);
        setStartPoints(players, g);
        setTickets(players, g);
        return g;
    }

    private void setStartPoints(Queue<Person> players, Game g) {
        Map<Person, GameNode> playerPos = new HashMap<>();
        for (Person player: players) {
            playerPos.put(player, getRandomStartPoint(g, playerPos));
        }
        g.setPlayerPos(playerPos);
    }

    private void setTickets(Queue<Person> players, Game g) {
        Map<Person, List<Ticket>> playerTickets = new HashMap<>();
        for (Person player: players) {
            playerTickets.put(player, player instanceof Detective ? detectiveTickets() : mrxTickets());
        }
        g.setPlayerTickets(playerTickets);
    }

    private GameNode getRandomStartPoint(Game game, Map<Person, GameNode> playersPos) {
        GameNode gNode;
        Collection<GameNode> gameNodes = playersPos.values();
        while (true) {
            Random rnd = new Random();

            gNode = gameGraphService.getOrCreateNode(game.getGameGraph(), START_POINTS[rnd.nextInt(START_POINTS.length)]);
            if (!gameNodes.contains(gNode)) {
                break;
            }
        }
        return gNode;
    }


    private List<Ticket> mrxTickets() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.addAll(Collections.nCopies(4, Ticket.TAXI));
        tickets.addAll(Collections.nCopies(3, Ticket.BUS));
        tickets.addAll(Collections.nCopies(3, Ticket.UNDERGROUND));
        tickets.addAll(Collections.nCopies(2, Ticket.DOUBLE));
        tickets.addAll(Collections.nCopies(5, Ticket.BLACK));
        return tickets;
    }

    private List<Ticket> detectiveTickets() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.addAll(Collections.nCopies(10, Ticket.TAXI));
        tickets.addAll(Collections.nCopies(8, Ticket.BUS));
        tickets.addAll(Collections.nCopies(4, Ticket.UNDERGROUND));
        tickets.addAll(Collections.nCopies(0, Ticket.DOUBLE));
        tickets.addAll(Collections.nCopies(0, Ticket.BLACK));
        return tickets;
    }
}
