package org.example.graphs_basics;

import java.util.LinkedList;

public class DungeonProblem {
    public static void run() {
        int numRow = 5;
        int numCol = 6;
        String[][] inputMatrix = new String[numRow][numCol];
        for(int i=0; i < numRow; i++) {
            for(int j=0; j < numCol; j++) {
                inputMatrix[i][j] = ".";
            }
        }

        int startRow = 0;
        int startCol = 0;
        inputMatrix[startRow][startCol] = "S";
        inputMatrix[4][3] = "E";
        inputMatrix[0][1] = "#";
        inputMatrix[0][4] = "#";
        inputMatrix[0][5] = "#";
        inputMatrix[0][3] = "#";
        inputMatrix[1][2] = "#";
        inputMatrix[1][4] = "#";
        inputMatrix[2][0] = "#";
        inputMatrix[2][5] = "#";

        for(int i=0; i < numRow; i++) {
            for(int j=0; j < numCol; j++) {
                System.out.print(inputMatrix[i][j] + " ");
            }
            System.out.println();
        }

        int move_count = solveDungeon(inputMatrix, numRow, numCol, startRow, startCol);
        if(move_count == -1){
            System.out.println("The Dungeon cannot be escaped");
        }
        else {
            System.out.println("Escaping the dungeon took " + move_count + " moves!");
        }
    }

    public static int solveDungeon(
            String[][] inputMatrix,
            int numRow,
            int numCol,
            int startRow,
            int startCol
    ){
        LinkedList<Integer> rowPositionQueue = new LinkedList<Integer>(); // init the Queue to store the visited X-axis positions
        LinkedList<Integer> colPositionQueue = new LinkedList<Integer>(); // init the Queue to store the visited Y-axis positions

        // Variables to track the number of steps taken
        int move_count = 0;
        int node_left_in_layer = 1;
        int node_in_next_layer = 0;

        // Variable to track if the Escape position is ever reached
        boolean reached_end = false;

        // numRow x numCol matrix to track if position (i,j) has ever been visited
        boolean[][] visitedMatrix = new boolean[numRow][numCol];

        // Variables to store the components of the different allowed vector directions
        // North, South, East, West
        int[] rowDirection = {0,0,1,-1};
        int [] colDirection = {1,-1,0,0};

        rowPositionQueue.addLast(startRow);
        colPositionQueue.addLast(startCol);

        visitedMatrix[startRow][startCol] = true;

        while (rowPositionQueue.size() > 0) {
            int rowPos = rowPositionQueue.poll();
            int colPos = colPositionQueue.poll();

            if(inputMatrix[rowPos][colPos].equals("E")) {
                reached_end = true;
                return move_count;
            }

            node_in_next_layer = explore_neighbors(rowPos, colPos, visitedMatrix, inputMatrix, rowPositionQueue,
                    colPositionQueue, rowDirection, colDirection, numRow, numCol, node_in_next_layer);
            node_left_in_layer--;

            if(node_left_in_layer == 0){
                node_left_in_layer = node_in_next_layer;
                node_in_next_layer = 0;
                move_count++;
            }

            if (reached_end){
                return move_count;
            }
        }
        return -1;
    }

    private static int explore_neighbors(
            int rowPos,
            int colPos,
            boolean[][] visitedMatrix,
            String[][] inputMatrix,
            LinkedList<Integer> rowPositionQueue,
            LinkedList<Integer> colPositionQueue,
            int[] rowDirection,
            int[] colDirection,
            int numRow,
            int numCol,
            int node_in_next_layer
    ) {
        for(int i=0; i< rowDirection.length; i++){
            int newRowPos = rowPos + rowDirection[i];
            int newColPos = colPos + colDirection[i];

            if(newRowPos < 0 || newRowPos >= numRow){
                continue;
            }

            if(newColPos < 0 || newColPos >= numCol){
                continue;
            }

            if(visitedMatrix[newRowPos][newColPos]) {
                continue;
            }

            if(inputMatrix[newRowPos][newColPos].equals("#")) {
                continue;
            }

            rowPositionQueue.addLast(newRowPos);
            colPositionQueue.addLast(newColPos);
            visitedMatrix[newRowPos][newColPos] = true;
            node_in_next_layer++;
        }
        return node_in_next_layer;
    }
}
