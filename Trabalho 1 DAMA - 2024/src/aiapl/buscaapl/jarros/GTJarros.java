/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aiapl.buscaapl.jarros;

/**
 *
 * @author jcarlos
 */

import aima.core.search.framework.problem.GoalTest;

public class GTJarros implements GoalTest {
    
    int sf5L, sf3L = 0; //volume objetivo
    
    public GTJarros(int a, int b) {
        sf5L = a;
        sf3L = b;
    }
    
    public boolean test(Object state) {
        EJarros potes = (EJarros) state;
        if (potes.getVol5L()==sf5L)
            if (potes.getVol3L()==sf3L)
                return true;
        return false;
    }


    public int[] getObjetive() {
        return new int[] {sf5L,sf3L};
    }

}
