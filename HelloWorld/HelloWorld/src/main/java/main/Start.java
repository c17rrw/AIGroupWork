import java.io.BufferedReader;
import java.io.FileReader;

public class Start {

/***************************************************
This program is a General Algortihm for solving a 
simple solution to finding a good circuit for a 
bike tour.  We will base the best result as one that
visits every city and takes the shortest distance.  

**************************************************/

	private static final int MAX_ITERATIONS = 50;

	/**TODO.
	**		What to print after each iteration is completed
	**		Find a way to pass in the distances matrix
	**			[probably only used in fitness calculation]
	**		For part 4 is it worth allowing automation for 
	**			setting GA params from within here?
	**/
	public static void main(String[] s) {
		System.out.println("Hello, World!");
		RouteSearch routeSearch = new RouteSearch();
		for(int i = 0; i < MAX_ITERATIONS; i++){
			routeSearch.iterateOneStep();
			System.out.println("Iteration "+i);
		}
	}
}
