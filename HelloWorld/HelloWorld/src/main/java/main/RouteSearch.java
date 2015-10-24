//package routeSearch1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**TODO.
-Best iteration number
-Print all iterations
-Roulette wheel thing
*/
/**
* Class for Route Search Genetic Algorithm.
**/
public class RouteSearch{
	 
	private int MAX_POPULATION_SIZE;
	private int KEEP_TOP_N_FITTEST_SOLUTIONS;
	private double MUTATION_CHANCE;
	
	private Random random;
	
	private int[][] distancesMatrix;
	public int numberOfCities = 8;
	
	private String[] currentPopulation;
	
	private String currentBestGenotype;
	private int currentBestGenotypeScore;
	
	private Map<Integer, String> m = new HashMap<Integer, String>();
		
	public RouteSearch(int maxPopulationSize, double mutationChance, double keepTopNFittestSolutions, int[][] distancesMatrix){
		MAX_POPULATION_SIZE = maxPopulationSize;
		MUTATION_CHANCE = mutationChance;
		KEEP_TOP_N_FITTEST_SOLUTIONS = (int) (maxPopulationSize * keepTopNFittestSolutions);
		this.distancesMatrix = distancesMatrix;
		random = new Random();
		createCityMap();
		generateInitialPopulation(m);
		currentBestGenotype = "";
		currentBestGenotypeScore = -1;
	}
	
	/* This method is taking the Map created in the citList method at the bottom of class,
	 * Using this map we can taken each entry at random and place the Strings into an array
	 * of cities starting with and ending with Edinburgh.
	 * We also initiate the Best GenoType so far to an empty string to ensure the best is taken
	 * at step one. 
	 * */
	private void generateInitialPopulation(Map<Integer, String> m){
		String cityOrder;
		currentPopulation = new String[MAX_POPULATION_SIZE];
		currentBestGenotype = "";		
		for(int j = 0; j < MAX_POPULATION_SIZE; j++){
			int i = 1;
			Map<Integer, String> cm = new HashMap<>();
			cm.putAll(m);
			cityOrder = "2";
			while(!cm.isEmpty()){
				int r = random.nextInt(numberOfCities);
				if(cm.get(r) != null){
					cityOrder += (r!=2) ? r : "";
					cm.remove(r);
					i++;
				}
			}
			cityOrder += "2";
			currentPopulation[j] = cityOrder;
		}
	}
	
	private Map<Integer,String> createCityMap(){
		try{
			FileReader fr = new FileReader("cities.txt");
			BufferedReader br = new BufferedReader(fr);
			for(int marker = 0; marker < 8; marker++ ){
				String nextCity = br.readLine();
				m.put(marker, nextCity);
			}
			br.close();
			fr.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	}
	
	public String[] iterateOneStep(){
		int[] currentPopulationScores = calculateFitnessOfAll(currentPopulation);
		String[] fittestSolutions = keepFittestSolutions(currentPopulation, currentPopulationScores);
		String[] newSolutions = generateChildren(fittestSolutions);
		currentPopulation = concatenateArrays(fittestSolutions, newSolutions);
		detectCurrentBestGenotype();
<<<<<<< HEAD
		for(String s : fittestSolutions){System.out.println(s);}
=======
>>>>>>> origin/master
		return currentPopulation;
	}
	
	private void detectCurrentBestGenotype(){
		for(String s : currentPopulation){
			int currentScore = calculateFitnessOfOne(s);
			if(currentScore>currentBestGenotypeScore){
				currentBestGenotypeScore = currentScore;
				currentBestGenotype = s;
			}
		}
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
		for(int i = 0; i < a1.length; i++){
			fullArray[fullArrayPos++] = a1[i];
		}
		for(int i = 0; i < a2.length; i++){
			fullArray[fullArrayPos++] = a2[i];
		}
		return fullArray;
	}
	
	private int[] calculateFitnessOfAll(String[] population){
		int[] populationScores = new int[MAX_POPULATION_SIZE];
		for(int i = 0; i < population.length; i++){
			populationScores[i] = calculateFitnessOfOne(population[i]);
		}
		return populationScores;
	}
	
	private int calculateFitnessOfOne(String solution){
		String[] routeLocationStrings = solution.split("");
		int totalDistance = 0;
		//Start at 2 because .split("") produces empty string at position 0
		for(int i = 1; i < routeLocationStrings.length; i++){
			int currentLocation = Integer.parseInt(routeLocationStrings[i]);
			int previousLocation = Integer.parseInt(routeLocationStrings[i-1]);
			totalDistance += distancesMatrix[previousLocation][currentLocation];
		}
		return (totalDistance==0) ? 1000001 : (int) ((1.0 / totalDistance) * 100000);
	}
	
	/**TODO.
		Roulette wheel?
	**/
	
	private static int rouletteWheelSelect(){
		
		
		
		double fitTotal; 
		double pointer, accumulatingFitness, randReal;
		int chromosone, randInt, selected = 0;
		int test = 0;
		int[] holder = new int[MAX_POPULATION_SIZE];
		
		fitTotal = 0.0;
		
		for(chromosone = 0; chromosone < 10; chromosone++){
			fitTotal += currentPopulationScores[chromosone];
		}
		
		randInt = getRandomNumberFrom(0, 100000);
		randReal = randInt / 1000000.0;
		pointer = fitTotal * randReal;
		accumulatingFitness = 0.0;
		
		while(selected < MAX_POPULATION_SIZE){
			if(holder[].contains() = currentPopulationScores[selected]){
				break;
			}
			accumulatingFitness += currentPopulationScores[selected];
			if (pointer < accumulatingFitness){
				break;
			}
			
			if (selected != MAX_POPULATION_SIZE -1){
				selected++;
			}
			
		}
		
		return test;		
	}
	
	 private static int getRandomNumberBetween(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static int getRandomNumberFrom(int min, int max) {
        return getRandomNumberBetween(min, max+1);
    }
	
	private String[] keepFittestSolutions(String[] population, int[] populationScores){
		//higher score = better choice = more chance of being picked
		//sum all scores
		//assign chance to each of population = score of one / population
		//make a roulette wheel
		//array
		//TEMP SOLUTION TO PREVENT CRASHES ONLY:
		String[] fittest = new String[KEEP_TOP_N_FITTEST_SOLUTIONS];
		for(int i = 0 ; i< KEEP_TOP_N_FITTEST_SOLUTIONS; i++){
			fittest[i] = population[i];
		}
		return fittest;
	}
	
	private String[] generateChildren(String[] parentStock){
		String[] children = new String[MAX_POPULATION_SIZE-KEEP_TOP_N_FITTEST_SOLUTIONS];
		for(int i = 0; i < children.length; i++){
			int parentPairPosition = random.nextInt(parentStock.length-1);
			children[i] = makeChild(parentStock[parentPairPosition], parentStock[parentPairPosition+1]);
		}
		return children;
	}
	
	private String makeChild(String mother, String father){
		String child;
		
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
					child = child.substring(0, child.length()-1) + i + "2";
				} else if(positionToPlaceAt==0){
					child = "2" + i + child.substring(1);
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