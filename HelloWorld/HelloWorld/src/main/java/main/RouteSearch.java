import java.util.Random;

//TODO: Fix tabs

/*
* Class for Route Search Genetic Algorithm
*/
public class RouteSearch{
	
	private final int MAX_POPULATION_SIZE = 4;
	
	//TODO: Decide whether to pass arguments or store them as private fields
	Object[] currentPopulation;
	Object[] newSolution;
	int[] newPopulationScores;
	
	public RouteSearch(){
		System.out.println("Have created a route search algorithm");
		generateInitialPopulation();
	}
	
	private void generateInitialPopulation(){
		currentPopulation = new Object[MAX_POPULATION_SIZE];
	}
	
	public void iterateOneStep(){
		Object[] newPopulation = currentPopulation;
		calculateFitnessOfAll(newPopulation);
		keepFittestSolutions(newPopulation, newPopulationScores);
		generateNewSolutions();
	}
	
	private void calculateFitnessOfAll(Object[] newPopulation){
		newPopulationScores = new int[MAX_POPULATION_SIZE];
		for(int i = 0; i < newPopulation.length; i++){
			newPopulationScores[i] = calculateFitnessOfOne(newPopulation[i]);
		}
	}
	private int calculateFitnessOfOne(Object solution){
		return 0;
	}
	private void keepFittestSolutions(Object[] newPopulation, int[] newPopulationScores){
	
	}
	private void generateNewSolutions(){
	
	}
	private Object makeChild(Object mother, Object father){
		return new Object();
	}
	private Object mutateChild(Object child){
		return new Object();
	}
}