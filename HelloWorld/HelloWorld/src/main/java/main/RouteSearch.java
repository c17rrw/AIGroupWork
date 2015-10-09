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
	
	private String[] currentPopulation;
	private String[] newPopulation;
	private String currentBestGenotype;
	
	public RouteSearch(){
		this(10,0.01,6);
	}
	
	public RouteSearch(int maxPopulationSize, double mutationChance, int keepTopNFittestSolutions){
		MAX_POPULATION_SIZE = maxPopulationSize;
		MUTATION_CHANCE = mutationChance;
		KEEP_TOP_N_FITTEST_SOLUTIONS = keepTopNFittestSolutions;
		random = new Random();
		generateInitialPopulation();
	}
	
	/**TODO.
		Randomize the initial population's genotypes
	**/
	private void generateInitialPopulation(){
		currentPopulation = new String[MAX_POPULATION_SIZE];
		for(int i = 0; i< currentPopulation.length; i++){
			currentPopulation[i] = "01234560";
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
	
	/**TODO**/
	private int calculateFitnessOfOne(String solution){
		return 0;
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