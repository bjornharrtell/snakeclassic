package org.wololo.snakeclassic.core;

import java.util.ArrayDeque;

public class Snake {

	static public final int DIR_UP = 0;
	static public final int DIR_DOWN = 1;
	static public final int DIR_LEFT = 2;
	static public final int DIR_RIGHT = 3;

	Game game;
	public Coordinate position;
	ArrayDeque<Coordinate> positions = new ArrayDeque<Coordinate>();

	public int direction = DIR_RIGHT;
	int length = 20;

	static public final int INITIAL_SPEED = 20;
	int speedCounter = INITIAL_SPEED;

	public boolean alive = true;

	Snake(Game game, Coordinate position) {
		this.game = game;
		this.position = position;

		positions.push(position.clone());

		game.getCell(position).occupied = true;
	}

	public void tick() {
		if (--speedCounter == 0) {
			switch (direction) {
			case DIR_UP:
				position.y--;
				break;
			case DIR_DOWN:
				position.y++;
				break;
			case DIR_LEFT:
				position.x--;
				break;
			case DIR_RIGHT:
				position.x++;
				break;
			}

			if (position.x < 0 || position.x >= game.width || position.y < 0
					|| position.y >= game.height) {
				alive = false;
			} else {
				Cell cell = game.getCell(position);

				if (cell.snack) {
					length += 10;
					cell.snack = false;
				}

				if (cell.occupied) {
					alive = false;
				} else {
					cell.occupied = true;
					positions.push(position.clone());

					if (positions.size() == length) {
						game.getCell(positions.removeLast()).occupied = false;
					}
				}
			}

			speedCounter = INITIAL_SPEED - game.level;
		}
	}

	public void turnLeft() {
		switch (direction) {
		case DIR_UP:
			direction = DIR_LEFT;
			break;
		case DIR_DOWN:
			direction = DIR_RIGHT;
			break;
		case DIR_LEFT:
			direction = DIR_DOWN;
			break;
		case DIR_RIGHT:
			direction = DIR_UP;
			break;
		}
	}

	public void turnRight() {
		switch (direction) {
		case DIR_UP:
			direction = DIR_RIGHT;
			break;
		case DIR_DOWN:
			direction = DIR_LEFT;
			break;
		case DIR_LEFT:
			direction = DIR_UP;
			break;
		case DIR_RIGHT:
			direction = DIR_DOWN;
			break;
		}
	}

}
