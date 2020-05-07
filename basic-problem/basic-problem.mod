/*********************************************
 * OPL 12.9.0.0 Model
 * Author: Sidharthan Kamaraj
 * Creation Date: May 7, 2020 at 8:30:20 PM
 *********************************************/
 
 // Variable Declaration
 dvar float+ x;
 dvar float+ y;
 
 // Optimal Solution
 dexpr float solution = 3000 * x + 5000 * y;
 maximize solution;
 
 // Constraints
 subject to {
 	constraint1:
 	x <= 4;
 	
 	constraint2:
 	2 * y <= 12;
 	
 	constraint3:
 	3 * x + 2 * y <= 18;
 	
 	constraint4:
 	x >= 0;
 	
 	constraint5:
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
 
