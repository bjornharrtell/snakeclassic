package org.wololo.snakeclassic.vmlayer;

public interface VMContext {
	void render(Object bitmap);
	BitmapFactory createBitmapFactory();
	CanvasFactory createCanvasFactory();
	int getScreenWidth();
	int getScreenHeight();
}
