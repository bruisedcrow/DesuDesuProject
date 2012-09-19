package com.desudesu;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;

public class Main extends BaseActivity {
	public Main() {
		super(R.string.content_title);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ContentListFragment cContent = new ContentListFragment();
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, cContent)
		.commit();
		
		setSlidingActionBarEnabled(true);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_MENU) {
	        this.getSlidingMenu().showBehind();
	    }
		return super.onKeyUp(keyCode, event);
	}
	
}
