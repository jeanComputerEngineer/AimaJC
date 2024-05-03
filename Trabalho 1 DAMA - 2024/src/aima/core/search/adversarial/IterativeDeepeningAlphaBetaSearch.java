package aima.core.search.adversarial;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.framework.Metrics;

/**
 * Implements an iterative deepening Minimax search with alpha-beta pruning and
 * action ordering. Maximal computation time is specified in seconds. The
 * algorithm is implemented as template method and can be configured and tuned
 * by subclassing.
 *
 * @param <S> Type which is used for states in the game.
 * @param <A> Type which is used for actions in the game.
 * @param <P> Type which is used for players in the game.
 * @author Ruediger Lunde
 */
public class IterativeDeepeningAlphaBetaSearch<S, A, P> implements AdversarialSearch<S, A> {

    public final static String METRICS_NODES_EXPANDED = "nodesExpanded";
    public final static String METRICS_MAX_DEPTH = "maxDepth";

    protected Game<S, A, P> game;
    protected double utilMax;
    protected double utilMin;
    protected int currDepthLimit;
    // nodes
    // have been evaluated.
    private Timer timer;
    private boolean logEnabled;

    private Metrics metrics = new Metrics();

    /**
     * Creates a new search object for a given game.
     *
     * @param game    The game.
     * @param utilMin Utility value of worst state for this player. Supports
     *                evaluation of non-terminal states and early termination in
     *                situations with a safe winner.
     * @param utilMax Utility value of best state for this player. Supports
     *                evaluation of non-terminal states and early termination in
     *                situations with a safe winner.
     * @param time    Maximal computation time in seconds.
     */
    public static <STATE, ACTION, PLAYER> IterativeDeepeningAlphaBetaSearch<STATE, ACTION, PLAYER> createFor(
            Game<STATE, ACTION, PLAYER> game, double utilMin, double utilMax, int time) {
        return new IterativeDeepeningAlphaBetaSearch<>(game, utilMin, utilMax, time);
    }

    /**
     * Creates a new search object for a given game.
     *
     * @param game    The game.
     * @param utilMin Utility value of worst state for this player. Supports
     *                evaluation of non-terminal states and early termination in
     *                situations with a safe winner.
     * @param utilMax Utility value of best state for this player. Supports
     *                evaluation of non-terminal states and early termination in
     *                situations with a safe winner.
     * @param time    Maximal computation time in seconds.
     */
    public IterativeDeepeningAlphaBetaSearch(Game<S, A, P> game, double utilMin, double utilMax,
            int time) {
        this.game = game;
        this.utilMin = utilMin;
        this.utilMax = utilMax;
        this.timer = new Timer(time);
    }

    public void setLogEnabled(boolean b) {
        logEnabled = b;
    }

    /**
     * Template method controlling the search. It is based on iterative
     * deepening and tries to make to a good decision in limited time. Credit
     * goes to Behi Monsio who had the idea of ordering actions by utility in
     * subsequent depth-limited search runs.
     */
    @Override
    public A makeDecision(S state) {
        metrics = new Metrics();
        StringBuffer logText = logEnabled ? new StringBuffer() : null;
        P player = game.getPlayer(state);
        List<A> results = orderActions(state, game.getActions(state), player, 0);
        timer.start();
        currDepthLimit = 0;

        double bestValue = Double.NEGATIVE_INFINITY;
        A bestAction = null;

        while (!timer.timeOutOccurred()) {
            incrementDepthLimit();
            if (logEnabled)
                logText.append("Depth " + currDepthLimit + ": ");

            ActionStore<A> newResults = new ActionStore<>();
            for (A action : results) {
                double value = minValue(game.getResult(state, action), player, Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY, 1);
                if (timer.timeOutOccurred())
                    break; // Exit if time out occurred
                newResults.add(action, value);
                if (value > bestValue) {
                    bestValue = value;
                    bestAction = action;
                }
                if (logEnabled)
                    logText.append(action).append(" -> ").append(value).append(", ");
            }
            if (logEnabled)
                System.out.println(logText);

            if (!newResults.isEmpty()) {
                results = newResults.actions; // Focus on potentially better actions
                if (!timer.timeOutOccurred() && newResults.isSignificantlyBetterThanNext()) {
                    break; // Stop if the best action is significantly better than the next
                }
            }
            if (hasSafeWinner(bestValue))
                break; // Early exit if a safe winning move is found
        }

        return bestAction != null ? bestAction : results.get(0);
    }

