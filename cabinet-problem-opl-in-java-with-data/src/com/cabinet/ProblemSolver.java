package com.cabinet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.opl.IloCplex;

public class ProblemSolver {
	static int number_of_cabinets = 2;
	static int number_of_conditions = 2;
	static List<Integer> objectiveValues = Arrays.asList(8, 12);
	static int[][] conditionValues = new int[][]{{10, 20},{6, 8}};
	static int[] budgetValues = new int[] {140, 72};
	
	public static void main(String[] args) {
		
		model();
	}

	public static void model() {
		try {
			IloCplex cplex = new IloCplex();

			List<IloNumVar> decision_variables = new ArrayList<IloNumVar>();
			for (int i = 0; i < number_of_cabinets; i++) {
				decision_variables.add(cplex.numVar(0, Double.MAX_VALUE, "x" + i));
			}
			
			// Objective Function
			IloLinearNumExpr objective = cplex.linearNumExpr();
			cplex.addMaximize(objective);
			for (int i = 0; i < objectiveValues.size(); i++) {
				objective.addTerm(objectiveValues.get(i), decision_variables.get(i));
			}
			
			List<IloRange> constraints = new ArrayList<IloRange>();
			for (int i = 0; i < number_of_conditions; i++) {
				List<IloNumExpr> constraintValueList = new ArrayList<IloNumExpr>();
				for (int j = 0; j < number_of_cabinets; j++) {
					constraintValueList.add(cplex.prod(conditionValues[i][j], decision_variables.get(j)));
				}
				IloNumExpr constraint = cplex.addLe(cplex.sum(constraintValueList.get(0), constraintValueList.get(1)), budgetValues[i]);
				constraints.add((IloRange) constraint);
			}
			cplex.solve();
			System.out.println(cplex.getObjValue());
			for (int i = 0; i < number_of_cabinets; i++) {
				System.out.println("Decision Variable " + decision_variables.get(i) + cplex.getValue(decision_variables.get(i)));
			}
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
} 
