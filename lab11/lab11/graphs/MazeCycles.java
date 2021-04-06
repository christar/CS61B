package lab11.graphs;

import java.util.ArrayList;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final int start;
    private boolean cycleFound = false;

    public MazeCycles(Maze m) {
        super(m);
        start = 0; // start could be arbitrary
        edgeTo[start] = -1;
        marked[start] = true;
    }

    @Override
    public void solve() {
        solve(start);
    }

    // Helper methods go here
    private void solve(int v) {
        if (cycleFound) {
            return;
        }

        for (int u : maze.adj(v)) {
            if (!marked[u]) {
                edgeTo[u] = v;
                marked[u] = true;
                solve(u);
                if (cycleFound) {
                    return;
                }
            } else {
                if (edgeTo[v] != u) {
                    // cycle detected
                    cycleFound = true;
                    drawCircle(v, u);
                    return;
                }
            }
        }
    }

    void drawCircle(int s, int t) {
        ArrayList<Integer> circleVertices = new ArrayList<>();
        int curr = s;
        while (curr != t) {
            circleVertices.add(curr);
            curr = edgeTo[curr];
        }
        circleVertices.add(t);

        // clear all edgeTo info
        for (int i = 0; i < maze.V(); i++) {
            edgeTo[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < circleVertices.size() - 1; i++) {
            edgeTo[circleVertices.get(i)] = circleVertices.get(i + 1);
        }
        edgeTo[circleVertices.get(circleVertices.size() - 1)] = circleVertices.get(0);
        announce();
    }
}

