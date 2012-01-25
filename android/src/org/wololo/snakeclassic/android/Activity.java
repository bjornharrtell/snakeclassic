package org.wololo.snakeclassic.android;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class Activity extends android.app.Activity {
	
	GameView gameView;
	
	AdView adView;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout relativeLayout = new RelativeLayout(this);
		
		adView = new AdView(this, AdSize.BANNER, "a14f1f0f8853916");
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adView.setLayoutParams(layoutParams);

		AdRequest adRequest = new AdRequest();

		adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		adRequest.addTestDevice("43423531314B4A554155");
		adRequest.addTestDevice("63A85C2F9D4A2A9FBB46DE225254592A");
		adView.loadAd(adRequest);
		
		final DisplayMetrics dm = getResources().getDisplayMetrics();
		
		gameView = new GameView(this, (int) (50 * dm.density)+1);
		
		relativeLayout.addView(gameView);
		relativeLayout.addView(adView);
		
		setContentView(relativeLayout);
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
	
	@Override
	  public void onDestroy() {
	    adView.destroy();
	    super.onDestroy();
	  }
}
