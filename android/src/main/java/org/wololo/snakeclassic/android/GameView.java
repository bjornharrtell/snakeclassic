package org.wololo.snakeclassic.android;

import org.wololo.snakeclassic.core.Game;
import org.wololo.snakeclassic.vmlayer.BitmapFactory;
import org.wololo.snakeclassic.vmlayer.CanvasFactory;
import org.wololo.snakeclassic.vmlayer.VMContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,
		OnTouchListener, VMContext {

	public Game game;

	public Thread thread;

	public SurfaceHolder surfaceHolder;

	GameView(Context context) {
		super(context);

		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);

		game = new Game(this);

		setFocusable(true);
		setOnTouchListener(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		System.out.println("surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		System.out.println("surfaceCreated");

		thread = new Thread() {
			@Override
			public void run() {
				game.run();
			}
		};

		game.running = true;
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		System.out.println("surfaceDestroyed");
		boolean retry = true;
		game.running = false;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		System.out.println("onTouch");

		int action = e.getAction();

		if (action == MotionEvent.ACTION_CANCEL)
			return true;

		int historySize = e.getHistorySize();

		int p = 0;

		for (int h = 0; h < historySize; h++) {
			int x = (int) e.getHistoricalX(p, h);
			int y = (int) e.getHistoricalY(p, h);

			if (action == MotionEvent.ACTION_DOWN)
				doClick(x, y);
		}

		int x = (int) e.getX(p);
		int y = (int) e.getY(p);

		if (action == MotionEvent.ACTION_DOWN)
			doClick(x, y);

		// sleep 16 milliseconds to avoid too much input CPU processing
		// goal is to get slightly more input events than target FPS which is 60
		try {
			Thread.sleep(16L);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		return true;
	}

	void doClick(int x, int y) {
		if (game.paused) {
			game.unpause();
		} else {
			game.click(x, y);
		}
	}

	@Override
	public void render(Object bitmap) {
		synchronized (surfaceHolder) {
			Canvas canvas = null;
			try {
				canvas = surfaceHolder.lockCanvas(null);
				canvas.drawBitmap((Bitmap) bitmap, 0, 0, null);
			} finally {
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public BitmapFactory createBitmapFactory() {
		return new AndroidBitmapFactory();
	}

	@Override
	public CanvasFactory createCanvasFactory() {
		return new AndroidCanvasFactory();
	}

	@Override
	public int getScreenWidth() {
		return getWidth();
	}

	@Override
	public int getScreenHeight() {
		return getHeight();
	}
}
