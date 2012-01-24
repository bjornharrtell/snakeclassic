package org.wololo.snakeclassic.jvm;

import java.awt.Color;
import java.awt.Graphics;

import org.wololo.snakeclassic.vmlayer.Canvas;

public class AWTCanvas implements Canvas {
	Graphics graphics;

	AWTCanvas(Graphics graphics) {
		this.graphics = graphics;
	}
	
	@Override
	public void clearRect(int arg0, int arg1, int arg2, int arg3) {
		graphics.setColor(Color.BLACK);
		graphics.clearRect(arg0, arg1, arg2, arg3);
	}

	@Override
	public void fillRect(int arg0, int arg1, int arg2, int arg3) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public void drawRect(int x, int y, int w, int h) {
		graphics.setColor(Color.WHITE);
		graphics.drawRect(x, y, w, h);
	}
	
	@Override
	public void drawTextCenter(int x, int y, int size, String text) {
		graphics.setFont(graphics.getFont().deriveFont(size));
		graphics.drawString(text, x, y);
	}

	@Override
	public void drawTextRight(int x, int y, int size, String text) {
		drawTextCenter(x,y,size,text);
	}

	@Override
	public void drawTextLeft(int x, int y, int size, String text) {
		drawTextCenter(x,y,size,text);
	}
}
