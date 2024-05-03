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
    private char currentPlayer;
    private char aiPlaysAs;
    private JLabel statusLabel;

    public CheckersGameGUI() {
        checkersGame = new CheckersGame();
        currentPlayer = 'X'; // Define o jogador inicial
        aiPlaysAs = 'O'; // Define qual peça a IA joga
        setTitle("Checkers Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.8);
        int height = (int) (screenSize.getHeight() * 0.8);
        setSize(width, height);

        board = checkersGame.getInitialState();
        createBoardPanel();

        statusLabel = new JLabel("", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        add(boardPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
        updateBoard();
    }

    private void createBoardPanel() {
        boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        labels = new JLabel[SIZE][SIZE];

        int cellSize = (int) (Math.min(getWidth(), getHeight()) * 0.9 / SIZE);
        Font pieceFont = new Font("Arial", Font.PLAIN, cellSize);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBackground((row + col) % 2 == 0 ? Color.GRAY : Color.WHITE);
                label.addMouseListener(new PieceClickListener(row, col));
                label.setPreferredSize(new Dimension(cellSize, cellSize));
                label.setFont(pieceFont);
                labels[row][col] = label;
                boardPanel.add(label);
            }
        }
    }

    private void movePiece(int endRow, int endCol) {
        if (selectedRow != -1 && selectedCol != -1) {
            if (checkersGame.isValidMove(selectedRow, selectedCol, endRow, endCol, board)) {
                checkersGame.makeMove(selectedRow, selectedCol, endRow, endCol);
            } else if (checkersGame.isValidJump(selectedRow, selectedCol, endRow, endCol, board, false)) {
                checkersGame.makeMove(selectedRow, selectedCol, endRow, endCol);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid move!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
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
                if (currentBoard[row][col] == 'X') {
                    labels[row][col].setForeground(Color.BLUE); // X em azul
                } else if (currentBoard[row][col] == 'O') {
                    labels[row][col].setForeground(Color.RED); // O em vermelho
                }
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

    public void switchToNextPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        if ((currentPlayer == 'X' && aiPlaysAs == 'X') || (currentPlayer == 'O' && aiPlaysAs == 'O')) {
            statusLabel.setText("IA Está Pensando, Aguarde Sua Vez!");
            SwingUtilities.invokeLater(this::makeAIMove);
        } else {
            statusLabel.setText("");
        }
    }

    private void makeAIMove() {
        try {
            // Exibe mensagem de que a IA está pensando em algum canto da tela
            statusLabel.setText("AI is thinking...");
            String aiAction = checkersGame.iterativeDeepeningAlphaBetaSearch.makeDecision(board);
            if (aiAction != null && !aiAction.isEmpty()) {
                applyMove(aiAction);
            } else {
                JOptionPane.showMessageDialog(this, "AI did not find a valid move.", "No Move",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred during AI processing: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            statusLabel.setText(""); // Remove a mensagem após a IA jogar
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
