package edu.touro.mco364;

import java.awt.*;
import java.util.Scanner;

class OthelloConsole {


    OthelloConsole(OthelloModelInterface model){
        startGame(model);

    }


    private void displayGrid(OthelloModelInterface model)
    {
        System.out.println("   1   2   3   4   5   6   7   8"); // to display column numbers
        for (int i = 0; i < 8; i++)
        {
            System.out.print(i + 1 + " "); // to display row numbers
            for (int j = 0; j < 8; j++)
            {
                if (model.getCelState(i, j).equals(" ")) {
                    System.out.print("|_| "); // to make it look more like a board
                } else {
                    System.out.print("|" + model.getCelState(i,j) + "| ");
                }
            }
            System.out.println();
        }
    }

    private void startGame(OthelloModelInterface model) {
        CellState playerColor = CellState.B;
        CellState computerColor = CellState.W;
        model.setupBoard();
        if (model.gameType() == 1) { //to make console work with both models we will check which model is being used then run the proper method
            onePlayerGame(model);
        }else {
            twoPlayerGame(model);
        }
    }

    private void onePlayerGame(OthelloModelInterface model){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Your Name : ");
        String Player = input.nextLine();

        int boardCounter = 0;

        while(boardCounter < 60) { //The game ends once the last (60th) piece is placed

            boolean turnComplete = false;
            int pass = 0;

            while (!turnComplete && pass == 0) { //while loop ensures that round only progresses after a valid move is made or player passes
                displayGrid(model);
                System.out.println(Player + "'s turn: (Black)"); //Player will be back and go first
                Scanner playerInput = new Scanner(System.in);
                System.out.println("In which row do you want to go?");
                int row = playerInput.nextInt();
                System.out.println("In which column do you want to go?");
                int col = playerInput.nextInt();
                if (model.isValid(row-1 , col-1 , CellState.B)) { //subtract 1 to match board
                    model.makeMove(row -1 , col -1 , CellState.B);
                    boardCounter ++;
                    turnComplete = true;
                } else {
                    System.out.println("Invalid move. Type 0 to try again or any other key to pass.");
                    pass = playerInput.nextInt();
                }
            }

            turnComplete = false;
            pass = 0;


            displayGrid(model);
            System.out.println("Computer's turn: (White)"); //computer will be white
            Point p = model.bestMove(CellState.W);
            model.makeMove(p.x,p.y,CellState.W);
            boardCounter++;

        }
        displayGrid(model);
        determineWinner(model);
    }

    private void twoPlayerGame(OthelloModelInterface model) //TODO player's turn into it's own method to reduce DRYness
    {

        Scanner name = new Scanner(System.in);
        System.out.println("Enter Player 1's Name : ");
        String Player1 = name.nextLine();
        System.out.println("Enter Player 2's Name : ");
        String Player2 = name.nextLine();

        int boardCounter = 0;

        while(boardCounter < 60) { //The game ends once the last (60th) piece is placed

            boolean turnComplete = false;
            int pass = 0;

            while (!turnComplete && pass == 0) { //while loop ensures that round only progresses after a valid move is made or player passes
                displayGrid(model);
                System.out.println(Player1 + "'s turn: (Black)"); //first player is always black
                Scanner playerInput = new Scanner(System.in);
                System.out.println("In which row do you want to go?");
                int row = playerInput.nextInt();
                System.out.println("In which column do you want to go?");
                int col = playerInput.nextInt();
                if (model.isValid(row-1 , col-1 , CellState.B)) { //subtract 1 to match board
                    model.makeMove(row -1 , col -1 , CellState.B);
                    boardCounter ++;
                    turnComplete = true;
                } else {
                    System.out.println("Invalid move. Type 0 to try again or any other key to pass.");
                    pass = playerInput.nextInt();
                }
            }

            turnComplete = false;
            pass = 0;

            while (!turnComplete && pass == 0) {
                displayGrid(model);
                System.out.println(Player2 + "'s turn: (White)"); //second player is always white
                Scanner playerInput = new Scanner(System.in);
                System.out.println("In which row do you want to go?");
                int row = playerInput.nextInt();
                System.out.println("In which column do you want to go?");
                int col = playerInput.nextInt();
                if (model.isValid(row -1, col-1, CellState.W)) {
                    model.makeMove(row -1 , col -1 , CellState.W);
                    boardCounter++;
                    turnComplete = true;
                }else {
                    System.out.println("Invalid move. Type 0 to try again or any other key to pass.");
                    pass = playerInput.nextInt();
                }
            }
        }
        displayGrid(model);
        determineWinner(model);
    }



    private void determineWinner(OthelloModelInterface model)
    {
        int counter = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++)
            {
                if (model.getCelState(i, j).equals("W"))
                    counter++;
            }
        }
        if (counter > 32){
            System.out.println("White wins!");
        }else if (counter < 32){
            System.out.println("Black wins!");
        } else{
            System.out.println("It's a tie");
        }

}

}
