package org.wololo.snakeclassic.android;

import android.os.Bundle;

public class Activity extends android.app.Activity {
	
	GameView gameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gameView = new GameView(this);

		setContentView(gameView);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		gameView.game.pause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		gameView.saveHighscore();
	}
}
