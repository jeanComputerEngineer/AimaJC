package exercicio2;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.framework.problem.StepCostFunction;
import java.util.Objects;

public class JFunctions {
    
    public int aux = 0;
    public static StepCostFunction<EHanoi, JAction> getJCostFunction() {
        return new JStepCostFunctionImpl();        
    }
    
    public static List<JAction> getActions(EHanoi state) {
        List<JAction> actions = new ArrayList<>();
        //primeiro testa se as pilhas estão vazias
        //método de fugir do out of bounds nos arrays
        
        //disco 1
        if(state.getP1(state.getIndexTopo(state.p1))==1){
            actions.add(new JAction(JAction.D1P1P2));
            actions.add(new JAction(JAction.D1P1P3));
        }else if(state.getP2(state.getIndexTopo(state.p2))==1){
            actions.add(new JAction(JAction.D1P2P1));
            actions.add(new JAction(JAction.D1P2P3));
        }else if(state.getP3(state.getIndexTopo(state.p3))==1){
            actions.add(new JAction(JAction.D1P3P1));
            actions.add(new JAction(JAction.D1P3P2));
        }
        //disco 2
        else if(state.getP1(state.getIndexTopo(state.p1))==2 && state.getP2(state.getIndexTopo(state.p2)) != 1){
            actions.add(new JAction(JAction.D2P1P2));
        }else if(state.getP1(state.getIndexTopo(state.p1))==2 && state.getP3(state.getIndexTopo(state.p3)) != 1){
            actions.add(new JAction(JAction.D2P1P3));
        }else if(state.getP2(state.getIndexTopo(state.p2))==2 && state.getP1(state.getIndexTopo(state.p1)) != 1){
            actions.add(new JAction(JAction.D2P2P1));
        }else if(state.getP2(state.getIndexTopo(state.p2))==2 && state.getP3(state.getIndexTopo(state.p3)) != 1){
            actions.add(new JAction(JAction.D2P2P3));
        }else if(state.getP3(state.getIndexTopo(state.p3))==2 && state.getP1(state.getIndexTopo(state.p1)) != 1){
            actions.add(new JAction(JAction.D2P3P1));
        }else if(state.getP3(state.getIndexTopo(state.p3))==2 && state.getP2(state.getIndexTopo(state.p2)) != 1){
            actions.add(new JAction(JAction.D2P3P2));
        }
        //disco 3
        else if(state.getP1(state.getIndexTopo(state.p1))==3 && (state.getP2(state.getIndexTopo(state.p2)) == -1 || state.getP2(state.getIndexTopo(state.p2))>3)){
            actions.add(new JAction(JAction.D3P1P2));
        }else if(state.getP1(state.getIndexTopo(state.p1))==3 && (state.getP3(state.getIndexTopo(state.p3)) == -1 || state.getP3(state.getIndexTopo(state.p3))>3)){
            actions.add(new JAction(JAction.D3P1P3));
        }else if(state.getP2(state.getIndexTopo(state.p2))==3 && (state.getP1(state.getIndexTopo(state.p1)) == -1 || state.getP1(state.getIndexTopo(state.p1))>3)){
            actions.add(new JAction(JAction.D3P2P1));
        }else if(state.getP2(state.getIndexTopo(state.p2))==3 && (state.getP3(state.getIndexTopo(state.p3)) == -1 || state.getP3(state.getIndexTopo(state.p3))>3)){
            actions.add(new JAction(JAction.D3P2P3));
        }else if(state.getP3(state.getIndexTopo(state.p3))==3 && (state.getP1(state.getIndexTopo(state.p1)) == -1 || state.getP1(state.getIndexTopo(state.p1))>3)){
            actions.add(new JAction(JAction.D3P3P1));
        }else if(state.getP3(state.getIndexTopo(state.p3))==3 && (state.getP2(state.getIndexTopo(state.p2)) == -1 || state.getP2(state.getIndexTopo(state.p2))>3)){
            actions.add(new JAction(JAction.D3P3P2));
        }
        //disco 4
        else if(state.getP1(state.getIndexTopo(state.p1))==4 && (state.getP2(state.getIndexTopo(state.p2)) == -1 || state.getP2(state.getIndexTopo(state.p2))>4)){
            actions.add(new JAction(JAction.D4P1P2));
        }else if(state.getP1(state.getIndexTopo(state.p1))==4 && (state.getP3(state.getIndexTopo(state.p3)) == -1 || state.getP3(state.getIndexTopo(state.p3))>4)){
            actions.add(new JAction(JAction.D4P1P3));
        }else if(state.getP2(state.getIndexTopo(state.p2))==4 && (state.getP1(state.getIndexTopo(state.p1)) == -1 || state.getP1(state.getIndexTopo(state.p1))>4)){
            actions.add(new JAction(JAction.D4P2P1));
        }else if(state.getP2(state.getIndexTopo(state.p2))==4 && (state.getP3(state.getIndexTopo(state.p3)) == -1 || state.getP3(state.getIndexTopo(state.p3))>4)){
            actions.add(new JAction(JAction.D4P2P3));
        }else if(state.getP3(state.getIndexTopo(state.p3))==4 && (state.getP1(state.getIndexTopo(state.p1)) == -1 || state.getP1(state.getIndexTopo(state.p1))>4)){
            actions.add(new JAction(JAction.D4P3P1));
        }else if(state.getP3(state.getIndexTopo(state.p3))==4 && (state.getP2(state.getIndexTopo(state.p2)) == -1 || state.getP2(state.getIndexTopo(state.p2))>4)){
            actions.add(new JAction(JAction.D4P3P2));
        }
        //disco 5
        else if(state.getP1(state.getIndexTopo(state.p1))==5 && (state.getP2(state.getIndexTopo(state.p2)) == -1 || state.getP2(state.getIndexTopo(state.p2))>5)){
            actions.add(new JAction(JAction.D5P1P2));
        }else if(state.getP1(state.getIndexTopo(state.p1))==5 && (state.getP3(state.getIndexTopo(state.p3)) == -1 || state.getP3(state.getIndexTopo(state.p3))>5)){
            actions.add(new JAction(JAction.D5P1P3));
        }else if(state.getP2(state.getIndexTopo(state.p2))==5 && (state.getP1(state.getIndexTopo(state.p1)) == -1 || state.getP1(state.getIndexTopo(state.p1))>5)){
            actions.add(new JAction(JAction.D5P2P1));
        }else if(state.getP2(state.getIndexTopo(state.p2))==5 && (state.getP3(state.getIndexTopo(state.p3)) == -1 || state.getP3(state.getIndexTopo(state.p3))>5)){
            actions.add(new JAction(JAction.D5P2P3));
        }else if(state.getP3(state.getIndexTopo(state.p3))==5 && (state.getP1(state.getIndexTopo(state.p1)) == -1 || state.getP1(state.getIndexTopo(state.p1))>5)){
            actions.add(new JAction(JAction.D5P3P1));
        }else if(state.getP3(state.getIndexTopo(state.p3))==5 && (state.getP2(state.getIndexTopo(state.p2)) == -1 || state.getP2(state.getIndexTopo(state.p2))>5)){
            actions.add(new JAction(JAction.D5P3P2));
        }
        //disco 6
        else if(state.getP1(state.getIndexTopo(state.p1))==6 && (state.getP2(state.getIndexTopo(state.p2)) == -1 || state.getP2(state.getIndexTopo(state.p2))>6)){
            actions.add(new JAction(JAction.D6P1P2));
        }else if(state.getP1(state.getIndexTopo(state.p1))==6 && (state.getP3(state.getIndexTopo(state.p3)) == -1 || state.getP3(state.getIndexTopo(state.p3))>6)){
            actions.add(new JAction(JAction.D6P1P3));
        }else if(state.getP2(state.getIndexTopo(state.p2))==6 && (state.getP1(state.getIndexTopo(state.p1)) == -1 || state.getP1(state.getIndexTopo(state.p1))>6)){
            actions.add(new JAction(JAction.D6P2P1));
        }else if(state.getP2(state.getIndexTopo(state.p2))==6 && (state.getP3(state.getIndexTopo(state.p3)) == -1 || state.getP3(state.getIndexTopo(state.p3))>6)){
            actions.add(new JAction(JAction.D6P2P3));
        }else if(state.getP3(state.getIndexTopo(state.p3))==6 && (state.getP1(state.getIndexTopo(state.p1)) == -1 || state.getP1(state.getIndexTopo(state.p1))>6)){
            actions.add(new JAction(JAction.D6P3P1));
        }else if(state.getP3(state.getIndexTopo(state.p3))==6 && (state.getP2(state.getIndexTopo(state.p2)) == -1 || state.getP2(state.getIndexTopo(state.p2))>6)){
            actions.add(new JAction(JAction.D6P3P2));
        }
        //disco 7
        else if(state.getP1(state.getIndexTopo(state.p1))==7 && state.getP2(state.getIndexTopo(state.p2)) == -1){
            actions.add(new JAction(JAction.D7P1P2));
        }else if(state.getP1(state.getIndexTopo(state.p1))==7 && state.getP3(state.getIndexTopo(state.p3)) == -1){
            actions.add(new JAction(JAction.D7P1P3));
        }else if(state.getP2(state.getIndexTopo(state.p2))==7 && state.getP1(state.getIndexTopo(state.p1)) == -1){
            actions.add(new JAction(JAction.D7P2P1));
        }else if(state.getP2(state.getIndexTopo(state.p2))==7 && state.getP3(state.getIndexTopo(state.p3)) == -1){
            actions.add(new JAction(JAction.D7P2P3));
        }else if(state.getP3(state.getIndexTopo(state.p3))==7 && state.getP1(state.getIndexTopo(state.p1)) == -1){
            actions.add(new JAction(JAction.D7P3P1));
        }else if(state.getP3(state.getIndexTopo(state.p3))==7 && state.getP2(state.getIndexTopo(state.p2)) == -1){
            actions.add(new JAction(JAction.D7P3P2));
        }
        
        return actions;
    }
    
