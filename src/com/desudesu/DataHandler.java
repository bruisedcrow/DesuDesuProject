package com.desudesu;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataHandler {
	Context context;
	DataBaseHelper myDbHelper;

	public DataHandler(Context context){
		this.context = context;
		myDbHelper = new DataBaseHelper(context);
	}

	public Board[] sortData(Board data[]){
		//Sort chans
		int n = data.length;
		int j = 0;
		Board temp[] = new Board[n];
		for(int i=0; i<n; i++){
			if (data[i].isFavourite()){
				temp[j] = data[i];
				j++;
			}
		}
		for(int i=0; i<n; i++){
			if (!data[i].isFavourite()){
				temp[j] = data[i];
				j++;
			}
		}
		return temp;
	}

	public Chan GetChanByName(String sChan){
		myDbHelper.openDataBase();
		SQLiteDatabase sqlDB = myDbHelper.getReadableDatabase();
		Cursor c = sqlDB.rawQuery("SELECT\n" +
				"Chans.ChanName,\n" +
				"Chans.ChanIcon,\n" +
				"Chans.ChanDescription,\n" +
				"Chans.ChanURL\n" +
				"FROM\n" +
				"Chans\n" +
				"WHERE\n" +
				"Chans.ChanName = '"+ sChan + "'", null);
		Chan returnval = new Chan(GetDrawableIDByName(c.getString(1)),c.getString(0),c.getString(2),c.getString(3));
		myDbHelper.close();
		return returnval;
	}

	public Board GetBoardByNames(String sChan, String sBoard){
		myDbHelper.openDataBase();
		SQLiteDatabase sqlDB = myDbHelper.getReadableDatabase();
		Cursor c = sqlDB.rawQuery("SELECT\n" +
				"Chans.ChanName,\n" +
				"Chans.ChanIcon,\n" +
				"Chans.ChanDescription,\n" +
				"Chans.ChanURL,\n" +
				"Boards.BoardLetter,\n" +
				"Boards.BoardName,\n" +
				"Boards.BoardDescription,\n" +
				"Boards.Favorited\n" +
				"FROM\n" +
				"Chans ,\n" +
				"Boards\n" +
				"WHERE\n" +
				"Chans.ChanID = Boards.OnChan AND\n" +
				"Chans.ChanName = '" + sChan + "' AND\n" +
				"Boards.BoardName = '" + sBoard + "'", null);
		
		c.moveToFirst();
		boolean favourite;
		if (c.getInt(7) == 1){
			favourite = true;
		} else {
			favourite = false;
		}
		Board returnval = new Board(new Chan(GetDrawableIDByName(c.getString(1)),c.getString(0),c.getString(2),c.getString(3)),c.getString(4),c.getString(5),c.getString(6),favourite);
		myDbHelper.close();
		return returnval;
	}

	public Chan[] GetChanData(){
		myDbHelper.openDataBase();
		SQLiteDatabase sqlDB = myDbHelper.getReadableDatabase();
		Cursor c = sqlDB.rawQuery("SELECT\n" +
				"Chans.ChanName,\n" +
				"Chans.ChanIcon,\n" +
				"Chans.ChanDescription,\n" +
				"Chans.ChanURL\n" +
				"FROM\n" +
				"Chans", null);
		
		Chan[] chan_data = new Chan[c.getCount()];
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i ++){
			chan_data[i] = new Chan(GetDrawableIDByName(c.getString(1)),c.getString(0),c.getString(2),c.getString(3));
			c.moveToNext();
		}
		myDbHelper.close();
		return chan_data;
	}

	public Board[] GetBoardDataByName(String sChan) {
		myDbHelper.openDataBase();
		SQLiteDatabase sqlDB = myDbHelper.getReadableDatabase();
		Cursor c = sqlDB.rawQuery("SELECT\n" +
				"Chans.ChanName,\n" +
				"Chans.ChanIcon,\n" +
				"Chans.ChanDescription,\n" +
				"Chans.ChanURL,\n" +
				"Boards.BoardLetter,\n" +
				"Boards.BoardName,\n" +
				"Boards.BoardDescription,\n" +
				"Boards.Favorited\n" +
				"FROM\n" +
				"Boards ,\n" +
				"Chans\n" +
				"WHERE\n" +
				"Boards.OnChan = Chans.ChanID AND\n" +
				"Chans.ChanName = '" + sChan + "'", null);
		Board[] board_data = new Board[c.getCount()];
		c.moveToFirst();
		boolean favourite;
		for (int i = 0; i < c.getCount(); i ++){
			if (c.getInt(7) == 1){
				favourite = true;
			} else {
				favourite = false;
			}
			board_data[i] = new Board(new Chan(GetDrawableIDByName(c.getString(1)),c.getString(0),c.getString(2),c.getString(3)),c.getString(4),c.getString(5),c.getString(6),favourite);
			c.moveToNext();
		}
		myDbHelper.close();
		return board_data;
	}

	public List<ChanThread> GetChanThreadDataByNames(String sChan, String sBoard, int page) {

		List<ChanThread> threadList = new ArrayList<ChanThread>();
		Board board = GetBoardByNames(sChan,sBoard);
		NetworkHandler nh = new NetworkHandler();
		String json = nh.getBoardJson(board.getBoardLetter(), page);
		json = "[" + json + "]"; //Retarded moot omitts []...
		try {
			//Retarded moot nests for no reason - unesting here
			JSONArray jsonThreadArray = new JSONArray(json).getJSONObject(0).getJSONArray("threads");
			Log.e("test","Number of threads: " + jsonThreadArray.length());
			JSONObject jsonThreadObject;
			for (int i = 0; i < jsonThreadArray.length(); i++) {
				ChanThread tempChanThread = null;
				//Get thread into jsonObject
				jsonThreadObject = jsonThreadArray.getJSONObject(i);
				//Get posts into jsonArray
				JSONArray jsonPostArray = jsonThreadObject.getJSONArray("posts");
				Log.e("test","Number of posts in this thread: " + jsonPostArray.length());
				JSONObject jsonPostObject;
				for (int j = 0; j < jsonPostArray.length(); j++){
					
					jsonPostObject = jsonPostArray.getJSONObject(j);
					String post = "";
					//Try to get post
					try {
						post = jsonPostObject.getString("com");
					} catch (Exception e) {
						//No post
					}
					if (j == 0){
						//Thread
						tempChanThread = new ChanThread(board,Integer.parseInt(jsonPostObject.getString("no")),jsonPostObject.getString("name"),post,jsonPostObject.getString("tim"),jsonPostObject.getString("ext"));
					} else {
						//Posts in thread
					}
					
				}
				threadList.add(tempChanThread);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return threadList;
	}

	public void SetBoardFav(String sChan, String sBoard, boolean bFav) {
		myDbHelper.openDataBase();
		SQLiteDatabase sqlDB = myDbHelper.getReadableDatabase();
		if (!bFav){
			sqlDB.execSQL("UPDATE Boards SET Favorited = 1 WHERE EXISTS (SELECT * FROM Chans WHERE Boards.OnChan = Chans.ChanID AND Chans.ChanName ='" + sChan + "') AND Boards.BoardName = '" + sBoard + "'");
		} else {
			sqlDB.execSQL("UPDATE Boards SET Favorited = 0 WHERE EXISTS (SELECT * FROM Chans WHERE Boards.OnChan = Chans.ChanID AND Chans.ChanName ='" + sChan + "') AND Boards.BoardName = '" + sBoard + "'");
		}
		myDbHelper.close();
	}

	public List<ChanThread> GetWatThreads() {
		List<ChanThread> watThreads = new ArrayList<ChanThread>();
		return watThreads;
	}
	public List<Board> GetFavBoards(){
		//Use query
		myDbHelper.openDataBase();
		SQLiteDatabase sqlDB = myDbHelper.getReadableDatabase();
		Cursor c = sqlDB.rawQuery("SELECT\n" +
				"Chans.ChanName,\n" +
				"Chans.ChanIcon,\n" +
				"Chans.ChanDescription,\n" +
				"Chans.ChanURL,\n" +
				"Boards.BoardLetter,\n" +
				"Boards.BoardName,\n" +
				"Boards.BoardDescription,\n" +
				"Boards.Favorited\n" +
				"FROM\n" +
				"Boards ,\n" +
				"Chans\n" +
				"WHERE\n" +
				"Boards.OnChan = Chans.ChanID AND\n" +
				"Boards.Favorited = 1", null);
		List<Board> favBoards = new ArrayList<Board>();
		c.moveToFirst();
		boolean favourite;
		for (int i = 0; i < c.getCount(); i ++){
			if (c.getInt(7) == 1){
				favourite = true;
			} else {
				favourite = false;
			}
			favBoards.add(new Board(new Chan(GetDrawableIDByName(c.getString(1)),c.getString(0),c.getString(2),c.getString(3)),c.getString(4),c.getString(5),c.getString(6),favourite));
			c.moveToNext();
		}
		myDbHelper.close();
		return favBoards;
	}

	private int GetDrawableIDByName(String sDrawable){
		return context.getResources().getIdentifier(sDrawable,"drawable",context.getPackageName());
	}
}
