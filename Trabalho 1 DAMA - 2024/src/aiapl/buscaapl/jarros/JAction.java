/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiapl.buscaapl.jarros;

import aima.core.agent.impl.DynamicAction;

/**
 *
 * @author zcr
 */
public class JAction extends DynamicAction {
    
    public static final String COMPLETAR_J5 = "encher(j5)";
    public static final String COMPLETAR_J3 = "encher(j3)";
    public static final String DEPESJAR_J5_J3 = "despejar(j5,j3)";
    public static final String ESVAZIAR_J5 = "esvaziar(j5)";
    public static final String DEPESJAR_J3_J5 = "depesjar(j3,j5)";
    public static final String ESVAZIAR_J3 = "esvaziar(j3)";
    public static final String COMPLETAR_J5_COM_J3 = "completar(j5,j3)";
    public static final String COMPLETAR_J3_COM_J5 = "completar(j3,j5)";
    
    public JAction(String type) {
        super(type);
    }
    
}