    // returns an utility value
    public double maxValue(S state, P player, double alpha, double beta, int depth) {
        updateMetrics(depth);
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred()) {
            return eval(state, player);
        } else {
            double value = Double.NEGATIVE_INFINITY;
            for (A action : orderActions(state, game.getActions(state), player, depth)) {
                value = Math.max(value, minValue(game.getResult(state, action), //
                        player, alpha, beta, depth + 1));
                if (value >= beta)
                    return value;
                alpha = Math.max(alpha, value);
            }
            return value;
        }
    }

    // returns an utility value
    public double minValue(S state, P player, double alpha, double beta, int depth) {
        updateMetrics(depth);
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred()) {
            return eval(state, player);
        } else {
            double value = Double.POSITIVE_INFINITY;
            for (A action : orderActions(state, game.getActions(state), player, depth)) {
                value = Math.min(value, maxValue(game.getResult(state, action), //
                        player, alpha, beta, depth + 1));
                if (value <= alpha)
                    return value;
                beta = Math.min(beta, value);
            }
            return value;
        }
    }

    private void updateMetrics(int depth) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        metrics.set(METRICS_MAX_DEPTH, Math.max(metrics.getInt(METRICS_MAX_DEPTH), depth));
    }

    /**
     * Returns some statistic data from the last search.
     */
    @Override
    public Metrics getMetrics() {
        return metrics;
    }

    /**
     * Primitive operation which is called at the beginning of one depth limited
     * search step. This implementation increments the current depth limit by
     * one.
     */
    protected void incrementDepthLimit() {
        currDepthLimit++;
    }

    /**
     * Primitive operation which is used to stop iterative deepening search in
     * situations where a clear best action exists. This implementation returns
     * always false.
     */
    protected boolean isSignificantlyBetter(double newUtility, double utility) {
        return false;
    }

    /**
     * Primitive operation which is used to stop iterative deepening search in
     * situations where a safe winner has been identified. This implementation
     * returns true if the given value (for the currently preferred action
     * result) is the highest or lowest utility value possible.
     */
    protected boolean hasSafeWinner(double resultUtility) {
        return resultUtility <= utilMin || resultUtility >= utilMax;
    }

    private static final int SIZE = 8;

    /**
     * Primitive operation, which estimates the value for (not necessarily
     * terminal) states. This implementation returns the utility value for
     * terminal states and <code>(utilMin + utilMax) / 2</code> for non-terminal
     * states. When overriding, first call the super implementation!
     */
    protected double eval(S state, P player) {
        if (game.isTerminal(state)) {
            return game.getUtility(state, player);
        } else {
            char[][] board = (char[][]) state;
            double score = 0.0;

            // Counts and values
            double myKings = 0;
            double opponentKings = 0;
            double myPieces = 0;
            double opponentPieces = 0;
            double kingValue = 5.0; // Increased value for kings

            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    char piece = board[row][col];
                    switch (piece) {
                        case 'X':
                            myPieces++;
                            if (row == 0)
                                myKings += kingValue; // Piece is a king
                            break;
                        case 'K':
                            myKings += kingValue;
                            break;
                        case 'O':
                            opponentPieces++;
                            if (row == SIZE - 1)
                                opponentKings += kingValue; // Opponent piece is a king
                            break;
                        case 'Q':
                            opponentKings += kingValue;
                            break;
                    }
                }
            }

            // Calculate basic score
            score += (myKings + myPieces) - (opponentKings + opponentPieces);

            // Prioritize kinging and blocking
            double myKingingPotential = SIZE - 1 - getClosestRowToKing(board, 'X');
            double opponentKingingPotential = SIZE - 1 - getClosestRowToKing(board, 'O');
            score += 2.0 * myKingingPotential - 2.0 * opponentKingingPotential;

            return score;
        }
    }

    private int getClosestRowToKing(char[][] board, char playerPiece) {
        int closestRow = SIZE; // Start with the maximum size, indicating farthest
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if ((playerPiece == 'X' && board[row][col] == 'X') || (playerPiece == 'O' && board[row][col] == 'O')) {
                    if (playerPiece == 'X') {
                        // Smaller row index means closer to becoming a king
                        closestRow = Math.min(closestRow, row);
                    } else {
                        // Larger row index means closer to becoming a king
                        closestRow = Math.min(closestRow, SIZE - 1 - row);
                    }
                }
            }
        }
        return closestRow;
    }

    /**
     * Primitive operation for action ordering. This implementation preserves
     * the original order (provided by the game).
     */
    public List<A> orderActions(S state, List<A> actions, P player, int depth) {
        return actions;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // nested helper classes

    private static class Timer {
        private long duration;
        private long startTime;

        Timer(int maxSeconds) {
            this.duration = 1000 * maxSeconds;
        }

        void start() {
            startTime = System.currentTimeMillis();
        }

        boolean timeOutOccurred() {
            return System.currentTimeMillis() > startTime + duration;
        }
    }

    /**
     * Orders actions by utility.
     */
    private static class ActionStore<A> {
        private List<A> actions = new ArrayList<>();
        private List<Double> utilValues = new ArrayList<>();

        void add(A action, double utilValue) {
            int idx = 0;
            while (idx < actions.size() && utilValue <= utilValues.get(idx))
                idx++;
            actions.add(idx, action);
            utilValues.add(idx, utilValue);
        }

        boolean isEmpty() {
            return actions.isEmpty();
        }

        boolean isSignificantlyBetterThanNext() {
            if (utilValues.size() < 2) {
                return false; // There is no "next" value to compare
            }
            double bestValue = utilValues.get(0);
            double secondBestValue = utilValues.get(1);
            double difference = bestValue - secondBestValue;
            return difference >= 2.0; // Adjust this threshold as needed
        }
    }
}