package com.desudesu;

import java.util.ArrayList;
import java.util.List;

import com.slidingmenu.lib.SlidingMenu.OnOpenListener;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;

public class Main extends BaseActivity {
	//Expandable list fragment that switches between the chan, boards and threads data
	ContentListFragment cContent;
	BehindAdapter bAdapter;
	List<Board> favBoards; //Really should be List<Board> but java is lame
	//List<ThreadChan> watchThreads;

	public Main() {
		super(R.string.content_title);
	}

	private void updateBehind(){
		//May want to put this in an AsyncTask
		DataHandler dh = new DataHandler(this);
		favBoards = dh.GetFavBoards(); //May want to cache these using favBoards, then check whether they've changed to improve performance
		mFrag.setListAdapter(new BehindAdapter(this,R.layout.item_behind_favchan,favBoards));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		favBoards = new ArrayList<Board>();
		mFrag = new ListFragment();
		t.replace(R.id.menu_frame, mFrag);
		t.commit();
		updateBehind();
		getSlidingMenu().setOnOpenListener(new OnOpenListener(){
			public void onOpen() {
				//TODO: Put this in a AsyncTask to increase performance
				updateBehind();
			}
		});

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
