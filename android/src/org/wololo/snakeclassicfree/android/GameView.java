package org.wololo.snakeclassicfree.android;

import org.wololo.snakeclassic.core.Game;
import org.wololo.snakeclassic.vmlayer.BitmapFactory;
import org.wololo.snakeclassic.vmlayer.CanvasFactory;
import org.wololo.snakeclassic.vmlayer.VMContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,
		OnTouchListener, VMContext {

	Context context;

	public Game game;

	public Thread thread;

	public SurfaceHolder surfaceHolder;

	public static final String PREFS_NAME = "SnakeClassicPrefrences";

	int bottomOffset;
	
	GameView(Context context, int bottomOffset) {
		super(context);

		this.context = context;
		this.bottomOffset = bottomOffset;
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);

		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		int highscore = settings.getInt("highscore", 0);

		game = new Game(this, highscore, bottomOffset);

		setFocusable(true);
		setOnTouchListener(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
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
		game.click(x, y);
	}

	@Override
	public void render(Object bitmap) {
		synchronized (surfaceHolder) {
			Canvas canvas = null;
			try {
				canvas = surfaceHolder.lockCanvas(null);
				if (canvas != null)	canvas.drawBitmap((Bitmap) bitmap, 0, 0, null);
			} finally {
				if (canvas != null) surfaceHolder.unlockCanvasAndPost(canvas);
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

	public void saveHighscore() {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("highscore", game.highscore);
		editor.commit();
	}
}
