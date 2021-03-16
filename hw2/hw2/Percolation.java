package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private boolean[] open;
    private int countOpen;
    private final int HEIGHT;
    private final int WIDTH;
    private final int HEAD_SENT_OFFSET;
    private final int TAIL_SENT_OFFSET;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Argument N must be positive!");
        }

        HEIGHT = N;
        WIDTH = N;
        HEAD_SENT_OFFSET = 0;
        TAIL_SENT_OFFSET = N * N + 1;
        grid = new WeightedQuickUnionUF(N * N + 2);
        open = new boolean[N * N + 2];
        open[HEAD_SENT_OFFSET] = true;
        open[TAIL_SENT_OFFSET] = true;
        countOpen = 0;
    }

    private int offsetHelper(int row, int col) {
        return row * WIDTH + col + 1;
    }

    public void open(int row, int col) {
        if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH) {
            throw new java.lang.IndexOutOfBoundsException("Parameter out of bound!");
        }
        int offset = offsetHelper(row, col);
        if (!isOpen(row, col)) {
            open[offset] = true;
            countOpen += 1;
        }

        // connect possible open neighbours
        // top
        if (row == 0) {
            // connect with virtual head sentinel cell
            grid.union(offset, HEAD_SENT_OFFSET);
        } else {
            if (isOpen(row - 1, col)) {
                grid.union(offset, offsetHelper(row - 1, col));
            }
        }

        // bottom
        if (row == HEIGHT - 1) {
            // connect with virtual tail sentinel cell
            grid.union(offset, TAIL_SENT_OFFSET);
        } else {
            if (isOpen(row + 1, col)) {
                grid.union(offset, offsetHelper(row + 1, col));
            }
        }

        // left
        if (col > 0 && isOpen(row, col - 1)) {
            grid.union(offset, offsetHelper(row, col - 1));
        }

        // right
        if (col < WIDTH - 1 && isOpen(row, col + 1)) {
            grid.union(offset, offsetHelper(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH) {
            throw new java.lang.IndexOutOfBoundsException("Parameter out of bound!");
        }
        return open[offsetHelper(row, col)];
    }

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && grid.connected(offsetHelper(row, col), HEAD_SENT_OFFSET);
    }

    public int numberOfOpenSites() {
        return countOpen;
    }

    public boolean percolates() {
        return grid.connected(HEAD_SENT_OFFSET, TAIL_SENT_OFFSET);
    }
}
