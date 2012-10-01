package com.desudesu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ExpandableListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;

public class ContentListFragment extends ExpandableListFragment {
	private int currentLevel = 0;
	private String currentChan;
	private String currentBoard;
	private int currentThread;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setLevel(currentLevel,null);
	}


	public boolean upLevel(){
		//Goes up one level in terms of the hierarchy shown in the content list view
		DataHandler dh = new DataHandler(getActivity());
		switch(currentLevel){
		case 0: //If at chan level return true, so back button isn't used up by this action.
			return false;
		case 1: //If at board level go to Chan level
			setLevel(0,null);
			return true;
		case 2: //If at thread level go to board level
			setLevel(1,dh.GetChanByName(currentChan));
			return true;
		}
		return false;
	}

	@Override
	public void OnGroupClick(int groupPosition, Object data){
		//Change level on item click
		switch(currentLevel){
		case 0:
			setLevel(1,(Chan) data);
			return;
		case 1:
			setLevel(2,(Board) data);
			return;
		case 2:
			//setLevel(3,((ChanThread) data).getChanName(),((ChanThread) data).getBoardName(),((ChanThread) data).get
			return;
		}
	};

	public void setLevel(final int level, final Object data){
		class setLevelThread extends AsyncTask<Void,Void,ExpandableListAdapter> {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}


			@Override
			protected ExpandableListAdapter doInBackground(Void... params) {
				//Get the list data
				//TODO: Animate progressbar
				DataHandler dh = new DataHandler(getActivity());
				switch(level){
				case 0:
					currentLevel = 0;
					return new ChanAdapter(getActivity(),dh.GetChanData());
				case 1:
					currentLevel = 1;
					currentChan = ((Chan) data).getChanName();
					return new BoardAdapter(getActivity(),dh.GetBoardData((Chan) data));
				case 2:
					currentChan = ((Board) data).getChanName();
					currentBoard = ((Board) data).getBoardName();
					currentLevel = 2;
					return new ChanThreadAdapter(getActivity(), dh.GetChanThreadData((Board) data));
				} //TODO: Add extra case for viewing archived threads
				return null;
			}

			@Override
			protected void onPostExecute(ExpandableListAdapter result) {
				//Put the data in ListFragment
				super.onPostExecute(result);
				setListAdapter(result);
			}

		}
		setLevelThread thread = new setLevelThread();
		thread.execute();
	}


}