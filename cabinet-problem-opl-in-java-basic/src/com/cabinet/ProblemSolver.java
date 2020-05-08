package com.cabinet;



import java.util.ArrayList;
import java.util.List;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
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
			
			List<IloRange> constraints = new ArrayList<IloRange>();
			constraints.add(cplex.addLe(cplex.sum(cplex.prod(10, x), cplex.prod(20, y)), 140));
			constraints.add(cplex.addLe(cplex.sum(cplex.prod(6, x), cplex.prod(8, y)), 72));
			constraints.add(cplex.addGe(cplex.prod(1, x), 0));
			constraints.add(cplex.addGe(cplex.prod(1, y), 0));
			
			// cplex.solve();
			
			if (cplex.solve()) {
				System.out.println("Solution found");
				System.out.println("Objective Value -> Max Space = " + cplex.getObjValue());
				System.out.println("X Value = " + cplex.getValue(x));
				System.out.println("Y Value = " + cplex.getValue(y));
				// Get dual Values
				for (int i = 0; i < constraints.size(); i++) {
					System.out.println("dual constraint -> " + (i + 1) + " = " + cplex.getDual(constraints.get(i)));
					System.out.println("Slack constraint -> " + (i + 1) + " = " + cplex.getSlack(constraints.get(i)));
				}
			} else {
				System.out.println("Solution Not Found");
			}
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
}
