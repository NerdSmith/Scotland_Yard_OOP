package ru.vsu.cs.game;

import ru.vsu.cs.player.Detective;
import ru.vsu.cs.player.MrX;
import ru.vsu.cs.player.Person;

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
    }
}
