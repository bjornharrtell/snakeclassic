package org.wololo.snakeclassic.core.gfx;

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
	
	int width;
	int height;
	
	public Screen(Game game) {
		this.game = game;
		bitmapFactory = game.vmContext.createBitmapFactory();
		canvasFactory = game.vmContext.createCanvasFactory();
		width = game.vmContext.getScreenWidth();
		height = game.vmContext.getScreenHeight();
		
		bitmap = bitmapFactory.create(width, height, BitmapFactory.OPAGUE);
		canvas = canvasFactory.create(bitmap);
		
		canvas.clearRect(0, 0, width, height);
	}
	
	public void render() {
		
	}
}
