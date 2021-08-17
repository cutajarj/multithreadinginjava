package com.cutajarjames.multithreading.barrier;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MatrixMultiply {
    final static int MATRIX_SIZE = 400;
    /*int[][] matrixA = new int[][]{
            {3, 1, -4},
            {2, -3, 1},
            {5, -2, 0}
    };
    int[][] matrixB = new int[][]{
            {1, -2, -1},
            {0, 5, 4},
            {-1, -2, 3}
    };*/
    int[][] matrixA = new int[MATRIX_SIZE][MATRIX_SIZE];
    int[][] matrixB = new int[MATRIX_SIZE][MATRIX_SIZE];
    int[][] result = new int[MATRIX_SIZE][MATRIX_SIZE];
    CyclicBarrier workStart = new CyclicBarrier(MATRIX_SIZE + 1);
    CyclicBarrier workComplete = new CyclicBarrier(MATRIX_SIZE + 1);

    public void workOutRow(int row) {
        try {
            while(true) {
                workStart.await();
                for (int col = 0; col < MATRIX_SIZE; col++)
                    for (int i = 0; i < MATRIX_SIZE; i++)
                        result[row][col] += matrixA[row][i] * matrixB[i][col];
                workComplete.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void randomMatrix(int[][] matrix) {
        for (int row = 0; row < MATRIX_SIZE; row++)
            for (int col = 0; col < MATRIX_SIZE; col++)
                matrix[row][col] = (int) (Math.random() * 10) - 5;
    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        var matrixMultiply = new MatrixMultiply();
        for (int row = 0; row < MATRIX_SIZE; row++) {
            int finalRow = row;
            new Thread(() -> matrixMultiply.workOutRow(finalRow)).start();
        }

        var start = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            Arrays.stream(matrixMultiply.result).forEach(a -> Arrays.fill(a, 0));
            randomMatrix(matrixMultiply.matrixA);
            randomMatrix(matrixMultiply.matrixB);
            matrixMultiply.workStart.await();
            matrixMultiply.workComplete.await();
            /*for (int[] row: matrixMultiply.result)
                System.out.println(Arrays.toString(row));*/
        }
        var end = System.currentTimeMillis();
        System.out.println("Done, timetaken: " + (end - start));
    }

}
