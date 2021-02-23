package edu.touro.mco364;

import java.awt.*;
import java.util.ArrayList;

public class OthelloModelOnePlayer implements  OthelloModelInterface {
    private CellState[][] grid;

    OthelloModelOnePlayer() {

        grid = new CellState[8][8];

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                grid[i][j] = CellState.NONE;
    }

    @Override
    public void setupBoard() {
        grid[3][3] = CellState.W;
        grid[3][4] = CellState.B;
        grid[4][3] = CellState.B;
        grid[4][4] = CellState.W;
    }

    public int gameType(){return 1;} //used to identify which model is being used

    @Override
    public void makeMove(int row, int col, CellState state) {
        if (isValid(row, col, state)) {
            grid[row][col] = state;
            flipCells(row, col, state);

        }
    }

    @Override
    public boolean isValid(int targetRow, int targetCol, CellState state) {
        if (isLegalIndex(targetRow, targetCol) == false) //avoid index out of bounds error
        {
            return false;
        }

        if (grid[targetRow][targetCol] != CellState.NONE) //make sure target cell is empty
        {
            return false;
        }

        for (int row = -1; row <= +1; row++)
            for (int col = -1; col <= +1; col++) //this nested for loop allows us to check the surrounding squares
            {
                if (row == 0 && col == 0) //this line is to ignore our target square
                    continue;

                int validRow = checkLine(targetRow, row, targetCol, col, state); // this method will then check through the line for each direction
                if (validRow != -1) {
                    return true; //once we find one valid row, we know the move is valid.
                }
            }
        return false;
    }

    @Override
    public String getCelState(int row, int col) {
        return grid[row][col] == CellState.W ? "W" :
                grid[row][col] == CellState.B ? "B" : " "; //double conditional op instead of if else if
    }

    @Override
    public CellState reverseState(CellState state) {
        return state == CellState.B ? CellState.W : CellState.B;
    }

    private void flipCells(int targetRow, int targetCol, CellState state) {
        for (int row = -1; row <= +1; row++)
            for (int col = -1; col <= +1; col++) {
                if (row == 0 && col == 0)
                    continue;

                int stopFlipping = checkLine(targetRow, row, targetCol, col, state);

                for (int j = 1; j < stopFlipping; j++) {
                    grid[targetRow + (row * j)][targetCol + (col * j)] = state;
                }
            }
    }

    private int checkLine(int targetRow, int row, int targetCol, int col, CellState state) {
        CellState reverseState = reverseState(state);

        if (!isLegalIndex(targetRow + row, targetCol + col)) {
            return -1;
        }

        if (grid[targetRow + row][targetCol + col] == reverseState) //if our cell is empty and is touching an opposite cell then we will continue our check

            for (int i = 2; i < 8; i++) {
                if (!isLegalIndex(targetRow + (row * i), targetCol + (col * i))) {
                    continue;
                }

                if (grid[targetRow + (row * i)][targetCol + (col * i)] == CellState.NONE)
                    break;
                else if (grid[targetRow + (row * i)][targetCol + (col * i)] == state) {
                    return i; //this method returns an int (as opposed to a boolean) b/c it is needed for the flipCells method to tell us when to stop flipping
                }
            }
        return -1;
    }

    boolean isLegalIndex(int row, int col)  // this is to avoid index out of bounds exception
    {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    ArrayList<Point> possibleMoves(CellState state) { //finds all possible moves

        ArrayList<Point> moves = new ArrayList<>();
        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++) {
                if (isValid(row, col, state)) {
                    Point p = new Point(row, col);
                    moves.add(p);
                }
            }
        return moves;
//        for (int i = 0; i < moves.size(); i++) {
//            System.out.println(moves.get(i).toString());
    }


    public Point bestMove(CellState state) {

        ArrayList<Point> moveList = possibleMoves(state); //call the possibleMoves method to get lost of possible moves

        Point bestMove = moveList.get(0); //if all moves are equal it will return first available move
        int largestFlip = 0;
        int flipCounter = 0;

        for (Point point : moveList) {

            for (int row = -1; row <= +1; row++)
                for (int col = -1; col <= +1; col++) {
                    if (row == 0 && col == 0)
                        continue;

                    for (int line = 2; line < 8; line++) {
                        if (!isLegalIndex(+(row * line), (point.y + (col * line)))) {
                            continue;
                        }

                        if (grid[(point.x + (row * line))][(point.y + (col * line))] == CellState.NONE)
                            break;
                        else if (grid[(point.x + (row * line))][(point.y + (col * line))] == state) {
                            flipCounter += line - 1;
                        }
                    }
                    if (flipCounter > largestFlip) {
                        bestMove = point;
                        largestFlip = flipCounter;
                        flipCounter = 0; //reset counter after each iteration
                    }
                }
        }
        return bestMove;
    }

}







