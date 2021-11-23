package ru.vsu.cs.service;

import ru.vsu.cs.model.*;
import ru.vsu.cs.model.graph.GameNode;
import ru.vsu.cs.model.person.Detective;
import ru.vsu.cs.model.person.MrX;
import ru.vsu.cs.model.person.Person;

import java.util.*;


public class GameService {
    private final String[] START_POINTS = { "13", "26", "29", "34", "50", "53", "91", "94", "103", "112", "117", "132",
            "138", "141", "155", "174", "197", "198" };
    private final int[] REVEAL_MRX_ON_TURNS = {3, 8, 13, 18};
    private final GameGraphService gameGraphService = new GameGraphService();
    private final Random rnd =  new Random();
    private final double BLACK_TICKET_PROBABILITY = 0.4;
    private final double DOUBLE_TICKET_PROBABILITY = 0.2;
    private final boolean debug = false;

    public GameService() { }

    public Game createGame(Queue<Person> players) {
        Game g = null;
        try {
            g = new Game(new ScotlandYardGraphService().readGameGraphFromFile("resources/ScotlandYardMap.txt"),
                    players, findMrX(players));
        } catch (Exception e) {
            System.out.printf("Can't create Game: %s", e.getMessage());
            System.exit(1);
        }
        setStartPoints(players, g);
        setTickets(players, g);
        return g;
    }

