package org.wololo.snakeclassic.jvm;

import java.awt.Color;
import java.awt.Graphics;

import org.wololo.snakeclassic.vmlayer.Canvas;

public class AWTCanvas implements Canvas {
	Graphics graphics;

	AWTCanvas(Graphics graphics) {
		this.graphics = graphics;
	}
	
	public void clearRect(int arg0, int arg1, int arg2, int arg3) {
		graphics.setColor(Color.BLACK);
		graphics.clearRect(arg0, arg1, arg2, arg3);
	}

	@Override
	public void fillRect(int arg0, int arg1, int arg2, int arg3) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(arg0, arg1, arg2, arg3);
	}
}
