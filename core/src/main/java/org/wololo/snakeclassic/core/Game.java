package org.wololo.snakeclassic.core;

import org.wololo.snakeclassic.core.gfx.Screen;
import org.wololo.snakeclassic.vmlayer.VMContext;

public class Game {
	public VMContext vmContext;

	int levelticks = 0;
	int snackticks = 0;

	public boolean running = false;
	public boolean paused = false;

	Screen screen;
	Snake snake;
	Coordinate snackPosition = new Coordinate(0, 0);

	public Cell[] grid;
	public int width;
	public int tileSize;
	public int height;
	public int widthOff;
	public int heightOff;

	public int level = 0;

	public Game(VMContext vmContext) {
		this.vmContext = vmContext;
	}

	void init() {
		screen = new Screen(this);
		if (screen.width > screen.height) {
			width = 40;
		} else {
			width = 20;
		}
		tileSize = screen.width / width;
		widthOff = screen.width % width / 2;
		height = screen.height / tileSize;
		heightOff = (screen.height) % height / 2;
		grid = new Cell[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				grid[y * width + x] = new Cell();
				grid[y * width + x].occupied = false;
			}
		}

		snake = new Snake(this, new Coordinate(10, 10));
	}

	public void run() {
		running = true;

		init();

		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

		while (running) {
			if (paused) {
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				continue;
			}

			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			while (unprocessed >= 1) {
				ticks += 1;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames += 1;
				if (!paused)
					screen.render();
				vmContext.render(screen.bitmap);
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void pause() {
		paused = true;
	}

	public void unpause() {
		paused = false;
	}

	public void tick() {
		if (levelticks > 800) {
			levelticks = 0;
			if (level < Snake.INITIAL_SPEED - 1)
				level++;
		}

		if (snackticks > 1300) {
			snackticks = 0;

			getCell(snackPosition).snack = false;

			snackPosition.setTo((int) (Math.random() * width),
					(int) (Math.random() * height));

			getCell(snackPosition).snack = true;
		}

		if (snake.alive) {
			snake.tick();
		} else {

		}

		levelticks++;
		snackticks++;
	};

	public Cell getCell(Coordinate coordinate) {
		return grid[coordinate.y * width + coordinate.x];
	}

	public void click(int x, int y) {
		if (x < screen.width / 2) {
			snake.turnLeft();
		} else {
			snake.turnRight();
		}
	};

	public void up() {
		snake.up();
	}

	public void down() {
		snake.down();
	}

	public void right() {
		snake.right();
	}

	public void left() {
		snake.left();
	}
}
