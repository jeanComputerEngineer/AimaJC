/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aiapl.buscaapl.jarros;

/**
 *
 * @author jcarlos
 */


import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.environment.map.MoveToAction;
import java.util.List;

import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFunctions;
import aima.core.environment.nqueens.QueenAction;
import aima.core.search.agent.SearchAgent;
import aima.core.search.framework.Node;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.GeneralProblem;
import aima.core.search.framework.problem.Problem;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.framework.problem.StepCostFunction;
import aima.core.search.uninformed.UniformCostSearch;
//import aima.search.framework.TreeSearch;

import java.util.Optional;


public class RunDemoPotes {
    
    public void run() {
//        astarSearch();
        //depthFirstSearch();
        //this.depthFirstLimitedSearch();
        this.uniformCostSearch();
//        breadthFirstSearch();
    }


  

    private void depthFirstLimitedSearch() {
        EJarros inicial = new EJarros();
        Problem<EJarros, JAction> problem;
        problem =   new GeneralProblem<>(inicial,
                                                JFunctions::getActions,
                                                JFunctions::getResult,
                                                JFunctions::testGoal);
	SearchForActions<EJarros, JAction> search = new DepthLimitedSearch<>(8);
	Optional<List<JAction>> actions = search.findActions(problem);
        this.printActions(actions);
    }

    private void uniformCostSearch() {
        EJarros inicial = new EJarros();
        Problem<EJarros, JAction> problem;
        problem = new GeneralProblem<>(
                        inicial,
                        JFunctions::getActions,
                        JFunctions::getResult,
                        JFunctions::testGoal,
                        JFunctions.getJCostFunction());
        SearchForActions<EJarros, JAction> search = new UniformCostSearch<>();
        try {
        SearchAgent<EJarros, JAction> agent = new SearchAgent<>(problem, search);
        Optional<List<JAction>> actions = search.findActions(problem);
        this.printActions(actions);
        } catch (Exception e) {
            System.out.println("excessão");
            System.exit(0);
        }

    }
  
    private void printActions(Optional<List<JAction>> actions) {
	List<JAction> acList ;
        Object aux = actions.get();
        acList = ((List<JAction>) aux);
        for (int i = 0; i < acList.size(); i++) {
            String act = (String) acList.get(i).getName();
            System.out.println(act);
	}
    }


}
