package com.desudesu;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		return new Chan(R.drawable.icon_4chan,c.getString(0),c.getString(2),c.getString(3));
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
		return new Board(new Chan(R.drawable.icon_4chan,c.getString(0),c.getString(2),c.getString(3)),c.getString(4),c.getString(5),c.getString(6),favourite);
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
			chan_data[i] = new Chan(R.drawable.icon_4chan,c.getString(0),c.getString(2),c.getString(3));
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
			board_data[i] = new Board(new Chan(R.drawable.icon_4chan,c.getString(0),c.getString(2),c.getString(3)),c.getString(4),c.getString(5),c.getString(6),favourite);
			c.moveToNext();
		}
		return board_data;
	}

	public List<ChanThread> GetChanThreadDataByNames(String sChan, String sBoard) {
		List<ChanThread> threadList = new ArrayList<ChanThread>();
//		Board board = GetBoardByNames(sChan,sBoard);
//		Jsoup j = null;
//		Document page = new Document("null");
//		try {
//			page = j.connect(board.getBoardURL()).get();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return threadList; //TODO Proper no network view
//		}
//
//		Elements content = page.getElementsByAttributeValue("class", "board");
//		Elements threadContents = content.get(0).getElementsByAttributeValue("class", "thread");
//		for (int i = 0; i < threadContents.size(); i++)
//		{
//			//Get ID
//			Element currentThread = threadContents.get(i);
//			int id = Integer.parseInt(currentThread.attr("id").replaceAll("[^\\d]", ""));
//			//Get thread post
//			Element imageThumb = currentThread.getElementsByAttributeValue("class", "file").get(0).getElementsByAttributeValue("class","fileThumb").get(0);
//			String imageURL = imageThumb.getElementsByAttribute("src").get(0).attr("src");
//			Element thread = currentThread.getElementsByAttributeValue("class", "postMessage").get(0);
//			String threadPost = thread.html();
//			threadList.add(new ChanThread(board, id, "Null", threadPost, "http:" + imageURL, "null")); //TODO: Get names - replace NULL. Also fullURL.
//		}
//		Log.w("test","lol" + threadList.size());
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
		String table = "SELECT\n" +
				"Chans.ChanName,\n" +
				"Chans.ChanIcon,\n" +
				"Chans.ChanDescription,\n" +
				"Chans.ChanURL,\n" +
				"Boards.BoardLetter,\n" +
				"Boards.BoardName,\n" +
				"Boards.BoardDescription\n" +
				"FROM\n" +
				"Boards ,\n" +
				"Chans\n" +
				"WHERE\n" +
				"Boards.OnChan = Chans.ChanID AND\n" +
				"Chans.ChanName = '" + sChan + "'";
		ContentValues cv = new ContentValues();
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
			favBoards.add(new Board(new Chan(R.drawable.icon_4chan,c.getString(0),c.getString(2),c.getString(3)),c.getString(4),c.getString(5),c.getString(6),favourite));
			c.moveToNext();
		}
		return favBoards;
	}

	private Object GetChanThreadByNames(String sChan, String sBoard,
			int iThreadId) {
		//TODO: Redo this
		
		return null;
	}

	private int StringToInt(String sInt){
		int val = 0;
		char[] caInt = sInt.toCharArray();
		char[] digits = {'0','1','2','3','4','5','6','7','8','9'};
		for (int i = 0; i < caInt.length; i++){
			val = val * 10;
			for (int j = 0; j < 10; j++){
				if (digits[j]==caInt[i]){
					val = val + j;
				}
			}
		}
		return val;
	}
}
