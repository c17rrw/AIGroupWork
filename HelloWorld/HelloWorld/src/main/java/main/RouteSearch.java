import java.util.Random;

/**
TODO.
	How to encode solutions [Genotype]
		0-Aberdeen
		1-ayr
		2-Edinburgh
		3-fortwilliam
		4-Glasgow
		5-inverness
		6-standrews
		7-stirling
	How to splice parent genes
**/

/**
* Class for Route Search Genetic Algorithm.
**/
public class RouteSearch{
	
	private int MAX_POPULATION_SIZE;
	private int KEEP_TOP_N_FITTEST_SOLUTIONS;
	private double MUTATION_CHANCE;
	
	private Random random;
	
	private int[][] distancesMatrix;
	
	private String[] currentPopulation;
	private String[] newPopulation;
	private String currentBestGenotype;
		
	public RouteSearch(int maxPopulationSize, double mutationChance, int keepTopNFittestSolutions, int[][] distancesMatrix){
		MAX_POPULATION_SIZE = maxPopulationSize;
		MUTATION_CHANCE = mutationChance;
		KEEP_TOP_N_FITTEST_SOLUTIONS = keepTopNFittestSolutions;
		this.distancesMatrix = distancesMatrix;
		random = new Random();
		generateInitialPopulation();
	}
	
	/**TODO.
		Randomize the initial population's genotypes
		Need to ensure is valid, i.e. starts and ends at edinburgh, no repeats
	**/
	private void generateInitialPopulation(){

	currentBestGenotype = "";

		currentPopulation = new String[MAX_POPULATION_SIZE];
		for(int i = 0; i< currentPopulation.length; i++){
			currentPopulation[i] = "012345670";
		}
	}
	
	/**TODO.
		Untested
	**/
	public String[] iterateOneStep(){
		int[] currentPopulationScores = calculateFitnessOfAll(currentPopulation);
		String[] fittestSolutions = keepFittestSolutions(currentPopulation, currentPopulationScores);
		String[] newSolutions = generateNewSolutions(fittestSolutions);
		currentPopulation = concatenateArrays(fittestSolutions, newSolutions);
		return currentPopulation;
	}
	
	public String getCurrentBestGenotype(){
		return currentBestGenotype;
	}
	
	public int getCurrentBestGenotypeScore(){
		return calculateFitnessOfOne(currentBestGenotype);
	}
	
	/**TODO.
		Untested
	**/
	private String[] concatenateArrays(String[] a1, String[] a2){
		String[] fullArray = new String[a1.length+a2.length];
		int fullArrayPos = 0;
		for(int i = 0; i < a1.length; i++){
			fullArray[fullArrayPos++] = a1[i];
		}
		for(int i = 0; i < a2.length; i++){
			fullArray[fullArrayPos++] = a2[i];
		}
		return fullArray;
	}
	
	/**TODO**/
	private int[] calculateFitnessOfAll(String[] newPopulation){
		int[] newPopulationScores = new int[MAX_POPULATION_SIZE];
		for(int i = 0; i < newPopulation.length; i++){
			newPopulationScores[i] = calculateFitnessOfOne(newPopulation[i]);
		}
		return newPopulationScores;
	}
	
	/**TODO.
		Needs testing.
	**/
	private int calculateFitnessOfOne(String solution){
		String[] routeLocationStrings = solution.split("");
		for(int i = 0; i <routeLocationStrings.length; i++){
			System.out.println(""+i+": "+routeLocationStrings[i]);
		}
		int totalDistance = 0;
		for(int i = 2; i < routeLocationStrings.length; i++){
			int currentLocation = Integer.parseInt(routeLocationStrings[i]);
			int previousLocation = Integer.parseInt(routeLocationStrings[i-1]);
			totalDistance += distancesMatrix[currentLocation][previousLocation];
		}
		return totalDistance;
	}
	
	/**TODO.
		Roulette wheel?
	**/
	private String[] keepFittestSolutions(String[] newPopulation, int[] newPopulationScores){
		return new String[KEEP_TOP_N_FITTEST_SOLUTIONS];
	}
	
	/**TODO**/
	private String[] generateNewSolutions(String[] parentStock){
		return new String[MAX_POPULATION_SIZE-KEEP_TOP_N_FITTEST_SOLUTIONS];
	}
	
	/**TODO.
		How to ensure a new child is valid?
	**/
	private String makeChild(String mother, String father){
		return "";
	}
	
	/**TODO.
		Random generator is wrong
	**/
	private String mutateChild(String child){
		return random.nextDouble() % MUTATION_CHANCE == 0.0 ? child : "";
	}
}