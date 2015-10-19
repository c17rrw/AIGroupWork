//package routeSearch1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
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
public class RouteSearch extends Start{
	
	
	
	private int MAX_POPULATION_SIZE;
	private int KEEP_TOP_N_FITTEST_SOLUTIONS;
	private double MUTATION_CHANCE;
	
	private Random random;
	
	private int[][] distancesMatrix;
	public int numberOfCities = 8;
	
	private String[] currentPopulation;
	private String currentBestGenotype;
	private Map<Integer, String> m = new HashMap<Integer, String>();
		
	public RouteSearch(int maxPopulationSize, double mutationChance, double keepTopNFittestSolutions, int[][] distancesMatrix){
		MAX_POPULATION_SIZE = maxPopulationSize;
		MUTATION_CHANCE = mutationChance;
		KEEP_TOP_N_FITTEST_SOLUTIONS = (int) (maxPopulationSize * keepTopNFittestSolutions);
		this.distancesMatrix = distancesMatrix;
		random = new Random();
		cityList();
		generateInitialPopulation(m);
	}
	
	/**TODO.
		Randomize the initial population's genotypes
		Need to ensure is valid, i.e. starts and ends at edinburgh, no repeats
	**/
	
	/* This method is taking the Map created in the citList method at the bottom of class,
	 * Using this map we can taken each entry at random and place the Strings into an array
	 * of cities starting with and ending with Edinburgh.
	 * We also initiate the Best GenoType so far to an empty string to ensure the best is taken
	 * at step one. 
	 * */
	private void generateInitialPopulation(Map<Integer, String> m){
		//String[] cityOrder = new String[numberOfCities+1];
		String cityOrder = "";
		currentPopulation = new String[MAX_POPULATION_SIZE];
		currentBestGenotype = "";		
		for(int j = 0; j < MAX_POPULATION_SIZE; j++){
			//System.out.println(j);
			int i = 1;
			Map<Integer, String> cm = new HashMap<Integer, String>();
			cm.putAll(m);
			//cityOrder[0] = "Edinburgh";
			cityOrder = "0";
			while(!cm.isEmpty()){
				int r = random.nextInt(numberOfCities);
				if(cm.get(r) != null){
					//System.out.println("r="+r+" - Loop "+j);
					//cityOrder[i] = cm.get(r);
					cityOrder += r;
					//System.out.println(cityOrder[i]);
					cm.remove(r);
					i++;
				}else{
					//System.out.println("An error has occured adding entry: " + i);
				}
			}
			//cityOrder[cityOrder.length-1] = "Edinburgh";
			cityOrder += "0";
			//for(String s : cityOrder){System.out.println(cityOrder);};
System.out.println(cityOrder);
			currentPopulation[j] = cityOrder;
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
	
	/*Creates a HashMap which starts with edinburgh in Key 0 
	maps a generic incremented int from 1-7 and stores the remaining cities from cities.txt
	in the map for selection by int Key.
	*/
	
private Map<Integer,String> cityList(){
		
		
		
		try{
			FileReader fr = new FileReader("cities.txt");
			BufferedReader br = new BufferedReader(fr);
			for(int marker = 0; marker < 7; marker++ ){
				String nextCity = br.readLine();
				m.put(marker, nextCity);
			}
			br.close();
			fr.close();
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		for(int c = 0; c < m.size(); c++){
			String current = m.get(c);
			//System.out.println("" + current);
		}
		
		return m;
		
	}
	
}