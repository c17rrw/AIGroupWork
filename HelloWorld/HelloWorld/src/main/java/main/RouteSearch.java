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
		//currentPopulation = concatenateArrays(fittestSolutions, newSolutions);
		return currentPopulation;
	}
	
	public String getCurrentBestGenotype(){
		return currentBestGenotype;
	}
	
	public int getCurrentBestGenotypeScore(){
		//return calculateFitnessOfOne(currentBestGenotype);
		return 0;
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
	private int[] calculateFitnessOfAll(String[] population){
		int[] populationScores = new int[MAX_POPULATION_SIZE];
		for(int i = 0; i < population.length; i++){
			populationScores[i] = calculateFitnessOfOne(population[i]);
		}
		return populationScores;
	}
	
	/**TODO.
		Needs testing.
	**/
	private int calculateFitnessOfOne(String solution){
		String[] routeLocationStrings = solution.split("");
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
	private String[] keepFittestSolutions(String[] population, int[] populationScores){
		return new String[KEEP_TOP_N_FITTEST_SOLUTIONS];
	}
	
	/**TODO**/
	private String[] generateNewSolutions(String[] parentStock){
		return new String[MAX_POPULATION_SIZE-KEEP_TOP_N_FITTEST_SOLUTIONS];
	}
	
	/**TODO.
		How to ensure a new child is valid?
		Do crossover
		Check that all places are visited
		Remove any non-zero duplicates
		For any missing, insert them at the position they were in one parent
	**/
	private String makeChild(String mother, String father){
		String child = "";
		for(int i = 0; i < mother.length; i++){
			if(random.nextBoolean()){
				child += mother.charAt(i);
			}else{
				child += father.charAt(i);
			}
		}
		boolean visited = {false,false,false,false,false,false,false};
		boolean duplicates = {false,false,false,false,false,false,false};
		for(int i = 1; i < child.length-1; i++){
			if(visited[child[i]]){
				duplicates[child[i]] = true;
			} else {
				visited[child[i]]=true;
			}
		}
		for(int i = 0; i < visited.length; i++){
			if(duplicates[i]){
				child = child.substring(0,child.indexOf(""+i)) +
						child.substring(child.indexOf(""+i)+1);
			}
		}
		for(int i = 0; i < visited.length; i++){
			if(!visited[i]){
				int positionInParent;
				if(random.nextBoolean()){
					positionInParent = mother.indexOf(""+i);
				}else{
					positionInParent = father.indexOf(""+i);
				}
				child = child.substring(0, positionInParent) +
					i + child.substring(positionInParent+1);
			}
		}
		return mutateChild(child);
	}
	
	private String mutateChild(String child){
		if(random.nextDouble() < MUTATION_CHANCE){
			int swapPosition = random.nextInt(6)+2;
			String mutatedChild = child.substring(0,swapPosition-1) +
								  child.charAt(swapPosition) + 
								  child.charAt(swapPosition-1) +
								  child.substring(swapPosition+1);
			return mutatedChild;
		}else{
			return child;
		}
	}
}