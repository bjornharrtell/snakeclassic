package org.wololo.snakeclassic.core;

public class Coordinate {
	int x;
	int y;

	Coordinate(int x, int y) {
		setTo(x, y);
	}
	
	void setTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
