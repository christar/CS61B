package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private boolean[] open;
    private int countOpen;
    private final int HEIGHT;
    private final int WIDTH;
    private byte[] backwash; // 1 - bottom, 2 - top, 3 - top and bottom
    private boolean percolated;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Argument N must be positive!");
        }

        HEIGHT = N;
        WIDTH = N;
        grid = new WeightedQuickUnionUF(HEIGHT * WIDTH);
        open = new boolean[HEIGHT * WIDTH];
        backwash = new byte[HEIGHT * WIDTH];
        percolated = false;
        countOpen = 0;
    }

    private int offsetHelper(int row, int col) {
        return row * WIDTH + col;
    }

    private int statusHelper(int row, int col) {
        return backwash[grid.find(offsetHelper(row, col))];
    }

    public void open(int row, int col) {
        if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH) {
            throw new java.lang.IndexOutOfBoundsException("Parameter out of bound!");
        }
        int offset = offsetHelper(row, col);
        byte status = 0; // current backwash status
        if (!isOpen(row, col)) {
            open[offset] = true;
            countOpen += 1;
            // connect possible open neighbours
            // top
            if (row == 0) {
                status = 2;
            } else {
                if (isOpen(row - 1, col)) {
                    status = (byte) (status | statusHelper(row - 1, col));
                    grid.union(offset, offsetHelper(row - 1, col));
                }
            }

            // bottom
            if (row == HEIGHT - 1) {
                status = (byte) (status | 1);
            } else {
                if (isOpen(row + 1, col)) {
                    status = (byte) (status | statusHelper(row + 1, col));
                    grid.union(offset, offsetHelper(row + 1, col));
                }
            }

            // left
            if (col > 0 && isOpen(row, col - 1)) {
                status = (byte) (status | statusHelper(row, col - 1));
                grid.union(offset, offsetHelper(row, col - 1));
            }

            // right
            if (col < WIDTH - 1 && isOpen(row, col + 1)) {
                status = (byte) (status | statusHelper(row, col + 1));
                grid.union(offset, offsetHelper(row, col + 1));
            }

            backwash[grid.find(offset)] = status;
            if (status == 3) {
                percolated = true;
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH) {
            throw new java.lang.IndexOutOfBoundsException("Parameter out of bound!");
        }
        return open[offsetHelper(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH) {
            throw new java.lang.IndexOutOfBoundsException("Parameter out of bound!");
        }
        return statusHelper(row, col) > 1;
    }

    public int numberOfOpenSites() {
        return countOpen;
    }

    public boolean percolates() {
        return percolated;
    }

    public static void main(String[] args) {

    }
}
