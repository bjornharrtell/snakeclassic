package org.wololo.snakeclassic.jvm;

import java.awt.Graphics;

import org.wololo.snakeclassic.vmlayer.Canvas;

public class AWTCanvas implements Canvas {
	Graphics graphics;

	AWTCanvas(Graphics graphics) {
		this.graphics = graphics;
	}
}
