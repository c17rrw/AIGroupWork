import java.util.Random;
import java.util.ArrayList;

/*
* Class for Route Search Genetic Algorithm
*/
public class RouteSearch{
	
	ArrayList<Object> currentPopulation;
	
	public RouteSearch(){
		currentPopulation = new ArrayList<Object>();
		System.out.println("Have created a route search algorithm");
	}
	
	public void generateInitialPopulation(){}
	
	public void iterateOneStep(){
		ArrayList<Object> newPopulation = currentPopulation;
		calculateFitnessOfAll();
		keepFittestSolutions();
		generateNewSolutions();
	}
	
	public void calculateFitnessOfAll(){}
	public int calculateFitnessOfOne(Object solution){}
	public void keepFittestSolutions(){}
	public void generateNewSolutions(){}
	public Object makeChild(Object mother, Object father){}
	public Object mutateChild(Object child){}
}