package com.cabinet;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.opl.IloCplex;

public class ProblemSolver {
	public static void main(String[] args) {
		model();
	}
	
	public static void model() {
		try {
			IloCplex cplex = new IloCplex();
			
			// variables
			IloNumVar x = cplex.numVar(0, Double.MAX_VALUE, "x");
			IloNumVar y = cplex.numVar(0, Double.MAX_VALUE, "y");
			
			IloLinearNumExpr objective = cplex.linearNumExpr();
			objective.addTerm(8, x);
			objective.addTerm(12, y);
			
			cplex.addMaximize(objective);
			
			cplex.addLe(cplex.sum(cplex.prod(10, x), cplex.prod(20, y)), 140);
			cplex.addLe(cplex.sum(cplex.prod(6, x), cplex.prod(8, y)), 72);
			cplex.addGe(cplex.prod(1, x), 0);
			cplex.addGe(cplex.prod(1, y), 0);
			
			cplex.solve();
			
			if (cplex.solve()) {
				System.out.println("Solution found");
				System.out.println("Objective Value -> Max Space = " + cplex.getObjValue());
				System.out.println("X Value = " + cplex.getValue(x));
				System.out.println("Y Value = " + cplex.getValue(y));
			} else {
				System.out.println("Solution Not Found");
			}
			
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
}
