package com.chethan.dataStructureAlgorithms.dataStructures.bigO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by $CapName on Dec 26, 2024.
 */

public class Graph {

    private Map<String, ArrayList<String>> adjacentList = new HashMap<>();

    public boolean addVertex(String vertex) {
        if (!adjacentList.containsKey(vertex)) {
            adjacentList.put(vertex, new ArrayList<>());
            return true;
        }
        return false;
    }

    public boolean addEdge(String vertex1, String vertex2) {
        if (adjacentList.containsKey(vertex1) && adjacentList.containsKey(vertex2)) {
            adjacentList.get(vertex1).add(vertex2);
            adjacentList.get(vertex2).add(vertex1);
            return true;
        }
        return false;
    }

    public boolean removeEdge(String vertex1, String vertex2) {
        if (adjacentList.containsKey(vertex1) && adjacentList.containsKey(vertex2)) {
            adjacentList.get(vertex1).remove(vertex2);
            adjacentList.get(vertex2).remove(vertex1);
            return true;
        }
        return false;
    }

    public boolean removeVertex(String vertex) {
        if (adjacentList.containsKey(vertex)) {
            ArrayList<String> list = adjacentList.get(vertex);
            for (String ver : list) {
                adjacentList.get(ver).remove(vertex);
            }
            adjacentList.remove(vertex);
            return true;
        }
        return false;
    }
}
