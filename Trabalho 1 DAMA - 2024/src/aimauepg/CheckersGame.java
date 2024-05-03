package aimauepg;

import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;

import aima.core.search.adversarial.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CheckersGame implements Game<char[][], String, Character> {

    private static final int SIZE = 8;
    private char[][] board;
    private char currentPlayer;
    IterativeDeepeningAlphaBetaSearch<char[][], String, Character> iterativeDeepeningAlphaBetaSearch;
    private Scanner scanner;
    private char aiPlaysAs; // 'X' or 'O', set this when initializing the game
    private int movesWithoutCapture;

    private static final int MAX_MOVES_WITHOUT_CAPTURE = 5; // Número máximo de jogadas sem eliminação antes do empate

    private boolean isKing(char piece) {
        return piece == 'K' || piece == 'Q';
    }

    public CheckersGame() {
        board = new char[SIZE][SIZE];
        scanner = new Scanner(System.in);
        initializeBoard();
        selectPlayerPiece();
        this.iterativeDeepeningAlphaBetaSearch = IterativeDeepeningAlphaBetaSearch.createFor(this,
                Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 5); // Specify the time limit (in seconds) for the
                                                                        // search
        this.movesWithoutCapture = 0; // Inicializa o contador de jogadas sem eliminação

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

    public void startGame() {
        play();
    }

    public void printBoard() {
        System.out.println("    0 1 2 3 4 5 6 7");
        for (int row = 0; row < SIZE; row++) {
            System.out.print(row + "   ");
            for (int col = 0; col < SIZE; col++) {
                System.out.print("|" + board[row][col]);
            }
            System.out.println("|");
        }
    }

    boolean isValidMove(int startRow, int startCol, int endRow, int endCol, char[][] state) {
        char piece = state[startRow][startCol];
        boolean isKing = isKing(piece);
        int dir = (piece == 'X' || isKing) ? -1 : 1;  // 'X' moves up, 'O' moves down unless it's a king
    
        // Check if the move is within the board limits and the destination cell is empty
        if (endRow >= 0 && endRow < SIZE && endCol >= 0 && endCol < SIZE && state[endRow][endCol] == ' ') {
            // Check if the move is forward for normal pieces or any direction for kings
            if (isKing) {
                return Math.abs(endRow - startRow) == 1 && Math.abs(endCol - startCol) == 1;
            } else {
                return endRow - startRow == dir && Math.abs(endCol - startCol) == 1;
            }
        }
        return false;
    }

    private boolean isValidJump(int startRow, int startCol, int endRow, int endCol, char[][] state, boolean isKing) {
        int jumpRow = (startRow + endRow) / 2;
        int jumpCol = (startCol + endCol) / 2;
        int dir = (state[startRow][startCol] == 'X' || isKing) ? -1 : 1;
    
        if (endRow >= 0 && endRow < SIZE && endCol >= 0 && endCol < SIZE && state[endRow][endCol] == ' ' && state[jumpRow][jumpCol] != ' ' && state[jumpRow][jumpCol] != state[startRow][startCol]) {
            if (isKing) {
                return Math.abs(endRow - startRow) == 2 && Math.abs(endCol - startCol) == 2;
            } else {
                return endRow - startRow == 2 * dir && Math.abs(endCol - startCol) == 2;
            }
        }
        return false;
    }

    private String getMoveAction(int startRow, int startCol, int endRow, int endCol) {
        return startRow + "," + startCol + ":" + endRow + "," + endCol;
    }

    private String getJumpAction(int startRow, int startCol, int endRow, int endCol) {
        return startRow + "," + startCol + ":" + endRow + "," + endCol;
    }

    public void makeMove(int startRow, int startCol, int endRow, int endCol) {
        char piece = board[startRow][startCol];
        board[startRow][startCol] = ' '; // Remove a peça da posição inicial
        board[endRow][endCol] = piece; // Coloque a peça na posição final

        // Se a peça for uma dama e chegar à última linha do oponente, coroe a peça
        if ((piece == 'O' && endRow == SIZE - 1) || (piece == 'X' && endRow == 0)) {
            board[endRow][endCol] = Character.toUpperCase(piece);
            System.out.println("Player " + currentPlayer + " made a king!");
            // Você pode adicionar um método para verificar se um jogador ganhou após coroar
            // uma peça
            if (hasPlayerWon(currentPlayer)) {
                System.out.println("Player " + currentPlayer + " wins!");
                return;
            }
        }

        // Se o movimento for um salto, remova a peça capturada
        if (Math.abs(startRow - endRow) == 2) {
            int jumpRow = (startRow + endRow) / 2;
            int jumpCol = (startCol + endCol) / 2;
            board[jumpRow][jumpCol] = ' ';
            movesWithoutCapture = 0;
        } else {
            movesWithoutCapture++;
        }

        // Troque para o próximo jogador
        switchToNextPlayer();
    }

    public boolean isGameOver() {
        int blackCount = 0;
        int whiteCount = 0;

        // Count the number of black and white pieces on the board
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 'O' || board[row][col] == 'Q') {
                    blackCount++;
                } else if (board[row][col] == 'X' || board[row][col] == 'K') {
                    whiteCount++;
                }
            }
        }

        // The game is over if one of the players has no more pieces
        return blackCount == 0 || whiteCount == 0;
    }

    public void selectPlayerPiece() {
        System.out.println("Choose the piece you want to play with ('X' or 'O'): ");
        String input = scanner.nextLine();
        if (input.length() == 1 && (input.charAt(0) == 'X' || input.charAt(0) == 'O')) {
            currentPlayer = input.toUpperCase().charAt(0);
            aiPlaysAs = (currentPlayer == 'X') ? 'O' : 'X'; // AI plays as the opposite
        } else {
            System.out.println("Invalid option. Choose 'X' or 'O'.");
            selectPlayerPiece();
        }
    }

    @Override
    public char[][] getInitialState() {
        return board;
    }

    @Override
    public Character getPlayer(char[][] state) {
        return currentPlayer;
    }

    @Override
    public List<Character> getPlayers() {
        return Arrays.asList('X', 'O');
    }

    @Override
    public List<String> getActions(char[][] state) {
        List<String> actions = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                char piece = state[row][col];
                if ((currentPlayer == 'X' && (piece == 'X' || piece == 'K')) ||
                        (currentPlayer == 'O' && (piece == 'O' || piece == 'Q'))) {
                    // Generate valid moves for the current piece and add them to the list
                    generateValidMoves(state, actions, row, col);
                }
            }
        }
        return actions;
    }

    private void generateValidMoves(char[][] state, List<String> actions, int row, int col) {
        char piece = state[row][col];
        int dir = (piece == 'X' || piece == 'K') ? -1 : 1; // Direction of movement (forward/backward)
        boolean isKing = (piece == 'K' || piece == 'Q');

        // Check for regular moves
        if (isValidMove(row, col, row + dir, col - 1, state)) {
            actions.add(getMoveAction(row, col, row + dir, col - 1));
        }
        if (isValidMove(row, col, row + dir, col + 1, state)) {
            actions.add(getMoveAction(row, col, row + dir, col + 1));
        }

        // Check for jumps (captures)
        if (isValidJump(row, col, row + 2 * dir, col - 2, state, isKing)) {
            actions.add(getJumpAction(row, col, row + 2 * dir, col - 2));
        }
        if (isValidJump(row, col, row + 2 * dir, col + 2, state, isKing)) {
            actions.add(getJumpAction(row, col, row + 2 * dir, col + 2));
        }
    }

    @Override
    public char[][] getResult(char[][] state, String action) {
        String[] parts = action.split(":");
        String[] start = parts[0].split(",");
        String[] end = parts[1].split(",");
        int startRow = Integer.parseInt(start[0]);
        int startCol = Integer.parseInt(start[1]);
        int endRow = Integer.parseInt(end[0]);
        int endCol = Integer.parseInt(end[1]);

        char[][] newState = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            newState[i] = state[i].clone();
        }

        char piece = newState[startRow][startCol];
        newState[startRow][startCol] = ' '; // Remove the piece from the start position
        newState[endRow][endCol] = piece; // Place the piece at the end position

        // If the piece is a king and reaches the opponent's last row, crown the piece
        if ((piece == 'O' && endRow == SIZE - 1) || (piece == 'X' && endRow == 0)) {
            newState[endRow][endCol] = Character.toUpperCase(piece);
        }

        // If the move is a jump, remove the captured piece
        if (Math.abs(startRow - endRow) == 2) {
            int jumpRow = (startRow + endRow) / 2;
            int jumpCol = (startCol + endCol) / 2;
            newState[jumpRow][jumpCol] = ' ';
        }

        return newState;
    }

    @Override
    public boolean isTerminal(char[][] state) {
        // Check if the game is in a terminal state (e.g., one player wins or no more
        // moves)
        // You'll need to implement this method based on your game rules
        return isGameOver() || movesWithoutCapture >= MAX_MOVES_WITHOUT_CAPTURE;
    }

    @Override
    public double getUtility(char[][] state, Character player) {
        int blackCount = 0;
        int whiteCount = 0;

        // Count the number of black and white pieces on the board
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (state[row][col] == 'O' || state[row][col] == 'Q') {
                    blackCount++;
                } else if (state[row][col] == 'X' || state[row][col] == 'K') {
                    whiteCount++;
                }
            }
        }

        if (player == 'X') {
            // Evaluate the utility for the white player ('X')
            return whiteCount - blackCount;
        } else {
            // Evaluate the utility for the black player ('O')
            return blackCount - whiteCount;
        }
    }

    private void applyAction(char[][] state, String action) {
        if (action != null && !action.isEmpty()) {
            String[] parts = action.split(":");
            int startRow = Integer.parseInt(parts[0].split(",")[0]);
            int startCol = Integer.parseInt(parts[0].split(",")[1]);
            int endRow = Integer.parseInt(parts[1].split(",")[0]);
            int endCol = Integer.parseInt(parts[1].split(",")[1]);

            // Execute the move on the board
            char piece = state[startRow][startCol];
            state[startRow][startCol] = ' ';
            state[endRow][endCol] = piece;

            // Check for a jump and remove the jumped piece
            if (Math.abs(startRow - endRow) == 2) {
                int middleRow = (startRow + endRow) / 2;
                int middleCol = (startCol + endCol) / 2;
                state[middleRow][middleCol] = ' ';
            }

            // Update the main board to reflect changes
            board = state;
        } else {
            System.out.println("No valid move was found or given.");
        }
    }

   

    private String getUserMove() {
        System.out.println("Enter your move (format: startRow,startCol:endRow,endCol): ");
        String moveInput = scanner.nextLine();
        if (!moveInput.matches("\\d,\\d:\\d,\\d")) {
            System.out
                    .println("Invalid input format. Please use the correct format 'startRow,startCol:endRow,endCol'.");
            return getUserMove();
        }
        return moveInput;
    }

    public void play() {
        boolean gameOver = false;
        while (!gameOver) {
            printBoard();
            System.out.println("Current player: " + currentPlayer);
            char[][] currentState = getCopyOfBoard();
            List<String> possibleActions = getActions(currentState);
    
            if (possibleActions.isEmpty()) {
                System.out.println("No possible actions for player " + currentPlayer + ". Game over!");
                gameOver = true;
                continue;
            }
    
            // Determine action based on current player and who controls that player (human or AI)
            if ((currentPlayer == 'O' && aiPlaysAs == 'O') || (currentPlayer == 'X' && aiPlaysAs == 'X')) {
                // AI's turn
                String aiAction = iterativeDeepeningAlphaBetaSearch.makeDecision(currentState);
                System.out.println("AI (" + currentPlayer + ") moves: " + aiAction);
                applyAction(currentState, aiAction);
            } else {
                // Human's turn
                String userAction = getUserMove();
                applyAction(currentState, userAction);
            }
    
            if (!isGameOver()) {
                switchToNextPlayer();
            }
            gameOver = isGameOver();
        }
        printBoard();
        System.out.println("Game over!");
    }

    private boolean hasPlayerWon(char player) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                char piece = board[row][col];
                if ((player == 'X' && (piece == 'O' || piece == 'Q')) ||
                        (player == 'O' && (piece == 'X' || piece == 'K'))) {
                    return false; // Ainda há peças do oponente no tabuleiro
                }
            }
        }
        return true; // O jogador não tem mais peças do oponente no tabuleiro, então ele ganhou
    }

    private char[][] getCopyOfBoard() {
        char[][] newState = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, newState[i], 0, SIZE);
        }
        return newState;
    }
    public void switchToNextPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }
    
    public char getCurrentPlayer() {
        return currentPlayer;
    }
  

}
