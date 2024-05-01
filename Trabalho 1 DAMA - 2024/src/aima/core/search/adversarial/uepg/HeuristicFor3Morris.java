/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aima.core.search.adversarial.uepg;

import aima.core.search.adversarial.Game;

/**
 *
 * @author zcr
 */
public class HeuristicFor3Morris implements ExternalHeuristic {
    
        public double compute(Game game, Object state, Object player)  {
            java.util.Random r = new java.util.Random();
            return r.nextDouble();
        }    
}