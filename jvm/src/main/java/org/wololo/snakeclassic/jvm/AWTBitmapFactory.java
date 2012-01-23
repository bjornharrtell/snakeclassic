package org.wololo.snakeclassic.jvm;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;

import org.wololo.snakeclassic.vmlayer.BitmapFactory;

public class AWTBitmapFactory implements BitmapFactory {
	static final GraphicsEnvironment ge = GraphicsEnvironment
			.getLocalGraphicsEnvironment();
	static final GraphicsDevice gs = ge.getDefaultScreenDevice();
	static final GraphicsConfiguration gc = gs.getDefaultConfiguration();

	public Object create(int width, int height, int bitmapType) {
		return gc.createCompatibleImage(width, height, parseBitmapType(bitmapType));
	}

	int parseBitmapType(int bitmapType) {
		int result = Transparency.OPAQUE;
		switch (bitmapType) {
		case BitmapFactory.OPAGUE:
			result = Transparency.OPAQUE;
		case BitmapFactory.BITMASK:
			result = Transparency.BITMASK;
		case BitmapFactory.TRANSLUCENT:
			result = Transparency.TRANSLUCENT;
		}

		return result;
	}
}
