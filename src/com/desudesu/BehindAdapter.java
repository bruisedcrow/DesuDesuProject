package com.desudesu;

import java.util.ArrayList;
import java.util.List;

import com.desudesu.ChanThreadAdapter.ThreadHolder;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class BehindAdapter extends ArrayAdapter<Object> {

	Context context;
	int layoutResourceId;
	List<Object> objects;
	int iNumFavBoards;
	int iNumWatThreads;
	//TODO: Make separator holder class
	public BehindAdapter(Context context, int layoutResourceId,
			List<Board> favBoards, List<ChanThread> watThreads) {
		super(context, layoutResourceId);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.objects = new ArrayList<Object>();
		this.iNumFavBoards = favBoards.size();
		this.iNumWatThreads = watThreads.size();
		//TODO: Add separator here (Favourite Boards)
		for (int i = 0; i<iNumFavBoards; i++ ){
			this.objects.add(favBoards.get(i));
		}
		//TODO: Add separator here (Watched Threads)
		for (int i = 0; i <iNumWatThreads; i ++){
			this.objects.add(watThreads.get(i));
		}
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
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
		super.add(object);
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (position < iNumFavBoards) {
			return 0;
			
		} else {
			return 1;
		}
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Object data = objects.get(position);
		if (data instanceof ChanThread) {
			//If the object is a thread then this is a watched thread
			WatThreadHolder holder = null;
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.item_behind_watthread, null);

				holder = new WatThreadHolder();
				holder.imgTheadImg = (ImageView)convertView.findViewById(R.id.imgBehindWatThreadImg);
				holder.txtPost = (TextView)convertView.findViewById(R.id.txtBehindWatThreadPost);
				holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgBehindWatThreadIcon);
				convertView.setTag(holder);
			} else {
				holder = (WatThreadHolder)convertView.getTag();
			}
			//Setup content

			Html HT = null;
			holder.txtPost.setText(HT.fromHtml(((ChanThread) data).getPost()));
			holder.imgIcon.setImageResource(((ChanThread) data).getChanIcon());
			//UrlImageViewHelper.setUrlDrawable(holder.imgTheadImg, tTemp.getThumbUrl());
			return convertView;
		} else if (data instanceof Board) {

			//If the object is a board then this is a favourite board
			FavBoardHolder holder = null;
			if(convertView == null)
			{
				LayoutInflater inflater = ((Activity)context).getLayoutInflater();
				convertView = inflater.inflate(layoutResourceId, parent, false);

				holder = new FavBoardHolder();
				holder.imgIcon = (ImageView)convertView.findViewById(R.id.imgIcon);
				holder.txtName = (TextView)convertView.findViewById(R.id.txtChanName);
				holder.sChan = ((Board) data).getChanName();
				holder.sBoard = ((Board) data).getBoardName();
				convertView.setTag(holder);
			} else {
				holder = (FavBoardHolder) convertView.getTag();
				holder.sChan = ((Board) data).getChanName();
				holder.sBoard = ((Board) data).getBoardName();
				convertView.setTag(holder);
			}
			holder.txtName.setText(((Board) data).getBoardName());
			holder.imgIcon.setImageResource(((Board) data).getChanIcon());
			return convertView;
		} //else if (data instanceof Separator)
		return convertView;
	}
	static class FavBoardHolder
	{
		String sChan;
		String sBoard;
		ImageView imgIcon;
		TextView txtName;
	}
	static class WatThreadHolder
	{
		
		ImageView imgIcon;
		ImageView imgTheadImg;
		TextView txtPost;
	}
}
