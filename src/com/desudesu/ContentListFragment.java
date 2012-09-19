package com.desudesu;

import android.os.Bundle;
import android.support.v4.app.ExpandableListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;

public class ContentListFragment extends ExpandableListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		DataHandler dh = new DataHandler(getActivity());

		ExpandableListAdapter mAdapter = new ChanAdapter(getActivity(), dh.GetChanData());
		//ExpandableListAdapter mAdapter = new BoardAdapter(getActivity(), dh.GetBoardData("4chan"));
		setListAdapter(mAdapter);
	}
	
	
}