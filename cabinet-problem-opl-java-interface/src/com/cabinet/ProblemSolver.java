package com.cabinet;

import ilog.concert.IloException;
import ilog.opl.IloCplex;
import ilog.opl.IloOplDataSource;
import ilog.opl.IloOplErrorHandler;
import ilog.opl.IloOplFactory;
import ilog.opl.IloOplModel;
import ilog.opl.IloOplModelDefinition;
import ilog.opl.IloOplModelSource;
import ilog.opl.IloOplSettings;

public class ProblemSolver {
	public static void main(String[] args) {
		int status = 127;
		try {
            IloOplFactory oplFactory = new IloOplFactory();                             
            IloOplErrorHandler errHandler = oplFactory.createOplErrorHandler();     
            IloOplSettings settings = oplFactory.createOplSettings(errHandler); 
            IloOplModelSource modelSource = oplFactory.createOplModelSource("cabinet-problem-with-dat.mod");
            IloOplModelDefinition modelDefinition = oplFactory.createOplModelDefinition(modelSource,settings);
            IloCplex cplex = oplFactory.createCplex();                                  
            cplex.setOut(null);                                                   
            IloOplModel oplModel = oplFactory.createOplModel(modelDefinition, cplex);                    
            IloOplDataSource dataSource = oplFactory.createOplDataSource("cabinet-problem-with-dat.dat");                                            
            oplModel.addDataSource(dataSource);                                        
            oplModel.generate();
            
            if (cplex.solve()) {                                                                     
                System.out.println("OBJECTIVE: " + oplModel.getCplex().getObjValue());
                System.out.println("CPLEX Status: " + oplModel.getCplex().getCplexStatus());
                System.out.println("CPLEX Version: " + oplModel.getCplex().getVersion());
                oplModel.postProcess();                                                
                oplModel.printSolution(System.out);                                    
            } else {                                                                     
                System.out.println("No solution!");                               
            } 
           
            oplFactory.end();                                                           
            status = 0;                                                   
            System.exit(status);
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
}
