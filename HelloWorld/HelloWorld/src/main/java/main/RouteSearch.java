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
	
	private String makeChild(String mother, String father){
		String child = "";
		
		int crossoverPoint = random.nextInt(mother.length());
		child = mother.substring(0, crossoverPoint) +
				father.substring(crossoverPoint);
				
		boolean[] visitedLocations = {false,false,false,false,false,false,false,false};
		boolean[] visitedTwiceLocations = {false,false,false,false,false,false,false,false};
		
		for(int i = 0; i < child.length()-1; i++){
			int currentLocation = Integer.parseInt(String.valueOf(child.charAt(i)));
			if(visitedLocations[currentLocation]){
				visitedTwiceLocations[currentLocation] = true;
			} else {
				visitedLocations[currentLocation] = true;
			}
		}
		
		child = removeDuplicatesFromChild(child, visitedTwiceLocations);
		child = insertMissingIntoChild(child, mother, father, visitedLocations);
		
		return mutateChild(child);
	}
	
	private String removeDuplicatesFromChild(String child, boolean[] duplicates){
		for(int i = 1; i < duplicates.length; i++){
			if(duplicates[i]){
				int positionOfDuplicate;
				if(random.nextBoolean()){
					positionOfDuplicate = child.indexOf(""+i);
				}else{
					positionOfDuplicate = child.lastIndexOf(""+i);
				}
				child = child.substring(0, positionOfDuplicate) + 
						child.substring(positionOfDuplicate+1);
			}
		}
		return child;
	}
	
	private String insertMissingIntoChild(String child, String mother, String father, boolean[] visitedLocations){
		for(int i = 1; i < visitedLocations.length; i++){
			if(!visitedLocations[i]){
				String chosenParent;
				if(random.nextBoolean()){
					chosenParent = mother;
				} else {
					chosenParent = father;
				}
				int positionOfNumberNextToCurrentInParent = chosenParent.indexOf(""+i)-1;
				String numberNextToCurrentInParent = String.valueOf(chosenParent.charAt(positionOfNumberNextToCurrentInParent));
				int positionToPlaceAt = child.indexOf(numberNextToCurrentInParent);
				if(positionToPlaceAt < 0){
					child = child.substring(0, child.length()-1) + i + "0";
				} else if(positionToPlaceAt==0){
					child = "0" + i + child.substring(1);
				} else {
					child = child.substring(0, positionToPlaceAt) + i + child.substring(positionToPlaceAt);
				}
			}
		}
		return child;
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