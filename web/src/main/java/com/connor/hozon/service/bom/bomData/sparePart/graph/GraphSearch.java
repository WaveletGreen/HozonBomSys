package com.connor.hozon.service.bom.bomData.sparePart.graph;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import sun.misc.Queue;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/23 11:00
 * @Modified By:
 */
public class GraphSearch<T> {
    public StringBuffer searchPathDFS = new StringBuffer();
    public StringBuffer searchPathBFS = new StringBuffer();

    /**
     * 深度优先搜索实现
     *
     * @param root
     */
    public void searchDFS(GraphNode<T> root) {
        if (root == null) {
            return;
        }

        // visited root
        if (searchPathDFS.length() > 0) {
            searchPathDFS.append("->");
        }
        searchPathDFS.append(root.data.toString());
        root.visited = true;

        for (GraphNode<T> node : root.neighborList) {
            if (!node.visited) {
                searchDFS(node);
            }
        }
    }

    /**
     * 广度优先搜索实现,使用队列
     *
     * @param root
     */
    public void searchBFS(GraphNode<T> root) throws InterruptedException {
        Queue<GraphNode<T>> queue = new Queue<GraphNode<T>>();

        // visited root
        if (searchPathBFS.length() > 0) {
            searchPathBFS.append("->");
        }
        searchPathBFS.append(root.data.toString());
        root.visited = true;

        // 加到队列队尾
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            GraphNode<T> r = queue.dequeue();
            for (GraphNode<T> node : r.neighborList) {
                if (!node.visited) {
                    searchPathBFS.append("->");
                    searchPathBFS.append(node.data.toString());
                    node.visited = true;

                    queue.enqueue(node);
                }
            }
        }
    }
}

//测试用例

/**
 * GraphSearch测试
 *
 * @author hoaven
 * @see GraphNode
 * @see GraphSearch
 */
class GraphSearchTest {
    GraphNode<Integer> node1;
    GraphNode<Integer> node2;
    GraphNode<Integer> node3;
    GraphNode<Integer> node4;
    GraphNode<Integer> node5;
    GraphNode<Integer> node6;
    GraphNode<Integer> node7;
    GraphNode<Integer> node8;
    GraphNode<Integer> node9;
    GraphNode<Integer> node10;

    @Before
    public void before() {
        node1 = new GraphNode<Integer>(1);
        node2 = new GraphNode<Integer>(2);
        node3 = new GraphNode<Integer>(3);
        node4 = new GraphNode<Integer>(4);
        node5 = new GraphNode<Integer>(5);
        node6 = new GraphNode<Integer>(6);
        node7 = new GraphNode<Integer>(7);
        node8 = new GraphNode<Integer>(8);
        node9 = new GraphNode<Integer>(9);
        node10 = new GraphNode<Integer>(10);

        node1.neighborList.add(node2);
        node1.neighborList.add(node3);

        node2.neighborList.add(node4);
        node2.neighborList.add(node5);
        node2.neighborList.add(node6);

        node3.neighborList.add(node1);
        node3.neighborList.add(node6);
        node3.neighborList.add(node7);
        node3.neighborList.add(node8);

        node4.neighborList.add(node2);
        node4.neighborList.add(node5);

        node5.neighborList.add(node2);
        node5.neighborList.add(node4);
        node5.neighborList.add(node6);

        node6.neighborList.add(node2);
        node6.neighborList.add(node5);
        node6.neighborList.add(node3);
        node6.neighborList.add(node8);
        node6.neighborList.add(node9);
        node6.neighborList.add(node10);

        node7.neighborList.add(node3);

        node8.neighborList.add(node3);
        node8.neighborList.add(node6);
        node8.neighborList.add(node9);

        node9.neighborList.add(node6);
        node9.neighborList.add(node8);
        node9.neighborList.add(node10);

        node10.neighborList.add(node6);
        node10.neighborList.add(node9);
    }

    @Test
    public void searchDFSTest() {
        before();
        GraphSearch<Integer> graphSearch = new GraphSearch<Integer>();
        graphSearch.searchDFS(node1);

        String expectedSearchPath = "1->2->4->5->6->3->7->8->9->10";
        System.out.println(graphSearch.searchPathDFS.toString());
    }

    @Test
    public void searchBFSTest() throws InterruptedException {
        GraphSearch<Integer> graphSearch = new GraphSearch<Integer>();
        graphSearch.searchBFS(node1);

        String expectedSearchPath = "1->2->3->4->5->6->7->8->9->10";
        System.out.println(graphSearch.searchPathBFS.toString());

    }

}
