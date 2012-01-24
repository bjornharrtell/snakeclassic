package org.wololo.snakeclassic.core;

import java.util.LinkedList;

public class Snake {

	static public final int DIR_UP = 0;
	static public final int DIR_DOWN = 1;
	static public final int DIR_LEFT = 2;
	static public final int DIR_RIGHT = 3;

	Game game;
	public Coordinate position;
	LinkedList<Coordinate> positions = new LinkedList<Coordinate>();

	public int direction = DIR_RIGHT;
	int length = 15;
	public int score = 0;

	static public final int INITIAL_SPEED = 15;
	int speedCounter = INITIAL_SPEED;

	public boolean alive = true;

	Snake(Game game, Coordinate position) {
		this.game = game;
		this.position = position;

		positions.add(position.clone());

		Cell cell = game.getCell(position);
		game.getCell(position).occupied = true;
		cell.dirty = true;
	}

	public void tick() {
		if (--speedCounter == 0) {

			tickTail();

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

				score++;

				if (cell.snack) {
					length += 30;
					cell.snack = false;
					cell.dirty = true;
					score += 20*game.level*2;
				}

				if (cell.occupied) {
					alive = false;
				} else {
					cell.occupied = true;
					cell.dirty = true;
				}
			}

			speedCounter = INITIAL_SPEED - game.level;
		}
	}

	/*public void tickMenu() {
		tickTail();

		float xf = 0.2f;
		float yf = 0.4f;
		
		if (position.x < game.width - game.width * xf && position.y <= game.height * yf) {
			position.x++;
		} else if (position.y < game.height - game.height * yf && position.x >= game.width - game.width * xf ) {
			position.y++;
		} else if (position.x >= game.width * xf && position.y >= game.height * yf) {
			position.x--;
		} else if (position.y > game.height * yf) {
			position.y--;
		}

		Cell cell = game.getCell(position);

		cell.occupied = true;
	}*/

	void tickTail() {
		positions.add(position.clone());
		if (positions.size() == length) {
			Cell cell = game.getCell(positions.removeFirst());
			cell.occupied = false;
			cell.dirty = true;
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

	public void up() {
		direction = DIR_UP;
	}

	public void down() {
		direction = DIR_DOWN;
	}

	public void right() {
		direction = DIR_RIGHT;
	}

	public void left() {
		direction = DIR_LEFT;
	}

}
