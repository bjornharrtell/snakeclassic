package org.wololo.snakeclassic.android;

import android.os.Bundle;

public class Activity extends android.app.Activity {

	GameView gameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("onCreate");
		
		super.onCreate(savedInstanceState);
		
		gameView = new GameView(this);

		setContentView(gameView);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		gameView.game.pause();
	}
}
