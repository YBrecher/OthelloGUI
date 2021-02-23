package edu.touro.mco364;

import java.awt.*;

interface OthelloModelInterface{
    void setupBoard();
    void makeMove(int row, int col, CellState state);
    boolean isValid(int row, int col, CellState state);
    String getCelState(int row, int col);
    CellState reverseState(CellState state);
    Point bestMove(CellState state);
    int gameType();
}