    private MrX findMrX(Queue<Person> players) throws Exception {
        for (Person person: players) {
            if (person instanceof MrX) {
                return (MrX) person;
            }
        }
        throw new Exception("MrX is not defined!");
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

    public void startGame(Game g) {
        Scanner scanner = new Scanner(System.in);
        Map<Person, GameNode> plPos = g.getPlayerPos();
        Map<Person, List<Ticket>> plTickets = g.getPlayerTickets();
        Queue<Person> players = g.getPlayers();
        MrX MrXref = g.getMrXref();
        boolean turnFlag = false;
        Person personWon;

        do {
            if (isNewTurn(g)) {
                printGlobalInfo(g.getTurn(), MrXref, players, plPos, plTickets, false);
                turnFlag = false;
            }

            Person currPlayer = players.poll();
            List<Ticket> currPlTickets = g.getPlayerTickets().get(currPlayer);


            Boolean turnRes = makeTurn(currPlTickets, plPos, currPlayer, MrXref, plTickets.get(MrXref), g.getTurn());
            if (turnRes != null) {
                turnFlag = turnRes || turnFlag;
            }

            personWon = isPersonWon(g, turnFlag);

            g.incGlobalTurn();
            scanner.nextLine();
            players.add(currPlayer);


        } while (personWon == null);
        printWinner(personWon);
        printGlobalInfo(g.getTurn(), MrXref, players, plPos, plTickets, true);
    }

    private void printWinner(Person player) {
        System.out.printf("Congratulations, player %s WON\n", player.getName());
    }

    private Person isPersonWon(Game g, boolean turnFlag) {
        Person detectiveWinCondition = isDetectiveWithMrX(g.getPlayerPos(), g.getMrXref());

        if (detectiveWinCondition != null) {
            return detectiveWinCondition;
        }

        boolean MrXWinCondition = g.getTurn() == 23 || (!turnFlag && isLastTurn(g));
        if (MrXWinCondition) {
            return g.getMrXref();
        }

        return null;
    }

    private Person isDetectiveWithMrX(Map<Person, GameNode> playerPos, MrX MrXref) {
        for (Person player: playerPos.keySet()) {
            if (player != MrXref && playerPos.get(player) == playerPos.get(MrXref)) {
                return player;
            }
        }
        return null;
    }

    private boolean isNewTurn(Game g) {
        return g.getGlobalTurn() % g.getPlayersNb() == 0;
    }

    private boolean isLastTurn(Game g) {
        return g.getGlobalTurn() % g.getPlayersNb() == g.getPlayersNb() - 1;
    }

    private Boolean makeTurn(List<Ticket> tickets, Map<Person, GameNode> plPos, Person player, MrX MrXref, List<Ticket> MrXTickets, Integer currTurn) {
        if (tickets.size() == 0) {
            printCantTravel(player);
            return false;
        }
        Map<GameNode, Set<Ticket>> availableNeighbors = getAvailableNeighbors(plPos.get(player).getNeighbors(), getOccupiedNodes(plPos), plPos.get(MrXref));

        boolean isMrXTurn = player == MrXref;
        Route route;

        if (isMrXTurn) {
            boolean repeat = useDoubleTicket(tickets);

            for (int i = 0; i < 1 + (repeat? 1 : 0); i++) {
                route = makeRide(availableNeighbors, tickets, player, MrXref, plPos, currTurn, true);
            }
            return null;
        }
        else {
            route = makeRide(availableNeighbors, tickets, player, MrXref, plPos, currTurn, false);
            if (route != null) {
                enrichMrX(MrXTickets, route.getTicket());
                return true;
            }
            return false;
        }

    }

    private boolean useBlackTicket(List<Ticket> tickets) {
        return tickets.contains(Ticket.BLACK) && (rnd.nextFloat() < BLACK_TICKET_PROBABILITY);
    }

    private boolean useDoubleTicket(List<Ticket> tickets) {
        boolean res = tickets.contains(Ticket.DOUBLE) && (rnd.nextFloat() < DOUBLE_TICKET_PROBABILITY);
        if (res) {
            tickets.remove(Ticket.DOUBLE);
        }
        return res;
    }

    private Route makeRide(Map<GameNode, Set<Ticket>> availableNeighbors, List<Ticket> tickets, Person player, MrX MrXref, Map<Person, GameNode> plPos, Integer currTurn, boolean isMrXTurn) {
        Route route;

        if (isMrXTurn && useBlackTicket(tickets)) {
            route = getRandomRouteByTickets(availableNeighbors, tickets, true);
        }
        else {
            route = getRandomRouteByTickets(availableNeighbors, tickets, false);
        }

        if (route == null) {
            printCantTravel(player);
            return route;
        }

        GameNode startStation = plPos.get(player);

        printSingleInfo(player, startStation, route, MrXref, currTurn);

        makeMove(tickets, plPos, player, route);

        return route;
    }

    private void enrichMrX(List<Ticket> MrXTickets, Ticket ticket) {
        MrXTickets.add(ticket);
    }

    private void makeMove(List<Ticket> tickets, Map<Person, GameNode> plPos, Person player, Route route) {
        tickets.remove(route.getTicket());
        plPos.put(player, route.getDest());
    }

    private Route getRandomRouteByTickets(Map<GameNode, Set<Ticket>> availableNeighbors, List<Ticket> playerTickets, boolean isBlack) {
        if (isBlack) {
            ArrayList<GameNode> availableNodes = new ArrayList<>(availableNeighbors.keySet());
            GameNode gNode = availableNodes.get(rnd.nextInt(availableNeighbors.size()));
            return new Route(gNode, Ticket.BLACK);
        }
        while (!availableNeighbors.isEmpty()) {
            ArrayList<GameNode> availableNodes = new ArrayList<>(availableNeighbors.keySet());
            GameNode gNode = availableNodes.get(rnd.nextInt(availableNeighbors.size()));
            Set<Ticket> tickets = availableNeighbors.get(gNode);
            Ticket currTicket = new ArrayList<>(tickets).get(rnd.nextInt(tickets.size()));
            tickets.remove(currTicket);
            if (tickets.size() == 0) {
                availableNeighbors.remove(gNode);
            }
            if (playerTickets.contains(currTicket)) {
                return new Route(gNode, currTicket);
            }
        }
        return null;
    }

    private ArrayList<GameNode> getOccupiedNodes(Map<Person, GameNode> plPos) {
        return new ArrayList<>(plPos.values());
    }

    private Map<GameNode, Set<Ticket>> getAvailableNeighbors(Map<GameNode, Set<Ticket>> neighbors, List<GameNode> occupiedNodes, GameNode MrXPos) {
        Map<GameNode, Set<Ticket>> availableNeighbors = new HashMap<>();
        for (GameNode gNode: neighbors.keySet()) {
            if (!occupiedNodes.contains(gNode) || gNode == MrXPos) {
                availableNeighbors.put(gNode, new HashSet<>(neighbors.get(gNode)));
            }
        }
        return availableNeighbors;
    }

    private void printGlobalInfo(Integer turn, MrX MrXref, Queue<Person> players, Map<Person, GameNode> plPos, Map<Person, List<Ticket>> plTickets, boolean reveal) {
        System.out.print("---------------");
        System.out.printf("|Turn: %d start|", turn);
        System.out.println("---------------");
        for (Person person : players) {
            if (debug || person != MrXref || reveal)
            System.out.printf("| %10s | station: %3s | %50s |\n", person.getName(), plPos.get(person).getStationName(), ticketsFormat(plTickets.get(person)));
        }
        System.out.print("---------------------------------------------\n");
    }

    private void printSingleInfo(Person player, GameNode startNode, Route route, MrX MrXref, Integer turn) {
        System.out.printf("%s travels", player.getName());
        if (checkReveal(player, MrXref, turn)) {
            System.out.printf(" from %s station to %s station", startNode.getStationName(), route.getDest().getStationName());
        }
        else {
            System.out.print("");
        }
        System.out.printf(" by %s\n", route.getTicket());
    }

    private boolean checkReveal(Person player, MrX MrXref, Integer turn) {
        return debug || player != MrXref || Arrays.stream(REVEAL_MRX_ON_TURNS).anyMatch(x -> x == turn);
    }

    private void printCantTravel(Person player) {
        System.out.printf("%s can't travel", player.getName());
    }

    private String ticketsFormat(List<Ticket> tickets) {
        StringBuilder sb = new StringBuilder();
        for (Ticket ticket: Ticket.values()) {
            sb.append(String.format(" %s = %2d ", ticket, Collections.frequency(tickets, ticket)));
        }
        return sb.toString();
    }
}
