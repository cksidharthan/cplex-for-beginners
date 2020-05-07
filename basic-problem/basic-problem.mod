/*********************************************
 * OPL 12.9.0.0 Model
 * Author: Sidharthan Kamaraj
 * Creation Date: May 7, 2020 at 8:30:20 PM
 *********************************************/
 
 // Variable Declaration
 dvar float+ x;
 dvar float+ y;
 
 // Optimal Solution
 dexpr float max_holding = 8 * x + 12 * y;
 maximize max_holding;
 
 // Constraints
 subject to {
 	cost_constraint:
 	10 * x + 20 * y <= 140;
 	
 	space_constraint:
 	6 * x + 8 * y <= 72;
 	
 	x_constraint:
 	x >= 0;
 	
 	y_constraint:
 	y >= 0; 
 }
 
 // Post Processing
 execute {
 	if (cplex.getCplexStatus() == 1) {
 		writeln("Solution Found - x = " + x + " y = " + y ) 
 		writeln("x = " + x)
 		writeln("y = " + y)
 		writeln("solution = " + solution)	
 	} else {
 		writeln("Solution not found") 	
 	}
 }
 
