package org.wololo.snakeclassic.android;

import org.wololo.snakeclassic.vmlayer.Canvas;
import org.wololo.snakeclassic.vmlayer.CanvasFactory;

import android.graphics.Bitmap;

public class AndroidCanvasFactory implements CanvasFactory {

	@Override
	public Canvas create(Object bitmap) {
		return new AndroidCanvas((Bitmap)bitmap);
	}

}
