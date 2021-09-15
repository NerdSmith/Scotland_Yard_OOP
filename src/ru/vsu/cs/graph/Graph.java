package ru.vsu.cs.graph;

public interface Graph {
    int vertexCount();

    int edgeCount();

    void addAdge(int v1, int v2);

    void removeAdge(int v1, int v2);

    Iterable<Integer> adjacencies(int v);

    default boolean isAdj(int v1, int v2) {
        for (Integer adj : adjacencies(v1)) {
            if (adj == v2) {
                return true;
            }
        }
        return false;
    }
}
