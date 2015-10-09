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
		//runEntireTestSuite();
		testQASingleRun();
	}
	
	public static void testQASingleRun(){
		int maxPopulationSize = 10;
		double mutationChance = 0.001;
		int amountOfFittestPopulationToKeep = 4;
		int iterationCount = 50;
		RouteSearch routeSearch = new RouteSearch(maxPopulationSize, mutationChance, amountOfFittestPopulationToKeep);
		long startTime = System.currentTimeMillis();
		for(int k = 0; k < iterationCount; k++){
			routeSearch.iterateOneStep();
		}
		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println(
		"P: "+maxPopulationSize+
		"\tM: "+mutationChance+"\tI:"+iterationCount +
		"\tB: "+routeSearch.getCurrentBestGenotype()+
		"("+routeSearch.getCurrentBestGenotypeScore()+")"+
		"\tT:" + totalTime);
	}
	
	public static void runEntireTestSuite(){
		long suiteStart = System.currentTimeMillis();
		for(int maxPopulationSize : MAXIMUM_POPULATION_COUNTS){
			for(double mutationChance : MUTATION_CHANCES){
				for(int fitnessKeepPercent : FITNESS_KEEPING_AMOUNT){
					int amountOfFittestPopulationToKeep = maxPopulationSize * fitnessKeepPercent / 100;
					for(int iterationCount : ITERATION_AMOUNTS){
						for(int j = 0; j < REPEAT_THIS_GA_FOR_STATISTICS; j++){
							RouteSearch routeSearch = new RouteSearch(maxPopulationSize, mutationChance, amountOfFittestPopulationToKeep);
							long startTime = System.currentTimeMillis();
							for(int k = 0; k < iterationCount; k++){
								routeSearch.iterateOneStep();
							}
							long totalTime = System.currentTimeMillis() - startTime;
							System.out.println(
							"P: "+maxPopulationSize+
							"\tM: "+mutationChance+"\tI:"+iterationCount +
							"\tB: "+routeSearch.getCurrentBestGenotype()+
							"("+routeSearch.getCurrentBestGenotypeScore()+")"+
							"\tT:" + totalTime);
						}						
					}
				}
			}
		}
		System.out.println("Total Time Elapsed: " + (System.currentTimeMillis()-suiteStart) + "ms");
	}
}
