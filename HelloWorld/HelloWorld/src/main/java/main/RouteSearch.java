import java.util.Random;

/**
TODO.
	How to encode solutions [Genotype]
	What level of randomness to have for mutating
	How large should population be
	How to splice parent genes
	How many of the fittest to keep
**/

/**
* Class for Route Search Genetic Algorithm.
**/
public class RouteSearch{
	
	private final int MAX_POPULATION_SIZE = 4;
	
	private Random random;
	
	Object[] currentPopulation;
	Object[] newSolution;
	int[] newPopulationScores;
	
	public RouteSearch(){
		System.out.println("Have created a route search algorithm");
		random = new Random();
		generateInitialPopulation();
	}
	
	/**TODO**/
	private void generateInitialPopulation(){
		currentPopulation = new Object[MAX_POPULATION_SIZE];
	}
	
	/**TODO**/
	public void iterateOneStep(){
		Object[] newPopulation = currentPopulation;
		calculateFitnessOfAll(newPopulation);
		keepFittestSolutions(newPopulation, newPopulationScores);
		generateNewSolutions();
	}
	
	/**TODO**/
	private void calculateFitnessOfAll(Object[] newPopulation){
		newPopulationScores = new int[MAX_POPULATION_SIZE];
		for(int i = 0; i < newPopulation.length; i++){
			newPopulationScores[i] = calculateFitnessOfOne(newPopulation[i]);
		}
	}
	
	/**TODO**/
	private int calculateFitnessOfOne(Object solution){
		return 0;
	}
	
	/**TODO**/
	private void keepFittestSolutions(Object[] newPopulation, int[] newPopulationScores){
	
	}
	
	/**TODO**/
	private void generateNewSolutions(){
	
	}
	
	/**TODO**/
	private Object makeChild(Object mother, Object father){
		return new Object();
	}
	
	/**TODO**/
	private Object mutateChild(Object child){
		return random.nextBoolean() ? child : new Object();
	}
}