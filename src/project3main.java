import static java.lang.System.out;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class project3main {

	public static void main(String[] args) throws FileNotFoundException {
		HashMap<String, Integer> cityId = new HashMap<String, Integer>();

		String inputFileName = args[0];
		File myInputFile = new File(inputFileName);
		ArrayList<String[]> myInputArray = new ArrayList<String[]>();
		try {
			Scanner myReader = new Scanner(myInputFile);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] splitted = data.split("\\s+");
				myInputArray.add(splitted);
			}
			myReader.close();

		} catch (FileNotFoundException e) {
			out.println("Catch - An error occurred.");
			e.printStackTrace();
		}

		int timeLimit = Integer.parseInt(myInputArray.get(0)[0]);
		int numCities = Integer.parseInt(myInputArray.get(1)[0]);
		String mecnunsCity = myInputArray.get(2)[0];
		String leylasCity = myInputArray.get(2)[1];


		int numOfCCities = Integer.parseInt(leylasCity.substring(1));
		int numOfDCitiesPlusLeylas = numCities - Integer.parseInt(leylasCity.substring(1)) + 1;

		DirectedGraph directedGraph = new DirectedGraph(numOfCCities);
		UndirectedGraph undirectedGraph = new UndirectedGraph(numOfDCitiesPlusLeylas);

		for (int line=3; line<numOfCCities+2;line++) {
			if (myInputArray.get(line).length>1) {
				String firstCity = myInputArray.get(line)[0];
				int i = 1;
				while (i < myInputArray.get(line).length) {
					String secCity = myInputArray.get(line)[i];
					int weight = Integer.parseInt(myInputArray.get(line)[i+1]);;
					directedGraph.addEdge(firstCity, secCity, weight);
					i+=2;
				}
			}
		}
		for (int line=numOfCCities+2; line<numCities+3;line++) {
			if (myInputArray.get(line).length>1) {
				String firstCity = myInputArray.get(line)[0];
				int i = 1;
				while (i < myInputArray.get(line).length) {
					String secCity = myInputArray.get(line)[i];
					int weight = Integer.parseInt(myInputArray.get(line)[i+1]);;
					undirectedGraph.addEdge(firstCity, secCity, weight);
					i+=2;
				}
			}
		}
		
		ArrayList<String> pathToLeyla = directedGraph.findShortestPath(mecnunsCity, leylasCity);
		int honeymoonTax = undirectedGraph.findMinimumSpan(leylasCity);
		
		String outputFileName = args[1];   //Writes to output file.
		File myOutputFile = new File(outputFileName);
		try {
			if (myOutputFile.createNewFile()) {
				out.println("File created: " + outputFileName);
			} else {
				out.println("File already exists.");
			}

			FileWriter myWriter = new FileWriter(outputFileName);
			
			if (pathToLeyla == null) {
				myWriter.write("-1"+"\n");
				myWriter.write("-1");
			}
			
			else {
				for (int city = 1;city<pathToLeyla.size()-1;city++) {
					myWriter.write(pathToLeyla.get(city)+" ");
				}
				myWriter.write(pathToLeyla.get(pathToLeyla.size()-1)+"\n");
				if (timeLimit>=Integer.parseInt(pathToLeyla.get(0))) {
					myWriter.write(String.valueOf(honeymoonTax));
				}
				else {
					myWriter.write("-1");
				}
			}
			
			myWriter.close();

		} catch (IOException e) {
			out.println("Catch - An error occurred.");
			e.printStackTrace();
		}


	}

}
