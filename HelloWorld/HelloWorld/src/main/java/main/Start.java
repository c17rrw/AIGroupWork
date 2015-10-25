import java.io.BufferedReader;
import java.io.FileReader;

public class Start {

/***************************************************
This program is a General Algortihm for solving a 
simple solution to finding a good circuit for a 
bike tour.  We will base the best result as one that
visits every city and takes the shortest distance.  

**************************************************/
	private static final String DISTANCES_MATRIX_LOCATION = "route.alt.txt";
	private static final int REPEAT_THIS_GA_FOR_STATISTICS = 50;
	private static final int[] MAXIMUM_POPULATION_COUNTS = {10, 50, 100, 500, 1000, 5000, 10000};
	private static final double[] MUTATION_CHANCES = {0.001, 0.005, 0.01, 0.05, 0.1, 0.25};
	private static final double[] FITNESS_KEEPING_AMOUNT = {0.2, 0.4, 0.6};
	private static final int[] ITERATION_AMOUNTS = {10, 50, 100, 500, 1000};
	
	private static int[][] distancesMatrix;

	private static int defaultPopulationCount;
	private static double defaultMutationChance;
	private static double defaultFitnessKeepingAmount;
	private static int defaultIterationAmount;
	
	public static void main(String[] s) {
		defaultPopulationCount = MAXIMUM_POPULATION_COUNTS[1];
		defaultMutationChance = MUTATION_CHANCES[1];
		defaultFitnessKeepingAmount = FITNESS_KEEPING_AMOUNT[1];
		defaultIterationAmount = ITERATION_AMOUNTS[1];
		distancesMatrix = readDistancesMatrixFromFile(DISTANCES_MATRIX_LOCATION);
		//printCSVHeader();
		//runEntireGASuiteForStatistics();
		runTheGAWithParams(defaultPopulationCount, defaultMutationChance, defaultFitnessKeepingAmount, defaultIterationAmount);
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
			
		         0   1     2     3     4     5     6     7
			0    0   179   129   157   146   105   79    119
			1    179 0     79    141   33    207   118   64
			2    129 79    0     131   43    154   50    36      
			3    157 141   131   0     116   74*   134   98
			4    146 33    43    116   0     175   81    27
			5    105 207   154   64*   175   0     145   143
			6    79  118   50    134   81    145   0     143
			7    119 64    36    96    27    143   52    0 
	**/
	private static int[][] readDistancesMatrixFromFile(String location){
		String[][] rawArrayData = new String[8][8];
		try{
			FileReader fileReader = new FileReader(location);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			rawArrayData[0] = bufferedReader.readLine().split(",");
			rawArrayData[1] = bufferedReader.readLine().split(",");
			rawArrayData[2] = bufferedReader.readLine().split(",");
			rawArrayData[3] = bufferedReader.readLine().split(",");
			rawArrayData[4] = bufferedReader.readLine().split(",");
			rawArrayData[5] = bufferedReader.readLine().split(",");
			rawArrayData[6] = bufferedReader.readLine().split(",");
			rawArrayData[7] = bufferedReader.readLine().split(",");
			bufferedReader.close();
			fileReader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		int[][] integerArrayData = new int[8][8];
		for(int i = 0; i < rawArrayData.length; i++){
			for(int j = 0; j < rawArrayData[i].length; j++){
				integerArrayData[i][j] = Integer.parseInt(rawArrayData[i][j]);
			}
		}
		
		return integerArrayData;
	}
	
	private static void runEntireGASuiteForStatistics(){
		for(int maxPopulationSize : MAXIMUM_POPULATION_COUNTS){
			repeatTheGAForStatisticsWithTheseParams(maxPopulationSize, defaultMutationChance, defaultFitnessKeepingAmount, defaultIterationAmount);
		}
		for(double mutationChance : MUTATION_CHANCES){
			repeatTheGAForStatisticsWithTheseParams(defaultPopulationCount, mutationChance, defaultFitnessKeepingAmount, defaultIterationAmount);
		}
		for(double fitnessKeepPercent : FITNESS_KEEPING_AMOUNT){
			repeatTheGAForStatisticsWithTheseParams(defaultPopulationCount, defaultMutationChance, fitnessKeepPercent, defaultIterationAmount);
		}
		for(int iterationCount : ITERATION_AMOUNTS){
			repeatTheGAForStatisticsWithTheseParams(defaultPopulationCount, defaultMutationChance, defaultFitnessKeepingAmount, iterationCount);
		}
	}
	
	private static void repeatTheGAForStatisticsWithTheseParams(int maxPopulationSize, double mutationChance, double amountOfFittestPopulationToKeep, int iterationCount){
		for(int j = 0; j < REPEAT_THIS_GA_FOR_STATISTICS; j++){
			runTheGAWithParams(maxPopulationSize, mutationChance, amountOfFittestPopulationToKeep, iterationCount);
		}	
	}

	private static void runTheGAWithParams(int maxPopulationSize, double mutationChance, double amountOfFittestPopulationToKeep, int iterationCount){
		long gaStart = System.currentTimeMillis();
		RouteSearch routeSearch = new RouteSearch(maxPopulationSize, mutationChance, amountOfFittestPopulationToKeep, distancesMatrix);
		for(int k = 0; k < iterationCount; k++){
			routeSearch.iterateOneStep();
		}
		printResultForCSV( maxPopulationSize, mutationChance, amountOfFittestPopulationToKeep, 
				iterationCount, routeSearch.getCurrentBestGenotype(), 
				routeSearch.getCurrentBestGenotypeScore(), (System.currentTimeMillis()-gaStart));
	}
	
	private static void printCSVHeader(){
		System.out.println(
				"maxPopulationSize,"+
				"mutationChance,"+
				"iterationCount,"+
				"amountOfFittestPopulationToKeep,"+
				"bestGenotype,"+
				"bestGenotypeScore,"+
				"totalTime");
	}
	private static void printResultForCSV(int maxPopulationSize, double mutationChance, double amountOfFittestPopulationToKeep, int iterationCount, String bestGenotype, int bestGenotypeScore, long totalTime){
		System.out.println(
				""+maxPopulationSize+
				","+mutationChance+
				","+iterationCount+
				","+amountOfFittestPopulationToKeep+
				","+bestGenotype+
				","+bestGenotypeScore+
				"," + totalTime);
	}
}