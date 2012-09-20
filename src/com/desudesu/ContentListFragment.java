package com.desudesu;

import android.os.Bundle;
import android.support.v4.app.ExpandableListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ListView;

public class ContentListFragment extends ExpandableListFragment {
	private ExpandableListAdapter mAdapter;
	private int currentLevel = 1;
	private String currentChan;
	private String currentBoard;
	private int currentThread;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		currentChan = "4chan";
		super.onActivityCreated(savedInstanceState);
		DataHandler dh = new DataHandler(getActivity());

		//ExpandableListAdapter mAdapter = new ChanAdapter(getActivity(), dh.GetChanData());
		mAdapter = new BoardAdapter(getActivity(), dh.GetBoardData(currentChan));
		setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO: Ideally this would work, but the ExpandListFragment is shitty so it doesn't
		// Hence we're handling everything through the adapter
		// Someone needs to rewrite ExpandListAdapter to make this work and make a onLongListItemClick too
		super.onListItemClick(l, v, position, id);
		Log.w("test","lol2");
	}

	public boolean upLevel(){
		//Goes up one level in terms of the hierarchy shown in the content list view
		DataHandler dh = new DataHandler(getActivity());
		switch(currentLevel){
		case 0: //If at chan level return true, so back isn't used.
			return false;
		case 1: //If at board level go to Chan level
			mAdapter = new ChanAdapter(getActivity(),dh.GetChanData(),this);
			setListAdapter(mAdapter);
			currentLevel = 0;
			return true;
		case 2: //If at thread level go to board level
			mAdapter = new BoardAdapter(getActivity(),dh.GetBoardData(currentChan));
			setListAdapter(mAdapter);
			currentLevel = 1;
			return true;
		}
		return false;
	}
	
	public void setLevel(int level, String sChan, String sBoard, int iBoard){
		DataHandler dh = new DataHandler(getActivity());
		switch(level){
		case 0:
			currentLevel = 0;
			mAdapter = new ChanAdapter(getActivity(),dh.GetChanData(),this);
			setListAdapter(mAdapter);
			return;
		case 1:
			currentLevel = 1;
			mAdapter = new BoardAdapter(getActivity(),dh.GetBoardData(sChan));
			setListAdapter(mAdapter);
			return;
		}
	}
	
}