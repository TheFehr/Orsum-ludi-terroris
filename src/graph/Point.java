package graph;

import logic.Index2D;

public class Point {
	private double x;
	private double y;
	
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}


	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}
	
	public Index2D toIndex2d(double fieldSize) {
		return new Index2D((int)Math.floor(x * fieldSize), (int)Math.floor(y * fieldSize));
	}
}