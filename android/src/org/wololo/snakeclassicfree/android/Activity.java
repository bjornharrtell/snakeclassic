package org.wololo.snakeclassicfree.android;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Activity extends android.app.Activity {
	
	GameView gameView;
	
	AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RelativeLayout relativeLayout = new RelativeLayout(this);
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-5743994027716094/2074885041");
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adView.setLayoutParams(layoutParams);

		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

		//adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
		//adRequestBuilder.addTestDevice("43423531314B4A554155");
		//adRequestBuilder.addTestDevice("63A85C2F9D4A2A9FBB46DE225254592A");
		adRequestBuilder.addTestDevice("788E31F82926400BC2D98E7633646348");
		adView.loadAd(adRequestBuilder.build());
		
		final DisplayMetrics dm = getResources().getDisplayMetrics();
		
		gameView = new GameView(this, (int) (50 * dm.density)+1);
		
		relativeLayout.addView(gameView);
		relativeLayout.addView(adView);
		
		setContentView(relativeLayout);
	}
	
	@Override
	protected void onPause() {
		adView.pause();
		super.onPause();
		gameView.game.pause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		gameView.saveHighscore();
	}
	
	@Override
    public void onResume() {
        super.onResume();
        adView.resume();
    }
	
	public void onDestroy() {
		adView.destroy();
        super.onDestroy();
    }
}
