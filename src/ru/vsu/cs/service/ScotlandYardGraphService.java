package ru.vsu.cs.service;

import ru.vsu.cs.model.graph.GameGraph;
import ru.vsu.cs.model.graph.GameNode;
import ru.vsu.cs.model.Ticket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScotlandYardGraphService {
    public GameGraph readGameGraphFromFile(String filePath) {
        GameGraph gameGraph = new GameGraph();
        GameGraphService gameGraphService = new GameGraphService();
        Scanner scanner;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.printf("file by path %s not found", filePath);
            e.printStackTrace();
            return gameGraph;
        }
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            GameNode gameNode1 = gameGraphService.getOrCreateNode(gameGraph, line[0]);
            GameNode gameNode2 = gameGraphService.getOrCreateNode(gameGraph, line[1]);
            gameGraphService.addEdge(gameNode1, gameNode2, Ticket.valueOf(line[2]));
        }
        return gameGraph;
    }

}
