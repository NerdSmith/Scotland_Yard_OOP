package ru.vsu.cs.graph;

import java.util.List;
import java.util.Map;

public class Node {
    private String stationName;
    private Map<Node, List<String>> neighbors;
    private boolean isWithDetective = false;
    private boolean isWithMrX = false;
}
