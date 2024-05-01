package exercicio2;

import aima.core.search.framework.problem.GoalTest;
import java.util.Arrays;

public class GTHanoi implements GoalTest {
    
    int gtp1[] = new int[7]; 
    int gtp2[] = new int[7]; 
    int gtp3[] = new int[7]; 
    
    public GTHanoi(int k) {
        for(int i=0;i<k;i++){
            this.gtp1[i] = 0;
            this.gtp2[i] = 0;
            this.gtp3[i] = k-i;
        }
    }
    
    public boolean test(Object state) {
        EHanoi hanoi = (EHanoi) state;
        if(Arrays.equals(this.gtp1, hanoi.getP1())){
            if(Arrays.equals(this.gtp2, hanoi.getP2())){
                if(Arrays.equals(this.gtp3, hanoi.getP3())){
                    return true;
                }
            }
        }
        return false;
    }
}
