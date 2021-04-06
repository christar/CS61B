package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    Comparator<Integer> comparator = (o1, o2) -> distTo[o1] + h(o1) - distTo[o2] - h(o2);
    MinPQ<Integer> pq = new MinPQ<>(comparator);

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private  void astar(int v) {
        marked[v] = true;
        pq.insert(v);

        while (!pq.isEmpty()) {
            int curr = pq.delMin();
            for (int n : maze.adj(curr)) {
                if (!marked[n]) {
                    edgeTo[n] = curr;
                    distTo[n] = distTo[curr] + 1;
                    marked[n] = true;
                    announce();
                    if (n == t) {
                        return;
                    }
                    pq.insert(n);
                }
            }
        }

    }

    @Override
    public void solve() {
        astar(s);
    }

}

