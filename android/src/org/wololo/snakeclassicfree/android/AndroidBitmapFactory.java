package org.wololo.snakeclassicfree.android;

import org.wololo.snakeclassic.vmlayer.BitmapFactory;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class AndroidBitmapFactory implements BitmapFactory{

	@Override
	public Object create(int width, int height, int bitmapType) {
		return Bitmap.createBitmap(width, height, parseBitmapType(bitmapType));
	}

	Config parseBitmapType(int bitmapType) {
		Config result = Bitmap.Config.RGB_565;
		switch (bitmapType) {
		case BitmapFactory.OPAGUE:
			result = Bitmap.Config.RGB_565;
		case BitmapFactory.BITMASK:
			result = Bitmap.Config.ALPHA_8;
		case BitmapFactory.TRANSLUCENT:
			result = Bitmap.Config.ARGB_8888;
		}

		return result;
	}
}
