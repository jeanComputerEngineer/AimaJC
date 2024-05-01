package exercicio2;

import aima.core.search.agent.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.GeneralProblem;
import aima.core.search.framework.problem.Problem;

import aima.core.search.uninformed.UniformCostSearch;

import java.util.List;
import java.util.Scanner;
import java.util.Optional;

public class Principal {
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Entre com o número de discos (entre 3 e 7):");
        int n = scan.nextInt();
        Principal hanoi = new Principal();
        hanoi.run(n);
    }
    
    public void run(int n){
       this.uniformCostSearch(n);
    }
    
    private void uniformCostSearch(int n) {
        EHanoi inicial = new EHanoi(n);
        Problem<EHanoi, JAction> problem;
        problem = new GeneralProblem<>(
                        inicial,
                        JFunctions::getActions,
                        JFunctions::getResult,
                        JFunctions::testGoal);
        SearchForActions<EHanoi, JAction> search = new UniformCostSearch<>();
        try {
            SearchAgent<EHanoi, JAction> agent = new SearchAgent<>(problem, search);
            Optional<List<JAction>> actions = search.findActions(problem);
            this.printActions(actions);
        } catch (Exception e) {
            System.out.println(e);
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