    public static EHanoi getResult(EHanoi e, JAction ac) {
        
        //System.out.println("Action Name:" + ac.getName());
        
        if (Objects.equals(ac.getName(), JAction.D1P1P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(child.p1)] = 0;
            child.p2[e.getIndexTopo(child.p2)+1] = 1;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D1P1P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 1;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D1P2P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 1;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D1P2P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 1;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D1P3P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 1;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D1P3P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 1;
            return child;
        }else if (Objects.equals(ac.getName(), JAction.D2P1P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 2;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D2P1P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 2;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D2P2P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 2;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D2P2P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 2;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D2P3P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 2;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D2P3P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 2;
            return child;
        }else if (Objects.equals(ac.getName(), JAction.D3P1P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 3;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D3P1P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 3;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D3P2P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 3;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D3P2P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 3;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D3P3P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 3;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D3P3P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 3;
            return child;
        }else if (Objects.equals(ac.getName(), JAction.D4P1P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 4;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D4P1P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 4;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D4P2P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 4;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D4P2P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 4;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D4P3P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 4;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D4P3P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 4;
            return child;
        }else if (Objects.equals(ac.getName(), JAction.D5P1P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 5;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D5P1P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 5;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D5P2P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 5;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D5P2P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 5;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D5P3P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 5;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D5P3P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 5;
            return child;
        }else if (Objects.equals(ac.getName(), JAction.D6P1P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 6;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D6P1P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 6;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D6P2P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 6;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D6P2P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 6;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D6P3P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 6;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D6P3P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 6;
            return child;
        }else if (Objects.equals(ac.getName(), JAction.D7P1P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 7;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D7P1P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p1[e.getIndexTopo(e.p1)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 7;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D7P2P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 7;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D7P2P3)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p2[e.getIndexTopo(e.p2)] = 0;
            child.p3[e.getIndexTopo(e.p3)+1] = 7;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D7P3P1)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p1[e.getIndexTopo(e.p1)+1] = 7;
            return child;
        }else if(Objects.equals(ac.getName(), JAction.D7P3P2)) {
            EHanoi child = new EHanoi(e.discos);
            child.setP1(e.p1);
            child.setP2(e.p2);
            child.setP3(e.p3);
            child.p3[e.getIndexTopo(e.p3)] = 0;
            child.p2[e.getIndexTopo(e.p2)+1] = 7;
            return child;
        }
        return null;
    }

    public static boolean testGoal(EHanoi e) {
        GTHanoi gt = new GTHanoi(e.discos);
        return gt.test(e);
    } 
    
    
    //Provavelmente não precisa implementar
    
    private static class JStepCostFunctionImpl implements StepCostFunction<EHanoi, JAction> {
        
        private static double constantCost = 1.0;

        private JStepCostFunctionImpl() {

        }

        @Override
        public double applyAsDouble(EHanoi state, JAction action, EHanoi statePrimed) {
            double jcost = 0;
            /*
            if (Objects.equals(action.getName(), JAction.COMPLETAR_J3)) jcost = 3 - statePrimed.getVol3L();
            if (Objects.equals(action.getName(), JAction.COMPLETAR_J4)) jcost = 4 - statePrimed.getVol4L();
            if (Objects.equals(action.getName(), JAction.COMPLETAR_J7)) jcost = 7 - statePrimed.getVol7L();
            */
            return jcost;
        }
    }
    
}