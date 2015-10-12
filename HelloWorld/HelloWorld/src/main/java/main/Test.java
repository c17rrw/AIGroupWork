import java.util.Random;
class Test {
public static String makeChild(String mother, String father){
		Random random = new Random();
		String child = "";
		
		int crossoverPoint = random.nextInt(mother.length());
		child = mother.substring(0, crossoverPoint) +
				father.substring(crossoverPoint);
				
		boolean[] visitedLocations = {false,false,false,false,false,false,false,false};
		boolean[] visitedTwiceLocations = {false,false,false,false,false,false,false,false};
		
		//checks which locations are visited and which are visited multiple times
		for(int i = 0; i < child.length()-1; i++){
			int currentLocation = Integer.parseInt(String.valueOf(numberText.charAt(position)));
			if(visitedLocations[currentLocation]){
				visitedTwiceLocations[currentLocation] = true;
			} else {
				visitedLocations[currentLocation] = true;
			}
		}
		
		
		
		//return mutateChild(child);
		return child;
	}

	public static void main(String[] rags){
System.out.println(makeChild("012345670","076543210"));
		
	}
}