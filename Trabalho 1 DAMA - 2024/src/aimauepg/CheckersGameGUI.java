package aimauepg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckersGameGUI extends JFrame {
    private static final int SIZE = 8;
    private char[][] board;
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

        board = checkersGame.getInitialState();
        createBoardPanel();

        add(boardPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
        updateBoard();
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
        if (selectedRow != -1 && selectedCol != -1 && checkersGame.isValidMove(selectedRow, selectedCol, endRow, endCol, board)) {
            checkersGame.makeMove(selectedRow, selectedCol, endRow, endCol);
            updateBoard();
            clearHighlights();
            switchToNextPlayer();
        }
    }

    private void updateBoard() {
        char[][] currentBoard = checkersGame.getInitialState();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                labels[row][col].setText(Character.toString(currentBoard[row][col]));
                labels[row][col].setBackground((row + col) % 2 == 0 ? Color.GRAY : Color.WHITE);
            }
        }
    }

    private void clearHighlights() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                labels[row][col].setBackground((row + col) % 2 == 0 ? Color.GRAY : Color.WHITE);
            }
        }
    }

    private void switchToNextPlayer() {
        checkersGame.switchToNextPlayer();
        if (checkersGame.getCurrentPlayer() == 'X') { // Assuming 'X' is the AI
            SwingUtilities.invokeLater(this::makeAIMove);
        }
    }

    private void makeAIMove() {
        try {
            JOptionPane.showMessageDialog(this, "AI is thinking...", "AI Move", JOptionPane.INFORMATION_MESSAGE);
            String aiAction = checkersGame.iterativeDeepeningAlphaBetaSearch.makeDecision(board);
            if (aiAction != null && !aiAction.isEmpty()) {
                applyMove(aiAction);
            } else {
                JOptionPane.showMessageDialog(this, "AI did not find a valid move.", "No Move", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred during AI processing: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyMove(String move) {
        String[] parts = move.split(":");
        int startRow = Integer.parseInt(parts[0].split(",")[0]);
        int startCol = Integer.parseInt(parts[0].split(",")[1]);
        int endRow = Integer.parseInt(parts[1].split(",")[0]);
        int endCol = Integer.parseInt(parts[1].split(",")[1]);
        checkersGame.makeMove(startRow, startCol, endRow, endCol);
        updateBoard();
        switchToNextPlayer();
    }

    private class PieceClickListener extends MouseAdapter {
        private int row;
        private int col;

        public PieceClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (selectedRow == -1 && board[row][col] == checkersGame.getCurrentPlayer()) {
                selectPiece(row, col);
            } else if (selectedRow != -1) {
                movePiece(row, col);
                deselectPiece();
            }
        }
    }

    private void selectPiece(int row, int col) {
        selectedRow = row;
        selectedCol = col;
        selectedLabel = labels[row][col];
        selectedLabel.setBackground(Color.YELLOW);
    }

    private void deselectPiece() {
        if (selectedLabel != null) {
            selectedLabel.setBackground((selectedRow + selectedCol) % 2 == 0 ? Color.GRAY : Color.WHITE);
        }
        selectedRow = -1;
        selectedCol = -1;
        selectedLabel = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CheckersGameGUI::new);
    }
}
