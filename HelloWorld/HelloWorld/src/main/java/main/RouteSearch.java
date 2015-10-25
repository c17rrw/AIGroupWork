import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
* Class for Route Search Genetic Algorithm.
**/
public class RouteSearch{
	
	private final int MAX_SCORE = 100000;
	private final String GENOTYPE_START_END_CODE = "2";
	
	private final int MAX_POPULATION_SIZE;
	private final int KEEP_TOP_N_FITTEST_SOLUTIONS;
	private final double MUTATION_CHANCE;
	private final int[][] DISTANCES_MATRIX;
	private final Map<Integer, String> CITIES;
	private final int NUMBER_OF_CITIES;
	private final Random random;
	
	private int iterationNumber;
	private String[] currentPopulation;
	private int[] currentPopulationScores;
	
	private String currentBestGenotype;
	private int currentBestGenotypeScore;
	private int iterationWithBestGenotype;
	
		
	public RouteSearch(int maxPopulationSize, double mutationChance, double keepTopNFittestSolutions, int[][] distancesMatrix, Map<Integer, String> citiesMap){
		MAX_POPULATION_SIZE = maxPopulationSize;
		KEEP_TOP_N_FITTEST_SOLUTIONS = (int) (maxPopulationSize * keepTopNFittestSolutions);
		MUTATION_CHANCE = mutationChance;
		DISTANCES_MATRIX = distancesMatrix;
		CITIES = citiesMap;
		NUMBER_OF_CITIES = CITIES.size();
		random = new Random();
		
		iterationNumber = 0;
		generateInitialPopulation();
		calculateFitnessOfCurrentPopulation();
		
		currentBestGenotype = "";
		currentBestGenotypeScore = -1;
		iterationWithBestGenotype = -1;
	}
	
	/* This method is taking the Map created in the citList method at the bottom of class,
	 * Using this map we can taken each entry at random and place the Strings into an array
	 * of cities starting with and ending with Edinburgh.
	 * We also initiate the Best GenoType so far to an empty string to ensure the best is taken
	 * at step one. 
	 * */
	private void generateInitialPopulation(){
		String cityOrder;
		currentPopulation = new String[MAX_POPULATION_SIZE];
		currentPopulationScores = new int[MAX_POPULATION_SIZE];
		currentBestGenotype = "";	
		for(int j = 0; j < MAX_POPULATION_SIZE; j++){
			int i = 1;
			Map<Integer, String> cm = new HashMap<>();
			cm.putAll(CITIES);
			cityOrder = GENOTYPE_START_END_CODE;
			while(!cm.isEmpty()){
				int r = random.nextInt(NUMBER_OF_CITIES);
				if(cm.get(r) != null){
					cityOrder += (r!=2) ? r : "";
					cm.remove(r);
					i++;
				}
			}
			cityOrder += GENOTYPE_START_END_CODE;
			currentPopulation[j] = cityOrder;
		}
	}
	
	public String[] iterateOneStep(){
		iterationNumber++;
		currentPopulationScores = calculateFitnessOfCurrentPopulation();
		String[] fittestSolutions = keepFittestSolutions();
		String[] newSolutions = generateChildren();
		currentPopulation = concatenateArrays(fittestSolutions, newSolutions);
		detectCurrentBestGenotype();
		return currentPopulation;
	}
	
	private void detectCurrentBestGenotype(){
		for(String s : currentPopulation){
			int currentScore = calculateFitnessOfOne(s);
			if(currentScore>currentBestGenotypeScore){
				currentBestGenotypeScore = currentScore;
				currentBestGenotype = s;
				iterationWithBestGenotype = iterationNumber;
			}
		}
	}
	
	public int getCurrentIterationNumber(){
		return iterationNumber;
	}
	
	public int getBestIterationNumber(){
		return iterationWithBestGenotype;
	}
	
	public String getCurrentBestGenotype(){
		return currentBestGenotype;
	}
	
	public int getCurrentBestGenotypeScore(){
		return calculateFitnessOfOne(currentBestGenotype);
	}
	
	private String[] concatenateArrays(String[] a1, String[] a2){
		String[] fullArray = new String[a1.length+a2.length];
		int fullArrayPos = 0;
		for (String item : a1) {
			fullArray[fullArrayPos++] = item;
		}
		for (String item : a2) {
			fullArray[fullArrayPos++] = item;
		}
		return fullArray;
	}
	
