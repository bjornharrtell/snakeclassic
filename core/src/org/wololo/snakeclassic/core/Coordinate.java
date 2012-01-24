package org.wololo.snakeclassic.core;

public class Coordinate {
	int x;
	int y;

	Coordinate(int x, int y) {
		setTo(x, y);
	}
	
	public void setTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setTo(Coordinate coordinate) {
		setTo(coordinate.x, coordinate.y);
	}
	
	public Coordinate clone() {
		return new Coordinate(x, y);
	}
}
