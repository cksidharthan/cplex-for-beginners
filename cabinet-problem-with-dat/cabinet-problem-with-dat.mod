/*********************************************
 * OPL 12.9.0.0 Model
 * Author: Sidharthan Kamaraj
 * Creation Date: May 7, 2020 at 9:20:47 PM
 *********************************************/

// Variable Declaration
int num_cabinet = ...;
int num_condition = ...;

range cabinets = 1..num_cabinet;
range conditions = 1..num_condition;

float holding[cabinets] = ...;
float budget[conditions] = ...;
float value[conditions][cabinets] = ...;

// Decision Variable
dvar float+ x[cabinets];

// Optimal Solution
dexpr float max_space = sum(cab in cabinets) holding[cab] * x[cab];
maximize max_space;

// Constraints
subject to {

	forall(con in conditions)
	  main_constraints: sum(cab in cabinets) value[con][cab] * x [cab] <= budget[con];
	  
	forall(cab in cabinets)
	  other_constraints: x[cab] >= 0;
}

// Post Processing

execute {
	if (cplex.getCplexStatus() == 1) {
		writeln("Solution found:");
		writeln("Number of Cabinets X and Y: " + x)
		writeln("Max Space: " + max_space);
	} else {
		writeln("Solution not found");	
	}
}

 

