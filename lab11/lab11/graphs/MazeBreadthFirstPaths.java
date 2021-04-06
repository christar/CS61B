package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze m;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        this.m = m;
        s = m.xyTo1D(sourceX, sourceY);
        t = m.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> q = new Queue<>();
        marked[s] = true;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int curr = q.dequeue();

            for (int v : m.adj(curr)) {
                if (!marked[v]) {
                    edgeTo[v] = curr;
                    distTo[v] = distTo[curr] + 1;
                    marked[v] = true;
                    announce();
                    if (v == t) {
                        return;
                    }
                    q.enqueue(v);
                }
            }
        }
    }

    @Override
    public void solve() {
        bfs();
    }
}

