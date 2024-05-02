
package aimauepg;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckersGameGUI extends JFrame {
    private static final int SIZE = 8;
    private char[][] board;
    private char currentPlayer;
    private JPanel boardPanel;
    private JLabel[][] labels;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private JLabel selectedLabel = null;
    private CheckersGame checkersGame;

    public CheckersGameGUI() {
        checkersGame = new CheckersGame();
        setTitle("Checkers Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        board = new char[SIZE][SIZE];
        currentPlayer = 'O';

        initializeBoard();
        createBoardPanel();

        add(boardPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
        updateBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    board[row][col] = ' ';
                } else {
                    if (row < 3) {
                        board[row][col] = 'O'; // Black piece
                    } else if (row > 4) {
                        board[row][col] = 'X'; // White piece
                    } else {
                        board[row][col] = ' '; // Empty space in the middle of the board
                    }
                }
            }
        }
    }

    private void createBoardPanel() {
        boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        labels = new JLabel[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JLabel label = new JLabel(Character.toString(board[row][col]), SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBackground((row + col) % 2 == 0 ? Color.GRAY : Color.WHITE);
                label.addMouseListener(new PieceClickListener(row, col));
                labels[row][col] = label;
                boardPanel.add(label);
            }
        }
    }

    private void movePiece(int endRow, int endCol) {
        if (selectedRow != -1 && selectedCol != -1) {
            // Move a peça no tabuleiro do jogo
            checkersGame.makeMove(selectedRow, selectedCol, endRow, endCol);
            // Atualiza o tabuleiro da interface gráfica com o novo estado do tabuleiro do
            // jogo
            updateBoard();
            selectedRow = -1;
            selectedCol = -1;

            // A vez é do próximo jogador (IA ou humano)
            if (!checkersGame.isGameOver() && checkersGame.getPlayer(checkersGame.getInitialState()) == 'X') {
                // É a vez da IA
                makeAIMove();
            }
        }
    }

    private void makeAIMove() {
        char[][] currentState = checkersGame.getInitialState();
        String aiAction = checkersGame.iterativeDeepeningAlphaBetaSearch.makeDecision(currentState);
        String[] parts = aiAction.split(":");
        String[] start = parts[0].split(",");
        String[] end = parts[1].split(",");
        int startRow = Integer.parseInt(start[0]);
        int startCol = Integer.parseInt(start[1]);
        int endRow = Integer.parseInt(end[0]);
        int endCol = Integer.parseInt(end[1]);

        checkersGame.makeMove(startRow, startCol, endRow, endCol);
        updateBoard();
    }

    /**
     * @param state
     * @return
     */

    private void updateBoard() {
        char[][] board = checkersGame.getInitialState(); // Obtém o estado atual do tabuleiro do jogo

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                labels[row][col].setText(Character.toString(board[row][col]));
            }
        }
    }

    private class PieceClickListener extends MouseAdapter {
        private int row;
        private int col;
        private boolean isSelected;

        public PieceClickListener(int row, int col) {
            this.row = row;
            this.col = col;
            this.isSelected = false;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!isSelected && board[row][col] == currentPlayer) {
                isSelected = true;
                selectedRow = row;
                selectedCol = col;
                if (selectedLabel != null) {
                    selectedLabel.setBackground((row + col) % 2 == 0 ? Color.GRAY : Color.WHITE);
                }
                selectedLabel = labels[row][col];
                selectedLabel.setBackground(Color.YELLOW); // Change the color to indicate selection
            } else if (isSelected) {
                movePiece(row, col);
                isSelected = false;
                selectedLabel.setBackground((row + col) % 2 == 0 ? Color.GRAY : Color.WHITE);
                selectedLabel = null;
            }
            updateBoard();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CheckersGameGUI::new);

    }
}
