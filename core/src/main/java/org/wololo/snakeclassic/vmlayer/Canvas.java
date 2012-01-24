package org.wololo.snakeclassic.vmlayer;

public interface Canvas {
	void clearRect(int arg0, int arg1, int arg2, int arg3);
	void fillRect(int arg0, int arg1, int arg2, int arg3);
	void drawRect(int x, int y, int w, int h);
	void drawTextCenter(int x, int y, int size, String text);
	void drawTextRight(int x, int y, int size, String text);
	void drawTextLeft(int x, int y, int size, String text);
}
