package edu.touro.mco364;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OthelloModelOnePlayerTest {

    private OthelloModelOnePlayer test = new OthelloModelOnePlayer();

    @Test
    void setupBoard() {
        test.setupBoard();
        assertEquals("W", test.getCelState(3,3));
        assertEquals("W", test.getCelState(4,4));
        assertEquals("B", test.getCelState(3,4));
        assertEquals("B", test.getCelState(4,3));
        assertEquals(" ", test.getCelState(5,2));
        assertEquals(" ", test.getCelState(3,7));
        assertEquals(" ", test.getCelState(6,3));
        assertEquals(" ", test.getCelState(1,1));
        assertEquals(" ", test.getCelState(7,4));
    }

    @Test
    void makeMove() {
        test.setupBoard();
        test.makeMove(4,3 ,CellState.B);
        assertEquals("B",test.getCelState(4,3));
        test.makeMove(3,5,CellState.W);
        assertEquals("W", test.getCelState(3,5));
    }

    @Test
    void isValid() {
        test.setupBoard();
        assertTrue(test.isValid(5,4,CellState.B));
        assertTrue(test.isValid(3,2,CellState.B));
        assertTrue(test.isValid(2,3,CellState.B));
        assertTrue(test.isValid(4,5,CellState.B));
        assertFalse(test.isValid(2,6,CellState.W));
        assertFalse(test.isValid(-2,13,CellState.W));
    }

    @Test
    void getCelState() {
        test.setupBoard();
        String actual = test.getCelState(3,4);
        assertEquals("B", actual);
        String actual2 = test.getCelState(4,4);
        assertEquals("W", actual2);
        String actual3 = test.getCelState(7,3);
        assertEquals(" ", actual3);

    }

    @Test
    void reverseState() {
        CellState actual = test.reverseState(CellState.W);
        assertEquals(CellState.B, actual);
        actual = test.reverseState(CellState.B);
        assertEquals(CellState.W, actual);
    }

    @Test
    void flipCells() {
        test.setupBoard();
        test.makeMove(4,3,CellState.B);
        assertEquals("B", test.getCelState(4,3));
        test.makeMove(3,5,CellState.W);
        assertEquals("W", test.getCelState(3,5));
    }

    @Test
    void isLegalIndex() {
        test.setupBoard();
        assertTrue(test.isLegalIndex(6,4));
        assertTrue(test.isLegalIndex(0,0));
        assertTrue(test.isLegalIndex(7,7));
        assertTrue(test.isLegalIndex(5,2));
        assertFalse(test.isLegalIndex(6,13));
        assertFalse(test.isLegalIndex(-1,5));
        assertFalse(test.isLegalIndex(4,8));
    }

    @Test
    void possibleMoves() {
        test.setupBoard();
        ArrayList<Point> moves = test.possibleMoves(CellState.B);
        assertEquals(2, moves.get(0).x);
        assertEquals(3, moves.get(0).y);
        assertEquals(3, moves.get(1).x);
        assertEquals(2, moves.get(1).y);
        assertEquals(4, moves.get(2).x);
        assertEquals(5, moves.get(2).y);
        assertEquals(5, moves.get(3).x);
        assertEquals(4, moves.get(3).y);


    }

    @Test
    void bestMove() {
        test.setupBoard();
        test.makeMove(3,2,CellState.B);
        test.makeMove(2,4,CellState.W);
        test.makeMove(4,2,CellState.W);
        test.makeMove(3,5,CellState.B);
        Point result = test.bestMove(CellState.B);
        assertEquals(1,result.getX());
        assertEquals(5,result.getY());
        //should be 5,4 : 5,3 : or 5,2

    }

    @Test
    void bestMoveWhite() {
        test.setupBoard();
        test.makeMove(3, 2, CellState.B);
        test.makeMove(2, 4, CellState.W);
        test.makeMove(4, 5, CellState.B);
        Point result = test.bestMove(CellState.W);
        assertEquals(3, result.getX());
        assertEquals(1, result.getY());
    }
}