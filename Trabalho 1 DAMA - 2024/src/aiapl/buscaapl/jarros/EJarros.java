/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aiapl.buscaapl.jarros;

/**
 *
 * @author jcarlos
 */
public class EJarros {

    int J5, J3 = 0;  //volume armazenado
    
    public EJarros() {
        this.J3 =0;
        this.J5 = 0;
    }
    
    public void setVol(int a, int b) {
        this.J5=a;
        this.J3=b;
    }
    
    public int getVol5L() {
        return this.J5;
    }
    
    public int getVol3L() {
        return this.J3;
    }

}
