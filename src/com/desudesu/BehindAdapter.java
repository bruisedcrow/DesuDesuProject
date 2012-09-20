package com.desudesu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BehindAdapter extends ArrayAdapter<Object> {

	Context context;
	int layoutResourceId;
	List<Object> objects;

	public BehindAdapter(Context context, int layoutResourceId,
			List<Board> favBoards) {
		super(context, layoutResourceId);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.objects = new ArrayList<Object>();
		for (int i = 0; i<favBoards.size(); i++ ){
			this.objects.add(favBoards.get(i));
		}
		
	}

	public BehindAdapter(Context context, int layoutResourceId) {
		super(context, layoutResourceId);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.objects = new ArrayList<Object>(); 
	}

	@Override
	public int getCount() {
		return objects.size();
	}
	
	

	@Override
	public void add(Object object) {
		Log.w("test","lol");
		super.add(object);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (objects.get(position) instanceof Board) {
			//If the object is a board then this is a favourited board
			FavBoardHolder holder = null;
			if(convertView == null)
			{
				LayoutInflater inflater = ((Activity)context).getLayoutInflater();
				convertView = inflater.inflate(layoutResourceId, parent, false);

				holder = new FavBoardHolder();
				holder.imgIcon = (ImageView)convertView.findViewById(R.id.imgIcon);
				holder.txtName = (TextView)convertView.findViewById(R.id.txtChanName);
				convertView.setTag(holder);
			} else {
				holder = (FavBoardHolder)convertView.getTag();
			}
			Board bTemp = (Board) objects.get(position);
			holder.txtName.setText(bTemp.getBoardName());
			holder.imgIcon.setImageResource(bTemp.getChanIcon());
			return convertView;
		} else if (objects.get(position) instanceof ChanThread) {
			//If the object is a thread then this is a watched thread

		}
		return convertView;
	}
	static class FavBoardHolder
	{
		ImageView imgIcon;
		TextView txtName;
	}
}
