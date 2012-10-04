package com.desudesu;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
		return new Chan(GetDrawableIDByName(c.getString(1)),c.getString(0),c.getString(2),c.getString(3));
	}

	public Board GetBoardByNames(String sChan, String sBoard){
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
				"Chans.ChanName = '" + sChan + "'", null);
		c.moveToFirst();
		boolean favourite;
		if (c.getInt(7) == 1){
			favourite = true;
		} else {
			favourite = false;
		}
		return new Board(new Chan(GetDrawableIDByName(c.getString(1)),c.getString(0),c.getString(2),c.getString(3)),c.getString(4),c.getString(5),c.getString(6),favourite);
	}

	public Chan[] GetChanData(){
		
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
		return chan_data;
	}

	public Board[] GetBoardDataByName(String sChan) {
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
		return board_data;
	}

	public List<ChanThread> GetChanThreadDataByNames(String sChan, String sBoard) {
		List<ChanThread> threadList = new ArrayList<ChanThread>();
		return threadList;
	}

	private boolean isOnline() {
		ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
	public void SetBoardFav(String sChan, String sBoard, boolean bFav) {
		SQLiteDatabase sqlDB = myDbHelper.getReadableDatabase();
		if (!bFav){
			sqlDB.execSQL("UPDATE Boards SET Favorited = 1 WHERE EXISTS (SELECT * FROM Chans WHERE Boards.OnChan = Chans.ChanID AND Chans.ChanName ='" + sChan + "') AND Boards.BoardName = '" + sBoard + "'");
		} else {
			sqlDB.execSQL("UPDATE Boards SET Favorited = 0 WHERE EXISTS (SELECT * FROM Chans WHERE Boards.OnChan = Chans.ChanID AND Chans.ChanName ='" + sChan + "') AND Boards.BoardName = '" + sBoard + "'");
		}
		
	}

	public List<ChanThread> GetWatThreads() {
		List<ChanThread> watThreads = new ArrayList<ChanThread>();
		return watThreads;
	}
	public List<Board> GetFavBoards(){
		//Use query
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
		return favBoards;
	}

	private Object GetChanThreadByNames(String sChan, String sBoard,
			int iThreadId) {
		//TODO: DO THIS
		
		return null;
	}
	
	private int GetDrawableIDByName(String sDrawable){
		return context.getResources().getIdentifier(sDrawable,"drawable",context.getPackageName());
	}
}
