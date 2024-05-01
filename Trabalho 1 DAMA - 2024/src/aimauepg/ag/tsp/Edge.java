/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aimauepg.ag.tsp;

/**
 * 
 * @author Roman Boegli
 *
 */
public class Edge {
	
	
	double nX1;
	
	
	
	
	/**
	 * Constructor I
	 * @param a first point in the coordinate system
	 * 		   represented as a vertex
	 * @param b second point in the coordinate system
	 * 		   represented as a vertex
	 */
	public Edge (Vertex a, Vertex b) {
		
		this.nX1 = a.getX();
		
		
	}
	
	
	
	
	
	
	
	
}