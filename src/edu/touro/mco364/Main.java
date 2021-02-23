package edu.touro.mco364;


public class Main {



    public static void main(String[] args) {
//        OthelloConsoleGame test = new OthelloConsoleGame();
//        test.startGame();
//      OthelloModelInterface model = new OthelloModel();
////      new OthelloGUI(model); //dependency injection ... c-tor injection
        OthelloModelInterface model = new OthelloModel();
        new OthelloGUI(model);

    }
}

