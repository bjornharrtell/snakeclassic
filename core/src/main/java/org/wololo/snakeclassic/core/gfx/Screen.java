package org.wololo.snakeclassic.core.gfx;

import org.wololo.snakeclassic.core.Cell;
import org.wololo.snakeclassic.core.Game;
import org.wololo.snakeclassic.vmlayer.BitmapFactory;
import org.wololo.snakeclassic.vmlayer.Canvas;
import org.wololo.snakeclassic.vmlayer.CanvasFactory;

public class Screen {

	Game game;
	BitmapFactory bitmapFactory;
	CanvasFactory canvasFactory;

	public Object bitmap;
	Canvas canvas;

	public int width;
	public int height;
	public int heightGame;
	public int widthGame;
	private int scoreCache = -1;
	private int levelCache = -1;

	public Screen(Game game) {
		this.game = game;
		bitmapFactory = game.vmContext.createBitmapFactory();
		canvasFactory = game.vmContext.createCanvasFactory();
		width = game.vmContext.getScreenWidth();
		height = game.vmContext.getScreenHeight();

		bitmap = bitmapFactory.create(width, height, BitmapFactory.OPAGUE);
		canvas = canvasFactory.create(bitmap);

		canvas.clearRect(0, 0, width, height);
		
		canvas.drawRect(game.widthOff-1, game.heightOff-1, widthGame+1, heightGame+1);
	}

	public void render() {
		switch (game.state) {
		case Game.STATE_MENU:
			renderMenu();
			break;
		case Game.STATE_GAME:
			renderGame();
			renderScore();
			break;
		case Game.STATE_PAUSE:
			renderPause();
			break;
		case Game.STATE_GAMEOVER:
			renderGameover();
			break;
		}
	}

	void renderGame() {
		//canvas.clearRect(0, game.tileSize * 2, width, height);
		canvas.drawRect(game.widthOff-1, game.heightOff-1, widthGame+1, heightGame+1);
		int oy = game.heightOff;
		for (int y = 0; y < game.height; y++) {
			int ox = game.widthOff;
			for (int x = 0; x < game.width; x++) {
				Cell cell = game.grid[y * game.width + x];

				if (cell.dirty) {
					if (cell.occupied || cell.snack) {
						canvas.fillRect(x * game.tileSize + ox, y
								* game.tileSize + oy, game.tileSize,
								game.tileSize);
					} else {
						canvas.clearRect(x * game.tileSize + ox, y
								* game.tileSize + oy, game.tileSize,
								game.tileSize);
					}
					cell.dirty = false;
				}
			}
		}
	}

	void renderScore() {
		if (scoreCache != game.snake.score || levelCache != game.level) {
			canvas.clearRect(0, 0, width, game.tileSize * 2 - 1);

			canvas.drawTextRight(width, game.tileSize * 2 - 1,
					game.tileSize * 2 - 1, "Score: " + game.snake.score);
			canvas.drawTextLeft(0, game.tileSize * 2 - 1,
					game.tileSize * 2 - 1, "Level: " + game.level);
			scoreCache = game.snake.score;
			levelCache = game.level;

			canvas.drawRect(0, game.tileSize * 2 - 1, width, 0);
		}
	}

	void renderPause() {
		canvas.clearRect(width / 2 - width / 4, height / 2 - height / 32
				- game.tileSize, width / 2, height / 16);
		canvas.drawTextCenter(width / 2, height / 2, game.tileSize * 2,
				"Game paused");
	}

	void renderMenu() {
		clear();

		canvas.drawTextCenter(width / 2, height / 2, game.tileSize * 3,
				"Snake Classic");

		canvas.drawTextCenter(width / 2, height / 2 + game.tileSize * 3,
				game.tileSize * 2, "Highscore: " + game.highscore);

		canvas.drawTextCenter(width / 2, height / 2 + game.tileSize * 6,
				(int) (game.tileSize * 1.5f), "Touch to start a new game");
	}

	void renderGameover() {
		canvas.clearRect(width / 2 - width / 4, height / 2 - height / 32
				- game.tileSize, width / 2, height / 16);
		canvas.drawTextCenter(width / 2, height / 2, game.tileSize * 2,
				"Game over!");
	}

	public void clear() {
		canvas.clearRect(0, 0, width, height);
	}
}
