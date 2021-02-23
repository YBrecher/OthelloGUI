package edu.touro.mco364;

import static org.junit.jupiter.api.Assertions.*;


class OthelloModelTest {

    private OthelloModel test = new OthelloModel();

    @org.junit.jupiter.api.Test
    void setupBoard()
    {
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

    @org.junit.jupiter.api.Test
    void makeMove()
    {
        test.setupBoard();
        test.makeMove(4,3 ,CellState.B);
        assertEquals("B",test.getCelState(4,3));
        test.makeMove(3,5,CellState.W);
        assertEquals("W", test.getCelState(3,5));
    }

    @org.junit.jupiter.api.Test
    void isValid()
    {
        test.setupBoard();
        assertTrue(test.isValid(5,4,CellState.B));
        assertTrue(test.isValid(3,2,CellState.B));
        assertTrue(test.isValid(2,3,CellState.B));
        assertTrue(test.isValid(4,5,CellState.B));
        assertFalse(test.isValid(2,6,CellState.W));
        assertFalse(test.isValid(-2,13,CellState.W));
    }

    @org.junit.jupiter.api.Test
    void isLegalIndex()
    {
        test.setupBoard();
        assertTrue(test.isLegalIndex(6,4));
        assertTrue(test.isLegalIndex(0,0));
        assertTrue(test.isLegalIndex(7,7));
        assertTrue(test.isLegalIndex(5,2));
        assertFalse(test.isLegalIndex(6,13));
        assertFalse(test.isLegalIndex(-1,5));
        assertFalse(test.isLegalIndex(4,8));
    }

    @org.junit.jupiter.api.Test
    void reverseState()
    {
        CellState actual = test.reverseState(CellState.W);
        assertEquals(CellState.B, actual);
        actual = test.reverseState(CellState.B);
        assertEquals(CellState.W, actual);
    }

    @org.junit.jupiter.api.Test
    void flipCells()
    {
        test.setupBoard();
        test.makeMove(4,3,CellState.B);
        assertEquals("B", test.getCelState(4,3));
        test.makeMove(3,5,CellState.W);
        assertEquals("W", test.getCelState(3,5));
    }

    @org.junit.jupiter.api.Test
    void getCellState()
    {
        test.setupBoard();
        String actual = test.getCelState(3,4);
        assertEquals("B", actual);
        String actual2 = test.getCelState(4,4);
        assertEquals("W", actual2);
        String actual3 = test.getCelState(7,3);
        assertEquals(" ", actual3);

    }
}