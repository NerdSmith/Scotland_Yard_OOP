package ru.vsu.cs;

import ru.vsu.cs.model.person.Detective;
import ru.vsu.cs.model.Game;
import ru.vsu.cs.model.person.MrX;
import ru.vsu.cs.model.person.Person;
import ru.vsu.cs.service.GameService;

import java.util.LinkedList;

public class App {
    public static void main(String[] args) {
        LinkedList<Person> players = new LinkedList<>();
        players.add(new MrX("MrX"));
        players.add(new Detective("Holmes"));
        players.add(new Detective("Poirot"));
        players.add(new Detective("Marple"));
        players.add(new Detective("Brown"));
        players.add(new Detective("Wolfe"));

        GameService gameService = new GameService();
        Game g = gameService.createGame(players);
        gameService.startGame(g);
    }
}
