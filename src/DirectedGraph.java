import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeSet;

import javax.swing.table.TableStringConverter;

public class DirectedGraph {
	int numVertices;
	HashMap<String,Vertex> vertices = new HashMap<String, Vertex>();
	ArrayList<Edge> edges = new ArrayList<Edge>();


	public DirectedGraph(int numVertices) {
		this.numVertices = numVertices;
	}


	public void addEdge(String x, String y, int weight) {
		if (!x.equals(y)) {
			if (!vertices.containsKey(x))
				vertices.put(x, new Vertex(x));
			if (!vertices.containsKey(y))
				vertices.put(y, new Vertex(y));
			vertices.get(x).neighborVertices.add(vertices.get(y));
			Edge myEdge = new Edge(vertices.get(x), vertices.get(y), weight);
			vertices.get(x).neighborEdges.add(myEdge);
			edges.add(myEdge);
		}
	}

	public ArrayList<String> findShortestPath(String vertex1, String vertex2) {
		vertices.get(vertex1).totalLengthFromStart = 0;
		for (int neigh=0; neigh<vertices.get(vertex1).neighborVertices.size(); neigh++) {  //Sets starting Vertex length to 0 and the neighbours' to their edge weight.
			vertices.get(vertex1).neighborVertices.get(neigh).totalLengthFromStart
			= vertices.get(vertex1).neighborEdges.get(neigh).weight;		
		}
		Comparator<Vertex> totalLengthComparator = new Comparator<Vertex>() {
			@Override
			public int compare(Vertex first, Vertex second) {
				return first.totalLengthFromStart - second.totalLengthFromStart;
			}
		};
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>(totalLengthComparator);
		vertexQueue.add(vertices.get(vertex1));
		while (!vertexQueue.isEmpty()) {
			Vertex currVertex = vertexQueue.poll();
			currVertex.notChecked = false;
			for (int neighbor=0; neighbor<currVertex.neighborVertices.size();neighbor++) {
				Vertex myNeighbor = currVertex.neighborVertices.get(neighbor);
				Edge myEdge = currVertex.neighborEdges.get(neighbor);
				if (myNeighbor.notChecked){
					if (currVertex.totalLengthFromStart+myEdge.weight <= myNeighbor.totalLengthFromStart) {
						myNeighbor.parent = currVertex;
						if(vertexQueue.contains(myNeighbor)) {
							vertexQueue.remove(myNeighbor);
							myNeighbor.totalLengthFromStart = currVertex.totalLengthFromStart+myEdge.weight;
							vertexQueue.add(myNeighbor);
						}
						else{
							myNeighbor.totalLengthFromStart = currVertex.totalLengthFromStart+myEdge.weight;
							vertexQueue.add(myNeighbor);
						}
					}
				}
			}
		}
		if (!vertices.containsKey(vertex2))
			return null;
		if (vertices.get(vertex2).totalLengthFromStart == 2147483647)
			return null;
		else {
			ArrayList<String> myPath = new ArrayList<String>();
			myPath.add(vertices.get(vertex2).cityName);
			Vertex processing = vertices.get(vertex2);
			while (processing.parent!=null) {
				myPath.add(processing.parent.cityName);
				processing = processing.parent;
			}
			myPath.add(Integer.toString(vertices.get(vertex2).totalLengthFromStart));
			Collections.reverse(myPath);
			return myPath ;
		}



	}
}
