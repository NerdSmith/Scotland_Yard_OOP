package ru.vsu.cs.game;

public class App {
    public static void main(String[] args) {
        GameService gameService = new GameService();
        Game g = gameService.createGame();
    }
}
