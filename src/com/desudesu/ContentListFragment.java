package com.desudesu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ExpandableListFragment;
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
		setLevel(new String[]{});
	}


	public boolean upLevel(){
		//Goes up one level in terms of the hierarchy shown in the content list view
		DataHandler dh = new DataHandler(getActivity());
		switch(currentLevel){
		case 0: //If at chan level return true, so back button isn't used up by this action.
			return false;
		case 1: //If at board level go to Chan level
			setLevel(new String[]{});
			return true;
		case 2: //If at thread level go to board level
			setLevel(new String[]{currentChan});
			return true;
		}
		return false;
	}

	@Override
	public void OnGroupClick(int groupPosition, String[] hierarchyNames){
		//Change level on item click
		setLevel(hierarchyNames);
	};

	public void setLevel(final String[] hierarchyNames){
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
				switch(hierarchyNames.length){
				case 0:
					currentLevel = 0;
					return new ChanAdapter(getActivity(),dh.GetChanData());
				case 1:
					currentLevel = 1;
					currentChan = hierarchyNames[0];
					return new BoardAdapter(getActivity(),dh.GetBoardDataByName(currentChan));
				case 2:
					currentChan = hierarchyNames[0];
					currentBoard = hierarchyNames[1];
					currentLevel = 2;
					return new ChanThreadAdapter(getActivity(), dh.GetChanThreadDataByNames(currentChan, currentBoard));
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