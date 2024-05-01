/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aima2019zc;

/**
 *
 * @author zcr
 */




public class Aima2019zc {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        // TODO code application logic here
     /*   int j=0;
        for( int i=0;i<10000000;i++) {
            j = (i % 2) + (i %3) + j;
            if (i % 50 == 0) System.out.print(j + ";");
            if (i % 500 == 0) {
                System.out.println("\n");
                System.out.println(i);
            }
        }
        System.out.println("fim");
//        (new Aima2019zc()).runTicTacToe();*/
     aiapl.buscaapl.jarros.RunDemoPotes rdm = new aiapl.buscaapl.jarros.RunDemoPotes();
     rdm.run();;
        
    }
    
    
    private void runTicTacToe() {
        MinimaxTicTacToe tictactoe = new MinimaxTicTacToe();
        tictactoe.Minimaxtest();
        
    }
    
}
