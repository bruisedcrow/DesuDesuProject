package com.desudesu;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class ChanThreadAdapter extends BaseExpandableListAdapter{

	private Context context;
	private List<ChanThread> data;
	public ChanThreadAdapter(Context context, List<ChanThread> data) {
		this.context = context;
		this.data = data;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {
		//ThreadHolder tHolder = null;
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//convertView = infalInflater.inflate(R.layout., null);
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
		return data.get(groupPosition);
	}

	public int getGroupCount() {
		return data.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// Return a group view. You can load your custom layout here.
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
			ViewGroup parent) {
		ThreadHolder holder = null;
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.item_thread_closed, null);
			
			holder = new ThreadHolder();
			holder.imgTheadImg = (SmartImageView)convertView.findViewById(R.id.imgThreadImg);
			holder.txtName = (TextView)convertView.findViewById(R.id.txtThreadName);
			holder.txtPost = (TextView)convertView.findViewById(R.id.txtThreadPost);
			holder.bFavourite = (ImageButton)convertView.findViewById(R.id.bThreadFav);
			convertView.setTag(holder);
		} else {
			holder = (ThreadHolder)convertView.getTag();
		}
		//Setup content
		ChanThread tTemp = data.get(groupPosition);
		int sId = tTemp.getOPId();

		holder.txtName.setText(tTemp.getOPName());
		Html HT = null;
		holder.txtPost.setText(HT.fromHtml(tTemp.getOPPost()));
		
		Log.w("test","lol: "+ tTemp.getThumbUrl());
		holder.imgTheadImg.setImageUrl(tTemp.getThumbUrl());
		
		//TODO: REDO THIS USING SQL
		//Setup buttons
		String uniqueString = tTemp.getChanThreadUniqueName();
		holder.bFavourite.setTag(uniqueString);
		SharedPreferences prefs = context.getSharedPreferences(
				"com.desudesu.watchedthreads", Context.MODE_PRIVATE);
		
		if (prefs.getBoolean((String) uniqueString, false)){
			holder.bFavourite.setImageResource(R.drawable.icon_fav2);
		} else {
			holder.bFavourite.setImageResource(R.drawable.icon_fav);
		}
		holder.bFavourite.setOnClickListener(new OnClickListener() {  
			public void onClick(View v)
			{
				SharedPreferences prefs = context.getSharedPreferences(
						"com.desudesu.watchedthreads", Context.MODE_PRIVATE);
				String prefNameBool = (String) v.getTag();
				boolean current = prefs.getBoolean(prefNameBool, false);
				prefs.edit().putBoolean(prefNameBool, ! current).commit();
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
		return data.get(groupPosition).getChanDescription();
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	static class ThreadHolder
	{
		SmartImageView imgTheadImg;
		TextView txtName;
		TextView txtPost;
		ImageButton bFavourite;
	}
}
