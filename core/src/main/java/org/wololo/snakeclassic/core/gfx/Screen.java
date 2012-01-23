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
		canvas.clearRect(0, 0, width, height);
		int oy = game.heightOff;
		for (int y=0; y<game.height; y++) {
			int ox = game.widthOff;
			for (int x=0; x<game.width; x++) {
				Cell cell = game.grid[y*game.width+x];
				
				if (cell.occupied || cell.snack) {
					canvas.fillRect(x*game.tileSize+ox, y*game.tileSize+oy, game.tileSize, game.tileSize);
				}
				//else if (cell.snack) {
					
				//}
				//ox++;
			}
			//oy++;
		}
	}
}
