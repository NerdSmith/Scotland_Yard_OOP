package ru.vsu.cs.game;

import ru.vsu.cs.graph.GameGraph;
import ru.vsu.cs.graph.GameGraphService;
import ru.vsu.cs.graph.GameNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
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
            System.out.println(Arrays.toString(line));
            GameNode gameNode1 = gameGraphService.getOrCreateNode(gameGraph, line[0]);
            GameNode gameNode2 = gameGraphService.getOrCreateNode(gameGraph, line[1]);
            gameGraphService.addEdge(gameNode1, gameNode2, line[2]);
        }
        return gameGraph;
    }
}
