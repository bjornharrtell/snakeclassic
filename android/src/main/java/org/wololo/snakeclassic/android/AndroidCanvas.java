package org.wololo.snakeclassic.android;

import org.wololo.snakeclassic.vmlayer.Canvas;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class AndroidCanvas implements Canvas {

	Bitmap bitmap;
	android.graphics.Canvas canvas;
	Paint whitePaint = new Paint();
	Paint blackPaint = new Paint();
	
	AndroidCanvas(Bitmap bitmap) {
		this.canvas = new android.graphics.Canvas(bitmap);
		
		whitePaint.setColor(Color.WHITE);
		whitePaint.setStyle(Style.FILL_AND_STROKE);
		whitePaint.setStrokeWidth(1);
		
		blackPaint.setColor(Color.BLACK);
	}
	
	@Override
	public void clearRect(int x, int y, int w, int h) {
		canvas.drawRect(x, y, x+w-1, y+h-1, blackPaint);
	}

	@Override
	public void fillRect(int x, int y, int w, int h) {
		canvas.drawRect(x, y, x+w-1, y+h-1, whitePaint);
	}
}
