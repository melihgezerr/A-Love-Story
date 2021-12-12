import java.util.ArrayList;

public class Vertex {
	String cityName;
	Vertex parent = null;;
	int totalLengthFromStart = Integer.MAX_VALUE;
	boolean notChecked = true;
	boolean notSpanned = true;
	ArrayList<Vertex> neighborVertices = new ArrayList<Vertex>();
	ArrayList<Edge> neighborEdges = new ArrayList<Edge>();
	
	public Vertex(String cityName) {
		this.cityName = cityName;
		
	}

	@Override
	public String toString() {
		return cityName;
	}
	
	
	
	
}