	private int[] calculateFitnessOfCurrentPopulation(){
		int[] populationScores = new int[MAX_POPULATION_SIZE];
		for(int i = 0; i < currentPopulation.length; i++){
			populationScores[i] = calculateFitnessOfOne(currentPopulation[i]);
		}
		return populationScores;
	}
	
	private int calculateFitnessOfOne(String solution){
		String[] routeLocationStrings = solution.split("");
		int totalDistance = 0;
		for(int i = 1; i < routeLocationStrings.length; i++){
			int currentLocation = Integer.parseInt(routeLocationStrings[i]);
			int previousLocation = Integer.parseInt(routeLocationStrings[i-1]);
			totalDistance += DISTANCES_MATRIX[previousLocation][currentLocation];
		}
		return (totalDistance==0) ? (MAX_SCORE+1) : (int) ((1.0 / totalDistance) * MAX_SCORE);
	}
	
	public void printCurrentIteration(){
		for (String genotype : currentPopulation) {
			String[] routeLocationStrings = genotype.split("");
			for(int i = 0; i < routeLocationStrings.length-1; i++){
				System.out.print(CITIES.get(Integer.parseInt(routeLocationStrings[i]))+"->");
			}
			System.out.println(CITIES.get(Integer.parseInt(routeLocationStrings[routeLocationStrings.length-1])));
		}
	}
	
	private int rouletteWheelSelect() {
        double pointer, accumulatingFitness, randomReal;
        int currentPopulationIndex = 0;

        double fitnessTotalScore = 0.0;
        for (int currentScore : currentPopulationScores) {
            fitnessTotalScore += currentScore;
        }

        randomReal = getRandomNumberFrom(0, 1000000) / 1000000.0;
        pointer = fitnessTotalScore * randomReal;
        accumulatingFitness = 0.0;
		
        while (currentPopulationIndex < MAX_POPULATION_SIZE) {
            accumulatingFitness += currentPopulationScores[currentPopulationIndex];
            if (pointer < accumulatingFitness) {
                break;
            }

            if (currentPopulationIndex != MAX_POPULATION_SIZE - 1) {
                currentPopulationIndex++;
            }
        }
        return currentPopulationIndex;
    }
	
	private int getRandomNumberBetween(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public int getRandomNumberFrom(int min, int max) {
        return getRandomNumberBetween(min, max+1);
    }
	
	private String[] keepFittestSolutions(){
		int[] temporaryPopulationScores = new int[currentPopulationScores.length];
		System.arraycopy(currentPopulationScores, 0, temporaryPopulationScores, 0, currentPopulationScores.length);
		String[] fittest = new String[KEEP_TOP_N_FITTEST_SOLUTIONS];
		for(int i = 0 ; i< KEEP_TOP_N_FITTEST_SOLUTIONS; i++){
			int currentHighest = 0;
			int currentHighestPosition = 0;
			for(int j = 0; j<temporaryPopulationScores.length; j++){
				if(temporaryPopulationScores[j] > currentHighest){
					currentHighest = temporaryPopulationScores[j];
					currentHighestPosition = j;
				}
			}
			fittest[i] = currentPopulation[currentHighestPosition];
			temporaryPopulationScores[currentHighestPosition] = -1;
		}
		return fittest;
	}
	
	private String[] generateChildren(){
		String[] children = new String[MAX_POPULATION_SIZE-KEEP_TOP_N_FITTEST_SOLUTIONS];
		for(int i = 0; i < children.length; i++){
			children[i] = makeChild();
		}
		return children;
	}
	
	private String makeChild(){
		String child;
		
		String mother = currentPopulation[rouletteWheelSelect()];
		String father = currentPopulation[rouletteWheelSelect()];
		
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
		for(int i = 0; i < duplicates.length; i++){
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
		for(int i = 0; i < visitedLocations.length; i++){
			if(!visitedLocations[i] && i!=2){
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
					child = child.substring(0, child.length()-1) + i + GENOTYPE_START_END_CODE;
				} else if(positionToPlaceAt==0){
					child = GENOTYPE_START_END_CODE + i + child.substring(1);
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