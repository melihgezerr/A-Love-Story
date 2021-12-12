import java.util.Comparator;

public class Edge implements Comparator<Edge>{
	Vertex from;
	Vertex to;
	int weight;
	public Edge(Vertex from, Vertex to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	@Override
	public String toString() {
		return from + " to= " + to + ", with weight= " + weight;
	}
	@Override
	public int compare(Edge o1, Edge o2) {
		// TODO Auto-generated method stub
		return o1.weight - o2.weight;
	}
	
	
}
