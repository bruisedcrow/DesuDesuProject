package com.desudesu;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;

public class Main extends BaseActivity {
	//Expandable list fragment that switches between the chan, boards and threads data
	ContentListFragment cContent;
	
	public Main() {
		super(R.string.content_title);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		cContent = new ContentListFragment();
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
		switch (keyCode){
		//Menu button switches between behind and above view
		case KeyEvent.KEYCODE_MENU:
			if (getSlidingMenu().isBehindShowing()){
				getSlidingMenu().showAbove();
				return true;
			} else {
				getSlidingMenu().showBehind();
				return true;
			}
		case KeyEvent.KEYCODE_BACK:
			//Back button goes up the levels when above - Thread -> Board -> Chan
			if (getSlidingMenu().isBehindShowing()){
				//If above then do nothing
				return super.onKeyUp(keyCode, event);
			} else {
				if (cContent.upLevel() == false){
					//If level at Chan level then do not handle click
					return super.onKeyUp(keyCode, event);
				} else {
					//Handle click
					return true;
				}
			}
		}
		return super.onKeyUp(keyCode, event);
	}

}
