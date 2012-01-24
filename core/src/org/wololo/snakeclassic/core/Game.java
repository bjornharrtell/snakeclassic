package org.wololo.snakeclassic.core;

import org.wololo.snakeclassic.core.gfx.Screen;
import org.wololo.snakeclassic.vmlayer.VMContext;

public class Game {
	public VMContext vmContext;

	int levelticksThreshold = 100;
	int levelticks = 0;
	int snackticks = 0;

	boolean inited = false;

	public boolean running = false;

	static public final int STATE_MENU = 0;
	static public final int STATE_GAME = 1;
	static public final int STATE_PAUSE = 2;
	static public final int STATE_GAMEOVER = 3;

	public int state = STATE_MENU;

	Screen screen;
	public Snake snake;
	Coordinate snackPosition = new Coordinate(0, 0);

	public Cell[] grid;
	public int width;
	public int tileSize;
	public int height;
	public int widthOff;
	public int heightOff;

	public int level;

	public int highscore;
	
	int bottomOffset;

	public Game(VMContext vmContext, int highscore, int bottomOffset) {
		this.vmContext = vmContext;
		this.highscore = highscore;
		this.bottomOffset = bottomOffset;
	}

	void init() {
		screen = new Screen(this, bottomOffset);
		if (screen.width > screen.height) {
			width = 60;
		} else {
			width = 40;
		}
		tileSize = screen.width / width;
		widthOff = screen.width % width / 2;
		// screen.widthGame = screen.width - (screen.width % width);
		screen.widthGame = width * tileSize - (screen.width % width);
		height = screen.height / tileSize - 2;
		screen.heightGame = height * tileSize;// - tileSize*2;
		heightOff = tileSize * 2;
		grid = new Cell[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				grid[y * width + x] = new Cell();
			}
		}

		initgame();

		inited = true;
	}

	void dirty() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				grid[y * width + x].dirty = true;
			}
		}
	}

	void initgame() {
		level = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				grid[y * width + x].occupied = false;
				grid[y * width + x].dirty = true;
			}
		}

		snake = new Snake(this, new Coordinate(10, 10));

		screen.clear();
	}

	public void run() {
		running = true;

		if (!inited)
			init();

		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			while (unprocessed >= 1) {
				ticks += 1;
				if (state != STATE_PAUSE)
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
				screen.render();
				vmContext.render(screen.bitmap);
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				//System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void pause() {
		state = STATE_PAUSE;
	}

	public void tick() {
		if (state != STATE_GAME)
			return;

		if (levelticks > levelticksThreshold) {
			levelticks = 0;
			if (level < Snake.INITIAL_SPEED - 1) {
				level++;
				levelticksThreshold += levelticksThreshold * 0.5f;
			}
		}

		if (snackticks > 900) {
			snackticks = 0;

			Cell cell = getCell(snackPosition);
			cell.snack = false;
			cell.dirty = true;

			snackPosition.setTo((int) (Math.random() * width),
					(int) (Math.random() * height));

			cell = getCell(snackPosition);
			cell.snack = true;
			cell.dirty = true;
		}

		if (snake.alive) {
			snake.tick();
		} else {
			state = STATE_GAMEOVER;
			if (snake.score > highscore)
				highscore = snake.score;
		}

		levelticks++;
		snackticks++;
	};

	public Cell getCell(Coordinate coordinate) {
		return grid[coordinate.y * width + coordinate.x];
	}

	public void click(int x, int y) {
		if (state == STATE_GAME) {
			if (x < screen.width / 2) {
				snake.turnLeft();
			} else {
				snake.turnRight();
			}
		} else if (state == STATE_MENU) {
			initgame();
			state = STATE_GAME;
		} else if (state == STATE_PAUSE) {
			dirty();
			state = STATE_GAME;
		} else if (state == STATE_GAMEOVER) {
			state = STATE_MENU;
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
