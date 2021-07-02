import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture pic;
    private int width;
    private int height;
    private double[][] energyMatrix;

    public SeamCarver(Picture picture) {
        this.pic = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
        this.energyMatrix = new double[this.width][this.height];
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Color left = picture.get((this.width + x - 1) % this.width, y);
                Color right = picture.get((x + 1) % this.width, y);
                Color up = picture.get(x, (this.height + y - 1) % this.height);
                Color down = picture.get(x, (y + 1) % this.height);

                double energyX = (left.getBlue() - right.getBlue())
                        * (left.getBlue() - right.getBlue());
                energyX += (left.getGreen() - right.getGreen())
                        * (left.getGreen() - right.getGreen());
                energyX += (left.getRed() - right.getRed())
                        * (left.getRed() - right.getRed());
                double energyY = (up.getBlue() - down.getBlue())
                        * (up.getBlue() - down.getBlue());
                energyY += (up.getGreen() - down.getGreen())
                        * (up.getGreen() - down.getGreen());
                energyY += (up.getRed() - down.getRed())
                        * (up.getRed() - down.getRed());
                this.energyMatrix[x][y] = energyX + energyY;
            }
        }
    }

    public Picture picture() {
        return this.pic;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public double energy(int x, int y) {
        if (x >= this.width() || x < 0 || y >= this.height() || y < 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.energyMatrix[x][y];
    }

    private double[][] sumVerticalSeamMatrix(double[][] matrix) {
        int matrixWidth = matrix.length;
        int matrixHeight = matrix[0].length;
        double[][] copy = new double[matrixWidth][];
        for (int x = 0; x < matrixWidth; x++) {
            copy[x] = matrix[x].clone();
        }
        for (int y = 0; y < matrixHeight; y++) {
            for (int x = 0; x < matrixWidth; x++) {
                if (y != 0) {
                    double lastMin;
                    if (x == 0) {
                        lastMin = Math.min(copy[x][y - 1], copy[x + 1][y - 1]);
                    } else if (x == matrixWidth - 1) {
                        lastMin = Math.min(copy[x][y - 1], copy[x - 1][y - 1]);
                    } else {
                        lastMin = Math.min(copy[x + 1][y - 1], copy[x - 1][y - 1]);
                        lastMin = Math.min(lastMin, copy[x][y - 1]);
                    }
                    copy[x][y] += lastMin;
                }
            }
        }
        return copy;
    }

    private int[] findVerticalSeamHelper(double[][] originalMatrix, double[][] matrix) {
        int matrixWidth = matrix.length;
        int matrixHeight = matrix[0].length;
        int[] seamX = new int[matrixHeight];
        double min = Double.MAX_VALUE;
        for (int x = 0; x < matrixWidth; x++) {
            if (matrix[x][matrixHeight - 1] < min) {
                min = matrix[x][matrixHeight - 1];
                seamX[matrixHeight - 1] = x;
            }
        }

        for (int y = matrixHeight - 2; y >= 0; y--) {
            int lastX = seamX[y + 1];
            double targetEnergy = matrix[lastX][y + 1] - originalMatrix[lastX][y + 1];
            if (matrix[lastX][y] == targetEnergy) {
                seamX[y] = lastX;
            } else if (lastX > 0 && matrix[lastX - 1][y] == targetEnergy) {
                seamX[y] = lastX - 1;
            } else {
                seamX[y] = lastX + 1;
            }

        }
        return seamX;
    }

    public int[] findHorizontalSeam() {
        double[][] transposeEnergyMatrix = new double[this.height()][this.width()];
        for (int x = 0; x < this.height(); x++) {
            for (int y = 0; y < this.width(); y++) {
                transposeEnergyMatrix[x][y] = this.energyMatrix[y][x];
            }
        }
        double[][] sumHorizontalMatrix = sumVerticalSeamMatrix(transposeEnergyMatrix);
        return findVerticalSeamHelper(transposeEnergyMatrix, sumHorizontalMatrix);
    }

    public int[] findVerticalSeam() {
        double[][] sumVerticalMatrix = sumVerticalSeamMatrix(this.energyMatrix);
        return findVerticalSeamHelper(this.energyMatrix, sumVerticalMatrix);
    }

    public void removeHorizontalSeam(int[] seam) {
        if (this.height() != seam.length) {
            throw new IllegalArgumentException();
        }
        for (int s : seam) {
            if (s < 0 || s >= this.width()) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] - seam[i + 1] > 1 || seam[i] - seam[i + 1] < -1) {
                throw new IllegalArgumentException();
            }
        }
        SeamRemover.removeHorizontalSeam(this.picture(), seam);
    }

    public void removeVerticalSeam(int[] seam) {
        if (this.width() != seam.length) {
            throw new IllegalArgumentException();
        }
        for (int s : seam) {
            if (s < 0 || s >= this.height()) {
                throw new IllegalArgumentException();
            }
        }
        SeamRemover.removeVerticalSeam(this.picture(), seam);
    }
}
