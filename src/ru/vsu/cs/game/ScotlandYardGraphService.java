package ru.vsu.cs.game;

import ru.vsu.cs.graph.GameGraph;
import ru.vsu.cs.graph.GameNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ScotlandYardGraphService {
    public static GameGraph readGameGraphFromFile(String filePath) {
        GameGraph gameGraph = new GameGraph();
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
            GameNode gameNode1 = gameGraph.getOrCreateNode(line[0]);
            GameNode gameNode2 = gameGraph.getOrCreateNode(line[1]);
            gameNode1.addEdge(gameNode2, line[2]);
        }
        return gameGraph;
    }
}
