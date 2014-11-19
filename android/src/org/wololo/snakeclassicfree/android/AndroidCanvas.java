package org.wololo.snakeclassicfree.android;

import org.wololo.snakeclassic.vmlayer.Canvas;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;

public class AndroidCanvas implements Canvas {

	Bitmap bitmap;
	android.graphics.Canvas canvas;
	Paint whitePaint = new Paint();
	Paint blackPaint = new Paint();
	
	Paint rectPaint = new Paint();
	Paint fontPaint = new Paint();
	Paint fontRightPaint = new Paint();
	Paint fontLeftPaint = new Paint();
	
	int sizeCacheCenter = 0;
	int sizeCacheRight = 0;
	int sizeCacheLeft = 0;
	
	AndroidCanvas(Bitmap bitmap) {
		this.canvas = new android.graphics.Canvas(bitmap);
		
		whitePaint.setColor(Color.WHITE);
		whitePaint.setStyle(Style.FILL);
		//whitePaint.setStrokeWidth(1);
		
		blackPaint.setColor(Color.BLACK);
		blackPaint.setStyle(Style.FILL);
		//blackPaint.setStrokeWidth(1);
		
		rectPaint.setColor(Color.WHITE);
		rectPaint.setStyle(Style.STROKE);
		rectPaint.setStrokeWidth(1);
		
		fontPaint.setColor(Color.WHITE);
		fontPaint.setStyle(Style.STROKE);
		fontPaint.setTextAlign(Align.CENTER);
		fontPaint.setSubpixelText(true);
		fontPaint.setAntiAlias(true);

		fontRightPaint.set(fontPaint);
		fontRightPaint.setTextAlign(Align.RIGHT);
		
		fontLeftPaint.set(fontPaint);
		fontLeftPaint.setTextAlign(Align.LEFT);
	}
	
	@Override
	public void clearRect(int x, int y, int w, int h) {
		canvas.drawRect(x, y, x+w, y+h, blackPaint);
	}

	@Override
	public void fillRect(int x, int y, int w, int h) {
		canvas.drawRect(x, y, x+w, y+h, whitePaint);
	}
	
	@Override
	public void drawRect(int x, int y, int w, int h) {
		canvas.drawRect(x, y, x+w, y+h, rectPaint);
	}
	
	@Override
	public void drawTextCenter(int x, int y, int size, String text) {
		if (sizeCacheCenter != size) {
			fontPaint.setTextSize(size);
			sizeCacheCenter = size;
		}
		canvas.drawText(text, x, y, fontPaint);
	}
	
	@Override
	public void drawTextLeft(int x, int y, int size, String text) {
		if (sizeCacheLeft != size) {
			fontPaint.setTextSize(size);
			sizeCacheLeft = size;
		}
		canvas.drawText(text, x, y, fontLeftPaint);
	}
	
	@Override
	public void drawTextRight(int x, int y, int size, String text) {
		if (sizeCacheRight != size) {
			fontPaint.setTextSize(size);
			sizeCacheRight = size;
		}
		canvas.drawText(text, x, y, fontRightPaint);
	}
}
