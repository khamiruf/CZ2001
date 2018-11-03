import java.util.*;
public class Main {

	public static Stack<Integer> BFS(int source, int destination, LinkedList<Integer>[] adjacentCity, int numOfCity)
	{
		List<Boolean> visited = new ArrayList<>();
		List<Integer> prev = new ArrayList<>();
		Stack<Integer> way = new Stack<Integer>();
		
		for (int i=0;i<numOfCity;i++)
		{	
			visited.add(false);
			prev.add(numOfCity);
		}
		
		visited.set(source,true);
		
		LinkedList<Integer> queue=new LinkedList<>();
		queue.add(source);
		
		int curr=source;
		
		while (!queue.isEmpty() && curr!=destination)
		{
			curr=queue.poll();
			LinkedList<Integer> adj=adjacentCity[curr];
			for (Integer i:adj)
			{
				if (visited.get(i)== false)
				{
					queue.add(i);
					visited.set(i,true);
					prev.set(i, curr);
				}				
			}
			
		}
		
		int prevLoc=destination;
		while (prevLoc!=source)
		{
			way.push(prevLoc);
			prevLoc=prev.get(prevLoc);
		}
		way.push(source);
		
		return way;
	}
	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		while (true)
		{
			System.out.println("Enter the number of cities:");
			int numOfCity = sc.nextInt();
			LinkedList<Integer>[] adjacentCity= new LinkedList[numOfCity];
			for (int i=0;i<numOfCity;i++)
			{
				LinkedList<Integer> adjacent = new LinkedList<Integer>();
				adjacentCity[i]=adjacent;
			}
			String[]cityName=CityNameGenerator.generateCityNames("src/ALOTOFCITIES.txt",numOfCity);
			
			int numOfEdge;
			do {
				//conditions for the min and max number of edges, based on the number of nodes(numOfCity)
				//max: where there is at least 1 pair of cities with no non-stop flight, but a route between them
	 			System.out.println("Enter the number of edge("+(numOfCity-1)+"-"+(((numOfCity*(numOfCity-1)/2))-1)+")\t:"); //\t: title, to center the text
				numOfEdge = sc.nextInt();
			} while (numOfEdge<(numOfCity-1) || numOfEdge>(numOfCity)*((numOfCity-1/2)-1));

			Random random = new Random();
			List<Integer> wayToGo = new ArrayList<>();
			Stack<Integer> way = new Stack<Integer>();
			
			long average = 0;
			long start=0;
			long end=0;
			
			int source=-1,destination=-1;
			do {
				System.out.println("Enter source ID:"); 
				source = sc.nextInt();
				System.out.println("Enter destination ID :");
				destination = sc.nextInt();
			} while (source<0 ||source>=numOfCity || destination<0 || destination>=numOfCity);
			
			for(int j=0; j<440; j++) {
				for(int i=0; i<numOfCity; i++) {
					adjacentCity[i].clear();
				}
				//create minimumEdges for BST
				for (int i=0;i<numOfCity;i++)
				{
					if (i>0)
					{
						adjacentCity[i].add(i-1);
						adjacentCity[i-1].add(i);
					}
				}
	
				for (int i=0;i<numOfEdge-(numOfCity-1);i++)
				{
					int v1=random.nextInt(numOfCity);
					int v2=random.nextInt(numOfCity);
	
					//if the cities are the same, or both cities are already connected, just continue
					if (Math.abs((v1-v2))<=1|| adjacentCity[v1].contains(v2))
						i--;
	
					//otherwise, add it into the graph
					else
					{
						adjacentCity[v1].add(v2);
						adjacentCity[v2].add(v1);
					}
				}
				
				//print out the graph (in a hash map way)
	 			/*System.out.println("The following is how the graph is connected: ");
				for (int i=0;i<numOfCity;i++)
				{
					System.out.println(i + ": " + cityName[i]);
					System.out.println(i+"->"+ adjacentCity[i]);
				}*/
	
				/*int source=-1,destination=-1;
				do {
					System.out.println("Enter source ID:"); 
					source = sc.nextInt();
					System.out.println("Enter destination ID :");
					destination = sc.nextInt();
				} while (source<0 ||source>=numOfCity || destination<0 || destination>=numOfCity);*/

				start=System.nanoTime();
				way = BFS(source,destination,adjacentCity,numOfCity);
				end=System.nanoTime();
				average += end-start;
				System.out.println(end-start);
			}
			average /= 440.0;
			System.out.println("The following is the shortest path:");
 			
 			do {
 				int curr;
 				if(way.peek() != destination) {
 					curr = way.pop();
 					System.out.print(curr + ": " + cityName[curr] + " -> ");
 				}
 				else {
 					curr = way.pop();
 					System.out.print(curr + ": " + cityName[curr]);
 				}	
 				
 			} while (!way.isEmpty());
 			
 			System.out.println();
			System.out.println("Time taken: "+ average);
		
			System.out.println("Do you want to continue? Y/N");
 			String input = sc.next();
			if (input.equals("N") || input.equals("n"))
				break;
		}
	}

}
