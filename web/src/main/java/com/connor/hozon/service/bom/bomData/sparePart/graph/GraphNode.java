package com.connor.hozon.service.bom.bomData.sparePart.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/23 11:01
 * @Modified By:
 */
public class GraphNode<T> {
    T data;
    List<GraphNode<T>> neighborList;
    boolean visited;

    public GraphNode(T data) {
        this.data = data;
        neighborList = new ArrayList<GraphNode<T>>();
        visited = false;
    }

    public boolean equals(GraphNode<T> node) {
        return this.data.equals(node.data);
    }

    /**
     * 还原图中所有节点为未访问
     */
    public void restoreVisited() {
        restoreVisited(this);
    }

    /**
     * 还原node的图所有节点为未访问
     *
     * @param node
     */
    private void restoreVisited(GraphNode<T> node) {
        if (node.visited) {
            node.visited = false;
        }

        List<GraphNode<T>> neighbors = node.neighborList;
        for (int i = 0; i < neighbors.size(); i++) {
            restoreVisited(neighbors.get(i));
        }

    }
}