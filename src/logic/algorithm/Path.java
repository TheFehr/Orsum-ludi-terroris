package logic.algorithm;

import java.util.ArrayList;

import logic.graph.Node;
import logic.graph.Point;

public class Path {
	private ArrayList<Point> points;

	public Path(ArrayList<Node> nodes) {
		super();
		
		this.points = new ArrayList<>();
		nodes.stream().forEach((node) -> {
			points.add(node.getPoint());
		});
	}

	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public Point getDestination() {
		return this.points.get(this.points.size() - 1);
	}
	
	public Point getStart() {
		return this.points.get(0);
	}
}
