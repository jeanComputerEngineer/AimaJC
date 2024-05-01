/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aiapl.buscaapl.jarros;

/**
 *
 * @author jcarlos
 */


import java.util.ArrayList;
import java.util.List;

import aima.core.search.framework.Node;
import aima.core.search.framework.problem.GeneralProblem;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.problem.StepCostFunction;
import java.util.Objects;

public class JFunctions {

    public static StepCostFunction<EJarros, JAction> getJCostFunction() {
        return new JStepCostFunctionImpl();        
    }
    
    public static List<JAction> getActions(EJarros state) {
        List<JAction> actions = new ArrayList<>();
        // encher pote de 5L
        
        if ((state.getVol3L()<0) || (state.getVol5L()<0)) {
            System.out.println("Erro consistência.");
            System.exit(0);
        }
            
        
        if (state.getVol5L()<5) 
            actions.add(new JAction(JAction.COMPLETAR_J5));
        
        // encher pote de 3L
        if (state.getVol3L()<3) 
            actions.add(new JAction(JAction.COMPLETAR_J3));


        //esvaziar pote de 5L no pote 3L
        if (state.getVol3L()>0)
            actions.add(new JAction(JAction.ESVAZIAR_J3));


        // esvaziar pote de 5L
        if (state.getVol5L()>0)
            actions.add(new JAction(JAction.ESVAZIAR_J5));
        
        // despejar 5L em 3L
        if ((state.getVol5L()>0) & (state.getVol3L()<3))
            if (state.getVol5L() <= (3-state.getVol3L()))
                actions.add(new JAction(JAction.DEPESJAR_J5_J3));
        
        // despejar 3L em 5L
        if ((state.getVol3L()>0) & (state.getVol5L()<5))
            if (state.getVol3L() <= (5-state.getVol5L()))
                actions.add(new JAction(JAction.DEPESJAR_J3_J5));

        // completar 3L com 5L
        if ((state.getVol5L()>0) & (state.getVol5L()<5) & (state.getVol3L()>0))
                actions.add(new JAction(JAction.DEPESJAR_J3_J5));
        
        return actions;
    }
    
    public static EJarros getResult(EJarros e, JAction ac) {
        System.out.println("Action Name:" + ac.getName());
        if (Objects.equals(ac.getName(), JAction.COMPLETAR_J5)) {
            EJarros child = new EJarros();
            child.setVol(5, e.getVol3L());
        return child;
        } else  if (Objects.equals(ac.getName(), JAction.COMPLETAR_J3)) {
            EJarros child = new EJarros();
            child.setVol(e.getVol5L(), 3);
        return child;
        } else if (Objects.equals(ac.getName(), JAction.ESVAZIAR_J3)) {
            EJarros child = new EJarros();
            child.setVol(e.getVol5L(), 0);
            return child;
        } else if (Objects.equals(ac.getName(), JAction.ESVAZIAR_J5)) {
            EJarros child = new EJarros();
            child.setVol(0, e.getVol3L());
            return child;
        } else if (Objects.equals(ac.getName(), JAction.DEPESJAR_J3_J5)) {
            EJarros child = new EJarros();
            int vj5 = e.getVol5L()+e.getVol3L();
            child.setVol(vj5, 0);
            return child;
        }  else if (Objects.equals(ac.getName(), JAction.COMPLETAR_J3_COM_J5)) {
            EJarros child = new EJarros();
            int folgaJ3 = 3 - e.getVol3L(); 
            int transf = 0;
            if (folgaJ3>=e.getVol5L())
                transf = e.getVol5L();
            else //v3 = 1 // v5 = 2
                transf = folgaJ3;
            int vj5 = e.getVol5L()-transf;
            child.setVol(vj5, 3);
            return child;
        }
        return null;
    }

  
    
    public static boolean testGoal(EJarros e) {
        GTJarros gt = new GTJarros(3,0);
        return gt.test(e);
    }
    
    
    
    private static class JStepCostFunctionImpl implements StepCostFunction<EJarros, JAction> {
//        private EJarros estado = null;

        // Used by Uniform-cost search to ensure every step is greater than or equal
        // to some small positive constant
        private static double constantCost = 1.0;

        private JStepCostFunctionImpl() {

        }

        @Override
        public double applyAsDouble(EJarros state, JAction action, EJarros statePrimed) {
            double jcost = 1;
            if (Objects.equals(action.getName(), JAction.COMPLETAR_J3)) jcost = 4;
            if (Objects.equals(action.getName(), JAction.COMPLETAR_J5)) jcost = 6;            
            return jcost;
        }
    }
}
