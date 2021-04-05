package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;

public class Board implements WorldState {
    private final int BLANK = 0;
    private final int[][] tiles;
    private final int size;
    private int distHamming;
    private int distManhattan;

    public Board(int[][] tiles) {
        size = tiles.length;
        this.tiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, size);
        }

        distHamming = 0;
        distManhattan = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = tileAt(i, j);
                if (value != BLANK && value != i * size + j + 1) {
                    distHamming++;
                    int sollR = (value - 1) / size;
                    int sollC = (value - 1) % size;
                    distManhattan += Math.abs(sollR - i) + Math.abs(sollC - j);
                }
            }
        }
    }

    private void checkLength(int i) {
        if (i >= size() || i < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    public int tileAt(int i, int j) {
        checkLength(i);
        checkLength(j);
        return tiles[i][j];
    }

    public int size() {
        return size;
    }

    @Override
    // taken over from lecture solution
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        return distHamming;
    }

    public int manhattan() {
        return distManhattan;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }

        if (!(y instanceof Board)) {
            return false;
        }

        Board o = (Board) y;
        if (o.size() != this.size()) {
            return false;
        }
        for (int r = 0; r < size(); r++) {
            for (int c = 0; c < size(); c++) {
                if (this.tileAt(r, c) != o.tileAt(r, c)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tiles);
    }

    @Override
    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
