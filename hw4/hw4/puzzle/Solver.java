package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;

public class Solver {
    private final int result;
    private Stack<WorldState> path = new Stack<>();

    class SearchNode implements Comparable<SearchNode> {
        WorldState ws;
        int moves;
        SearchNode prev;

        SearchNode(WorldState ws, int moves, SearchNode prev) {
            this.ws = ws;
            this.moves = moves;
            this.prev = prev;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.ws.estimatedDistanceToGoal() + this.moves
                    - (o.ws.estimatedDistanceToGoal() + o.moves);
        }
    }

    public Solver(WorldState initial) {
        SearchNode start = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(start);

        SearchNode curr = pq.delMin();

        while (!curr.ws.isGoal()) {
            for (WorldState n : curr.ws.neighbors()) {
                if (curr.prev != null && curr.prev.ws.equals(n)) {
                    continue;
                }
                pq.insert(new SearchNode(n, curr.moves + 1, curr));
            }
            curr = pq.delMin();
        }
        result = curr.moves;
        while (curr != start) {
            path.push(curr.ws);
            curr = curr.prev;
        }
        path.push(start.ws);
    }

    public int moves() {
        return result;
    }

    public Iterable<WorldState> solution() {
        class Solution implements Iterable<WorldState> {
            private Stack<WorldState> path;

            Solution(Stack<WorldState> path) {
                this.path = path;
            }

            @Override
            public Iterator<WorldState> iterator() {
                return path.iterator();
            }
        }
        return new Solution(path);
    }
}
