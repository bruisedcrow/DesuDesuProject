package com.desudesu;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataHandler {
	Context context;
	StaticData staticdata;

	public DataHandler(Context context){
		this.context = context;
		staticdata = new StaticData(context);
	}

	public Board[] sortData(Board data[]){
		//Preference loader
		SharedPreferences prefs = context.getSharedPreferences(
				"com.desudesu", Context.MODE_PRIVATE);

		//Sort chans
		int n = data.length;
		int j = 0;
		Board temp[] = new Board[n];
		for(int i=0; i<n; i++){
			if (prefs.getBoolean(data[i].getBoardUniqueName(), false)){
				temp[j] = data[i];
				j++;
			}
		}
		for(int i=0; i<n; i++){
			if (! prefs.getBoolean(data[i].getBoardUniqueName(), false)){
				temp[j] = data[i];
				j++;
			}
		}
		return temp;
	}

	public Chan GetChanByName(String sChan){
		if (sChan == "4chan"){
			return staticdata.fourchan;
		} else if (sChan == "7chan") {
			return staticdata.sevenchan;
		} else if (sChan == "420chan"){
			return staticdata.fourtwozerochan;
		}
		return staticdata.nullchan;
	}

	public Board GetBoardByNames(String sChan, String sBoard){
		if ("4chan".equals(sChan)){ //WHAT
			for (int i = 0; i < staticdata.fourchanBoards.length; i++){
				if (staticdata.fourchanBoards[i].getBoardName().equals(sBoard)){
					return staticdata.fourchanBoards[i];
				}
			}

		} else if ("7chan".equals(sChan)) {
			for (int i = 0; i < staticdata.sevenchanBoards.length; i++){
				if (staticdata.sevenchanBoards[i].getBoardName().equals(sBoard)){
					return staticdata.sevenchanBoards[i];
				}
			}
		} else if ("420chan".equals(sChan)){
			for (int i = 0; i < staticdata.fourtwozerochanBoards.length; i++){
				if (staticdata.fourtwozerochanBoards[i].getBoardName().equals(sBoard)){
					return staticdata.fourtwozerochanBoards[i];
				}
			}
		}
		return staticdata.nullchanBoards[0];
	}

	public Chan[] GetChanData(){
		//Standard chan data
		Chan chan_data[] = new Chan[] {staticdata.fourchan,staticdata.sevenchan,staticdata.fourtwozerochan};
		return chan_data;

	}

	public Board[] GetBoardDataByName(String sChan) {
		//TODO: Take all the text out of the code and into a XML
		if (sChan=="4chan"){
			return sortData(staticdata.fourchanBoards);
		} else if (sChan=="7chan"){
			return sortData(staticdata.sevenchanBoards);
		} else if (sChan=="420chan") {

		}
		return staticdata.nullchanBoards;
	}

	public Board[] GetBoardData(Chan chan) {
		//TODO: Take all the text out of the code and into a XML
		String sChan = chan.getChanName();
		if (sChan=="4chan"){
			return sortData(staticdata.fourchanBoards);
		} else if (sChan=="7chan"){
			return sortData(staticdata.sevenchanBoards);
		} else if (sChan=="420chan") {
			//TODO: Add the rest
		}
		return staticdata.nullchanBoards;
	}

	public List<ChanThread> GetChanThreadData(Board board) {
		List<ChanThread> threadList = new ArrayList<ChanThread>();
		ChanThread example1 = new ChanThread(board, 0, "newfag", "lol im so randum haha." + " I'm new here - what do u do at " + board.getBoardName(), null, null);
		ChanThread example2 = new ChanThread(board, 1, "oldfag", "This is shitty - " +board.getChanName() + " was never good, especially " + board.getBoardName(), null, null);
		threadList.add(example1);
		threadList.add(example2);
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

	public List<ChanThread> GetWatThreads() {
		SharedPreferences prefs = context.getSharedPreferences(
				"com.desudesu.watchedthreads", Context.MODE_PRIVATE);
		List<ChanThread> watThreads = new ArrayList<ChanThread>();
		Set<String> keys = prefs.getAll().keySet();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()){
			String key = iterator.next();
			if (prefs.getBoolean(key, false)) {
				watThreads.add((ChanThread) GetByUniqueName(key));
			}
		}
		for (int i = 0; i < watThreads.size(); i++){
			Log.w("test","lol - Board Name: "+((ChanThread) watThreads.get(i)).getBoardName());
		}
		return watThreads;
	}
	public List<Board> GetFavBoards(){
		SharedPreferences prefs = context.getSharedPreferences(
				"com.desudesu", Context.MODE_PRIVATE);
		List<Board> favBoards = new ArrayList<Board>();
		Board[] temp;
		//Check boards
		temp = GetBoardDataByName("4chan");
		for (int i=0; i < temp.length; i++){
			if (prefs.getBoolean(temp[i].getBoardUniqueName(), false)) {
				favBoards.add(temp[i]);
			}
		}
		temp = GetBoardDataByName("7chan");
		for (int i=0; i < temp.length; i++){
			if (prefs.getBoolean(temp[i].getBoardUniqueName(), false)) {
				favBoards.add(temp[i]);
			}
		} //TODO: 420chan
		return favBoards;
	}
	public Object GetByUniqueName(String uniquename){
		List<String> parts = new ArrayList<String>();
		char[] caLetters = uniquename.toCharArray();
		int start = 0;
		for (int i = 0; i < caLetters.length; i ++){
			if (caLetters[i]== '/' && caLetters[i+1]== '/'){
				parts.add(String.copyValueOf(caLetters,start,i- start));
				start = i + 2;
			}
		}
		parts.add(String.copyValueOf(caLetters,start,caLetters.length - start));
		switch (parts.size()){
		case 1:
		{
			return GetChanByName(parts.get(0));
		}
		case 2:
		{
			return GetBoardByNames(parts.get(0), parts.get(1));

		} case 3:
			return GetChanThreadByNames(parts.get(0),parts.get(1),StringToInt(parts.get(2)));
		}
		return null;
	}

	private Object GetChanThreadByNames(String sChan, String sBoard,
			int iThreadId) {
		//TODO: Redo this
		List<ChanThread> potentialThreads = GetChanThreadData(GetBoardByNames(sChan, sBoard));
		for (int i = 0; i < potentialThreads.size(); i++) {
			if (potentialThreads.get(i).getId() == iThreadId){
				return potentialThreads.get(i);
			}
		}
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
