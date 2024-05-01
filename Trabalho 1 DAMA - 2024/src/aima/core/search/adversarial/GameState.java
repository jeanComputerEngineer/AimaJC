package aima.core.search.adversarial;

public class GameState {
    // Assume these are counters for two types of game pieces
    private int numPiecesPlayer; // Number of pieces for Player
    private int numPiecesOpponent; // Number of pieces for Opponent

    public GameState(int numPiecesPlayer, int numPiecesOpponent) {
        this.numPiecesPlayer = numPiecesPlayer;
        this.numPiecesOpponent = numPiecesOpponent;
    }

    // Method to get the number of pieces for Player
    public int numpecaP() {
        return numPiecesPlayer;
    }

    // Method to get the number of pieces for Opponent
    public int numpecaO() {
        return numPiecesOpponent;
    }

    // You might have other methods and properties relevant to your game state
}
