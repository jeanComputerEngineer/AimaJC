package aima.core.search.adversarial;

import aima.core.search.adversarial.uepg.ExternalHeuristic;
import aima.core.search.framework.Metrics;

/**
 * Artificial Intelligence A Modern Approach (3rd Ed.): Page 173.<br>
 * <p>
 * <pre>
 * <code>
 * function ALPHA-BETA-SEARCH(state) returns an action
 *   v = MAX-VALUE(state, -infinity, +infinity)
 *   return the action in ACTIONS(state) with value v
 *
 * function MAX-VALUE(state, alpha, beta) returns a utility value
 *   if TERMINAL-TEST(state) then return UTILITY(state)
 *   v = -infinity
 *   for each a in ACTIONS(state) do
 *     v = MAX(v, MIN-VALUE(RESULT(s, a), alpha, beta))
 *     if v >= beta then return v
 *     alpha = MAX(alpha, v)
 *   return v
 *
 * function MIN-VALUE(state, alpha, beta) returns a utility value
 *   if TERMINAL-TEST(state) then return UTILITY(state)
 *   v = infinity
 *   for each a in ACTIONS(state) do
 *     v = MIN(v, MAX-VALUE(RESULT(s,a), alpha, beta))
 *     if v <= alpha then return v
 *     beta = MIN(beta, v)
 *   return v
 * </code>
 * </pre>
 * <p>
 * Figure 5.7 The alpha-beta search algorithm. Notice that these routines are
 * the same as the MINIMAX functions in Figure 5.3, except for the two lines in
 * each of MIN-VALUE and MAX-VALUE that maintain alpha and beta (and the
 * bookkeeping to pass these parameters along).
 *
 * @param <S> Type which is used for states in the game.
 * @param <A> Type which is used for actions in the game.
 * @param <P> Type which is used for players in the game.
 * @author Ruediger Lunde
 */
public class DLAlphaBetaSearch<S, A, P> extends aima.core.search.adversarial.AlphaBetaSearch<S, A, P> {

    //public final static String METRICS_NODES_EXPANDED = "nodesExpanded";

    //Game<S, A, P> game;
    //private Metrics metrics = new Metrics();  // that is the point
    
    private int depth = 7;
    private int cDepth = 0;
    private ExternalHeuristic eHeuristic;

    /**
     * Creates a new search object for a given game.
     */
    public static <STATE, ACTION, PLAYER> DLAlphaBetaSearch<STATE, ACTION, PLAYER> createFor(
            Game<STATE, ACTION, PLAYER> game) {
        return new DLAlphaBetaSearch<STATE, ACTION, PLAYER>(game);
    }

    public DLAlphaBetaSearch(Game<S, A, P> game) {
        super(game);
    }


    public double maxValue(S state, P player, double alpha, double beta) {
        super.getMetrics().incrementInt(METRICS_NODES_EXPANDED);
        if (game.isTerminal(state))
            return game.getUtility(state, player);
        if (this.cDepth==this.depth)
            return computetHeuristic(state,player);
        double value = Double.NEGATIVE_INFINITY;
        this.cDepth++;
        for (A action : game.getActions(state)) {
            value = Math.max(value, minValue( //
                    game.getResult(state, action), player, alpha, beta));
            if (value >= beta) { 
                this.cDepth--;
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        this.cDepth--;
        return value;
    }

    public double minValue(S state, P player, double alpha, double beta) {
        super.getMetrics().incrementInt(METRICS_NODES_EXPANDED);
        if (game.isTerminal(state))
            return game.getUtility(state, player);
        if (this.cDepth==this.depth)
            return computetHeuristic(state,player);
        double value = Double.POSITIVE_INFINITY;
        this.cDepth++;
        for (A action : game.getActions(state)) {
            value = Math.min(value, maxValue( //
                    game.getResult(state, action), player, alpha, beta));
            if (value <= alpha) {
                this.cDepth--;
                return value;
            }
            beta = Math.min(beta, value);
        }
        this.cDepth--;
        return value;
    }

    // jcarlos
    
    public void setDepth(int d) {
        this.depth = d;
    }
    
    
    
    public void setHeuristics(ExternalHeuristic eh) {
        this.eHeuristic = eh;
    }
    
    
    public double computetHeuristic(S state, P player) {
        return this.eHeuristic.compute(game, state, player);
    }
    
}
