package edu.touro.mco364;

import java.awt.*;

public class OthelloModel implements OthelloModelInterface{

    private CellState[][] grid;

    OthelloModel() {
        grid = new CellState[8][8];

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                grid[i][j] = CellState.NONE;
    }

    public void setupBoard() {
        grid[3][3] = CellState.W;
        grid[3][4] = CellState.B;
        grid[4][3] = CellState.B;
        grid[4][4] = CellState.W;

    }

    public void makeMove(int row, int col, CellState state) {
        if (isValid(row, col, state))
        {
            grid[row][col] = state;
            flipCells(row, col, state);

        }
    }

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
        return  false;
    }

    public String getCelState(int row, int col) {
        return grid[row][col] == CellState.W ?  "W" :
                grid[row][col] == CellState.B ? "B": " "; //double conditional op instead of if else if
    }

    private void flipCells(int targetRow, int targetCol, CellState state) {

        for (int row = -1; row <= +1; row++)
            for (int col = -1; col <= +1; col++)
            {
                if (row == 0 && col == 0)
                    continue;

                int stopFlipping =checkLine(targetRow,row,targetCol,col,state);

                for (int j = 1; j < stopFlipping; j++) {
                    grid[targetRow + (row * j)][targetCol + (col * j)] = state;
                }
            }
    }

    //Helper methods
    private int checkLine(int targetRow, int row, int targetCol, int col, CellState state) {
        CellState reverseState = reverseState(state);

        if (!isLegalIndex(targetRow + row, targetCol + col)) { return -1;}

            if (grid[targetRow + row][targetCol + col] == reverseState) //if our cell is empty and is touching an opposite cell then we will continue our check

            for (int i = 2; i < 8; i++)
            {
                if (!isLegalIndex(targetRow + (row * i), targetCol + (col * i))) {
                    continue;
                }

                if (grid[targetRow + (row * i)][targetCol + (col * i)] == CellState.NONE)
                    break;
                else if (grid[targetRow + (row * i)][targetCol + (col * i)] == state)
                {
                    return i; //this method returns an int (as opposed to a boolean) b/c it is needed for the flipCells method to tell us when to stop flipping
                }
            }
        return -1;
    }

    boolean isLegalIndex(int row, int col)  // this is to avoid index out of bounds exception
    {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public CellState reverseState(CellState state) //this returns the opposite cell state (None will come up as B but this method should never be used on a None cell)
    {
        return state == CellState.B ? CellState.W : CellState.B;
    }
    public int gameType(){return 2;}
    public Point bestMove(CellState state){return null;}
}



