package com.desudesu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class BoardAdapter extends BaseExpandableListAdapter{

	private Context context;
	private Board[] data;
	public BoardAdapter(Context context, Board[] data) {
		this.context = context;
		DataHandler dh = new DataHandler(context);
		this.data = dh.sortData(data);
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {
		//ThreadHolder tHolder = null;
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.item_thread_closed, null);
//			txtDescription = (TextView) convertView.findViewById(R.id.txtChanDescription);
//			convertView.setTag(txtDescription);
		} else {
//			txtDescription = (TextView) convertView.getTag();
		}
//		txtDescription.setText(data[groupPosition].getChanDescription());
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		//TOOD: User defined in future
		return 3;
	}

	public Board getGroup(int groupPosition) {
		return data[groupPosition];
	}

	public int getGroupCount() {
		return data.length;
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// Return a group view. You can load your custom layout here.
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
			ViewGroup parent) {
		BoardHolder holder = null;
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.item_board_closed, null); //TODO: Tailor item_board_child instead of using item_board_closed
			
			holder = new BoardHolder();
			holder.imgIcon = (ImageView)convertView.findViewById(R.id.imgIcon);
			holder.txtName = (TextView)convertView.findViewById(R.id.txtBoardName);
			holder.bFavourite = (ImageButton)convertView.findViewById(R.id.bBoardFav);
			convertView.setTag(holder);
		} 		else
		{
			holder = (BoardHolder)convertView.getTag();
		}
		
		//Set up content
		Board cTemp = data[groupPosition];
		holder.txtName.setText(cTemp.getBoardName());

		//Setup buttons
		ViewInfoHolder currentInfo = new ViewInfoHolder();
		currentInfo.sBoard = cTemp.getBoardName();
		currentInfo.sChan = cTemp.getChanName();
		if (cTemp.isFavourite()){
			holder.bFavourite.setImageResource(R.drawable.icon_fav2);
			currentInfo.clicked = true;
			holder.bFavourite.setTag(currentInfo);
		} else {
			holder.bFavourite.setImageResource(R.drawable.icon_fav);
			currentInfo.clicked = false;
			holder.bFavourite.setTag(currentInfo);
		}
		holder.bFavourite.setOnClickListener(new OnClickListener() {  
			public void onClick(View v)
			{
				ViewInfoHolder oldInfo = (ViewInfoHolder) v.getTag();
				DataHandler dh = new DataHandler(context);
				//TODO: Put this in AsyncTask maybe?
				dh.SetBoardFav(oldInfo.sChan, oldInfo.sBoard, oldInfo.clicked);
				//Update current data
				data = dh.sortData(dh.GetBoardDataByName(oldInfo.sChan));
				notifyDataSetChanged();
			}
		});
		
		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}
	
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	public String getChild(int groupPosition, int childPosition) {
		return data[groupPosition].getChanDescription();
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	static class BoardHolder
	{
		ImageView imgIcon;
		TextView txtName;
		TextView txtDescriptions;
		ImageButton bFavourite;
	}
	static class ThreadHolder
	{
		ImageView imgIcon;
		TextView txtName;
		TextView txtDescriptions;
		ImageButton bFavourite;
	}
	
	static class ViewInfoHolder
	{
		String sChan;
		String sBoard;
		boolean clicked;
	}
}
