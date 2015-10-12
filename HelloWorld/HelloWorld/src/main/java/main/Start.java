import java.io.BufferedReader;
import java.io.FileReader;

public class Start {

/***************************************************
This program is a General Algortihm for solving a 
simple solution to finding a good circuit for a 
bike tour.  We will base the best result as one that
visits every city and takes the shortest distance.  

**************************************************/
	private static final int REPEAT_THIS_GA_FOR_STATISTICS = 50;
	
	private static final int[] MAXIMUM_POPULATION_COUNTS = {10, 50, 100, 500, 1000, 5000, 10000};
	private static final double[] MUTATION_CHANCES = {0.001, 0.005, 0.01, 0.05, 0.1, 0.25};
	private static final int[] FITNESS_KEEPING_AMOUNT = {20, 40, 60};
	private static final int[] ITERATION_AMOUNTS = {10, 50, 100, 500, 1000};
	
	private static int[][] distancesMatrix;
	
	/** Repetitions.
		We need to repeat across may iterations with different
			int max population size
			double mutation chance
			int how many of the fittest solutions to keep each time, as percentage of pop
		MaxPop: 10, 50, 100, 500, 1000, 5000, 10000
		MutCh: 0.001, 0.005, 0.01, 0.05, 0.1, 0.25
		Fitkeep: 20% of maxpop, 40% of maxpop, 60% of maxpop		
		For each of max populations
			For each of mutation chance
				For each of keep fitness
					For each number of iterations
						For statistics repeat count
							Start GA
							Start Timer
							For each iteration
								Iterate GA
							Stop Timer
							Keep a record of the best solution from population and its score
		There are 31500 GAs created, with 10,458,000 total iterations
		Should be enough for statistical analysis, right?
		However, even if each run takes 1 second, the whole suite will take 2905 hours to run.
	**/

	/**TODO.
	**		What to print after each iteration is completed
	**		Find a way to pass in the distances matrix
	**			[probably only used in fitness calculation]
	**/
	public static void main(String[] s) {
		//distancesMatrix = readDistancesMatrix("route.txt");
		distancesMatrix = readDistancesMatrix("route.alt.txt");
		//runEntireTestSuite();
		testQASingleRun();
	}
	
	/**TODO.
		Read the distance matrix in and convert it into a 2-d array
		e.g. [0][1] = position of 1st item on row 0 of text file = 179
		To read: Select row as FROM, Select column as TO
		[0][1] is the distance from city 0 to city 1
		         ABD AYR   EDI   FTW   GLS   INV   STA   STR
			ABD  0   179   129   157   146   105   79    119
			AYR  179 0     79    141   33    207   118   64
			EDI  129 79    0     131   43    154   50    36      
			FTW  157 141   131   0     116   74*   134   98
			GLS  146 33    43    116   0     175   81    27
			INV  105 207   154   64*   175   0     145   143
			STA  79  118   50    134   81    145   0     143
			STR  119 64    36    96    27    143   52    0   
	**/
	public static int[][] readDistancesMatrix(String location){
		return new int[][] {{0}};
	}
	
	public static void testQASingleRun(){
		int maxPopulationSize = 10;
		double mutationChance = 0.001;
		int amountOfFittestPopulationToKeep = 4;
		int iterationCount = 50;
		runTheGAWithParams(maxPopulationSize, mutationChance, amountOfFittestPopulationToKeep, iterationCount);
	}
	
	public static void runEntireTestSuite(){
		long suiteStart = System.currentTimeMillis();
		for(int maxPopulationSize : MAXIMUM_POPULATION_COUNTS){
			for(double mutationChance : MUTATION_CHANCES){
				for(int fitnessKeepPercent : FITNESS_KEEPING_AMOUNT){
					int amountOfFittestPopulationToKeep = maxPopulationSize * fitnessKeepPercent / 100;
					for(int iterationCount : ITERATION_AMOUNTS){
						for(int j = 0; j < REPEAT_THIS_GA_FOR_STATISTICS; j++){
							runTheGAWithParams(maxPopulationSize, mutationChance, amountOfFittestPopulationToKeep, iterationCount);
						}						
					}
				}
			}
		}
		System.out.println("Total Time Elapsed: " + (System.currentTimeMillis()-suiteStart) + "ms");
	}

	public static void runTheGAWithParams(int maxPopulationSize, double mutationChance, int amountOfFittestPopulationToKeep, int iterationCount){
		RouteSearch routeSearch = new RouteSearch(maxPopulationSize, mutationChance, amountOfFittestPopulationToKeep, distancesMatrix);
		long startTime = System.currentTimeMillis();
		for(int k = 0; k < iterationCount; k++){
			routeSearch.iterateOneStep();
		}
		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println(
		"P: "+maxPopulationSize+
		"\tM: "+mutationChance+"\tI:"+iterationCount +
		"\tR: "+amountOfFittestPopulationToKeep+
		"\tB: "+routeSearch.getCurrentBestGenotype()+
		"("+routeSearch.getCurrentBestGenotypeScore()+")"+
		"\tT:" + totalTime);
	}
}
