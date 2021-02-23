package edu.touro.mco364;

import javax.swing.*;
import java.awt.*;

class OthelloGUI extends JFrame {

    private JLabel statusBar = new JLabel("Player 1 turn: (Black)");
    private JPanel centerPanel = new JPanel();
    private JPanel statusPanel = new JPanel();
    private JButton pass = new JButton("Pass");
    private JButton checkScore = new JButton("Check Score");
    private JTextField blackScore = new JTextField("Black Score");
    private JTextField whiteScore = new JTextField("White Score");
    private CellState state = CellState.B;


    OthelloGUI(OthelloModelInterface model) {

        model.setupBoard();

        setTitle("Othello");
        super.setSize(900, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        centerPanel.setLayout(new GridLayout(8, 8));
        statusPanel.setLayout(new FlowLayout());

        add(centerPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        statusPanel.add(statusBar);
        statusPanel.add(pass);
        statusPanel.add(checkScore);
        statusPanel.add(whiteScore);
        statusPanel.add(blackScore);

        pass.addActionListener(e -> {  //pass button will allow players to pass when out of moves
            state = model.reverseState(state);
            if (state == CellState.W){
                statusBar.setText("Player 2 turn: (White)");
            }else{
                statusBar.setText("Player 1 turn: (Black)");
            }
        });


        checkScore.addActionListener(e -> { //check score will let us know who's winning
            int whiteCounter = 0;
            int blackCounter = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (model.getCelState(i, j).equals("W")){
                        whiteCounter++;
                    }else if (model.getCelState(i, j).equals("B")){
                        blackCounter++;
                    }
                }
            }
            whiteScore.setText("White: " + whiteCounter);
            blackScore.setText("Black: " + blackCounter);
        });



        JButton[][] buttons = new JButton[8][8]; //this is for creating the actual buttons

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new JButton();
                if (model.getCelState(i, j).equals("W")){
                    buttons[i][j].setBackground(Color.white);
                }else if (model.getCelState(i, j).equals("B")){
                    buttons[i][j].setBackground(Color.black);
                }else{
                    buttons[i][j].setBackground(new Color(12, 92, 59));
                }
                centerPanel.add(buttons[i][j]);
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(
                        e -> {
                            {
                                if (model.isValid(row, col, state)) { //if move is valid then...

                                    model.makeMove(row, col, state); //make the move on the model (this also flips model board)

                                    for (int i1 = 0; i1 < 8; i1++) { //update GUI board to make flips show up
                                        for (int j1 = 0; j1 < 8; j1++) {
                                            // buttons[i1][j1].setText(model.getCelState(i1, j1));
                                            if  (model.getCelState(i1, j1).equals("W")){
                                                buttons[i1][j1].setBackground(Color.white);
                                            }else if (model.getCelState(i1, j1).equals("B")){
                                                buttons[i1][j1].setBackground(Color.black);
                                            }
                                        }
                                    }

                                    state = model.reverseState(state); //switch the opposite state (for next turn)

                                    if (state == CellState.W){ //have statusBar say who's turn is next
                                        statusBar.setText("Player 2 turn: (White)");
                                    }else{
                                        statusBar.setText("Player 1 turn: (Black)");
                                    }

                                } else {
                                    statusBar.setText("Invalid move. Try again. If you are out of moves hit Pass.");
                                }
                            }
                        }
                );
            }
        }
        this.setVisible(true);
    }
}