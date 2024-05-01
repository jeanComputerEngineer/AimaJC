/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aimauepg;

/**
 *
 * @author zcr
 */
import aima.core.environment.tictactoe.TicTacToeGame;
import aima.core.environment.tictactoe.TicTacToeState;
import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.search.adversarial.DLAlphaBetaSearch;
import aima.core.search.adversarial.uepg.HeuristicFor3Morris;
import aima.core.util.datastructure.XYLocation;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MinimaxTicTacToe {
    // https://www.javatips.net/api/aima.core.environment.tictactoe.tictactoegame
    

    public void Minimaxtest() {
        System.out.println("MINI MAX DEMO\n");
        TicTacToeGame game = new TicTacToeGame();
        TicTacToeState currState = game.getInitialState();
        AdversarialSearch<TicTacToeState, XYLocation> search = MinimaxSearch.createFor(game);
        while (!(game.isTerminal(currState))) {
            System.out.println(game.getPlayer(currState) + "  pensando ... ");
            XYLocation action = search.makeDecision(currState);
            currState = game.getResult(currState, action);
            System.out.println();
            System.out.println("Lance do agente de software");
            System.out.println(currState.toString());
            if (!game.isTerminal(currState)) {
                currState = userPlay(game,currState);
                System.out.println();
                System.out.println("Lance do usuario");
                System.out.println(currState.toString());
            }            
        }
        System.out.println("MINI MAX DEMO done");
    }
    
    public void MinimaxABDLtest() {
        System.out.println("MINI MAX DEMO\n");
        TicTacToeGame game = new TicTacToeGame();
        TicTacToeState currState = game.getInitialState();
        AdversarialSearch<TicTacToeState, XYLocation> search = DLAlphaBetaSearch.createFor(game);
        ( (DLAlphaBetaSearch) search).setHeuristics(new HeuristicFor3Morris());
        ( (DLAlphaBetaSearch) search).setDepth(5);
        while (!(game.isTerminal(currState))) {
            System.out.println(game.getPlayer(currState) + "  pensando ... ");
            XYLocation action = search.makeDecision(currState);
            currState = game.getResult(currState, action);
            System.out.println();
            System.out.println("Lance do agente de software");
            System.out.println(currState.toString());
            if (!game.isTerminal(currState)) {
                currState = userPlay(game,currState);
                System.out.println();
                System.out.println("Lance do usuario");
                System.out.println(currState.toString());
            }            
        }
        System.out.println("MINI MAX DEMO done");
    }
    
    private TicTacToeState userPlay(TicTacToeGame game, TicTacToeState currState) {

        System.out.println("Selecione uma posicao X");
        int x, y = 0;
        x = this.readNum2();
        System.out.println("Selecione uma posicao Y");
        y = this.readNum2();
        XYLocation uaction = new XYLocation(x,y);
        currState = game.getResult(currState,uaction);
        return currState;
    }
    
/*    private int readNumber() {
        Scanner in = new Scanner(System.in);
        int i = in.nextInt();
        in.close();
        return i;
    }*/
    


    public int readNum2() {
        int i ;
        String s;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
          s = br.readLine();
        } catch(java.io.IOException e) {
            return 1;
        }
        try{
            i = Integer.parseInt(s);
        }catch(NumberFormatException nfe){
            return 1;
        }
        return i;
    }
    
    
}
    
    
    

