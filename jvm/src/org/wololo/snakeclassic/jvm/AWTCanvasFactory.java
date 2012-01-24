package org.wololo.snakeclassic.jvm;

import java.awt.image.BufferedImage;

import org.wololo.snakeclassic.vmlayer.Canvas;
import org.wololo.snakeclassic.vmlayer.CanvasFactory;

public class AWTCanvasFactory implements CanvasFactory {
	public Canvas create(Object bitmap) {
		return new AWTCanvas(((BufferedImage) bitmap).getGraphics());
	}
}
