import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class UndirectedGraph {
	int numVertices;
	HashMap<String,Vertex> vertices = new HashMap<String, Vertex>();
	Edge minEdge=null;


	public UndirectedGraph(int numVertices) {
		this.numVertices = numVertices;
	}


	public void addEdge(String x, String y, int weight) {
		if (!x.equals(y)) {
			if (!vertices.containsKey(x))
				vertices.put(x, new Vertex(x));
			if (!vertices.containsKey(y))
				vertices.put(y, new Vertex(y));
			vertices.get(x).neighborVertices.add(vertices.get(y));
			vertices.get(y).neighborVertices.add(vertices.get(x));
			Edge myEdge = new Edge(vertices.get(x), vertices.get(y), weight);
			if (minEdge==null)
				minEdge = myEdge;
			if (myEdge.weight<minEdge.weight)
				minEdge = myEdge;
			vertices.get(x).neighborEdges.add(myEdge);
			vertices.get(y).neighborEdges.add(new Edge(vertices.get(y), vertices.get(x), weight));
		}
	}




	public int findMinimumSpan(String startVertex) {
		ArrayList<Edge> spanEdges = new ArrayList<Edge>();
		Comparator<Edge> edgeWeightPriority = new Comparator<Edge>() {
			@Override
			public int compare(Edge first, Edge second) {
				return first.weight - second.weight;
			}
		};
		PriorityQueue<Edge> edgeQueue = new PriorityQueue<Edge>(edgeWeightPriority);
		if (minEdge==null)
			return -2;
		edgeQueue.add(minEdge);
		while (spanEdges.size()<vertices.size()-1) {
			Edge myEdge = edgeQueue.poll();
			if ((myEdge.from.notSpanned==false&&myEdge.to.notSpanned==false)) {
				myEdge.from.neighborVertices.remove(myEdge.to);
				myEdge.from.neighborEdges.remove(myEdge);
				myEdge.to.neighborVertices.remove(myEdge.from);
				myEdge.to.neighborEdges.remove(myEdge);
				continue;
			}
			Vertex firstVertex = myEdge.from;
			Vertex secVertex = myEdge.to;
			firstVertex.notSpanned = false;
			secVertex.notSpanned = false;
			for (Edge edg : firstVertex.neighborEdges)
				if (edg.to.notSpanned)
					edgeQueue.add(edg);
			for (Edge edg : secVertex.neighborEdges)
				if (edg.to.notSpanned)
					edgeQueue.add(edg);
			spanEdges.add(myEdge);

		}

		int totalTax = 0;
		if (spanEdges.size()!=numVertices-1) {
			totalTax = -2;
			return totalTax;
		}
		for (Edge edg:spanEdges)
			totalTax += edg.weight*2;
		
		return totalTax;
	}

}
