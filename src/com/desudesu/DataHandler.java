package com.desudesu;


import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataHandler {
	Context context;
	public DataHandler(Context context){
		this.context = context;
	}
	
	public Chan[] sortData(Chan data[]){
		//Preference loader
		SharedPreferences prefs = context.getSharedPreferences(
				"com.derped", Context.MODE_PRIVATE);

		//Sort chans
		int n = data.length;
		int j = 0;
		Chan temp[] = new Chan[n];
		for(int i=0; i<n; i++){
			if (prefs.getBoolean(data[i].getChanName(), false)){
				temp[j] = data[i];
				j++;
			}
		}
		for(int i=0; i<n; i++){
			if (!prefs.getBoolean(data[i].getChanName(), false)){
				temp[j] = data[i];
				j++;
			}
		}

		return temp;
	}

	public Board[] sortData(Board data[]){
		//Preference loader
		SharedPreferences prefs = context.getSharedPreferences(
				"com.derped", Context.MODE_PRIVATE);

		//Sort chans
		int n = data.length;
		int j = 0;
		Board temp[] = new Board[n];
		for(int i=0; i<n; i++){
			if (prefs.getBoolean(data[i].getBoardName(), false)){
				temp[j] = data[i];
				j++;
			}
		}
		for(int i=0; i<n; i++){
			if (! prefs.getBoolean(data[i].getBoardName(), false)){
				temp[j] = data[i];
				j++;
			}
		}
		return temp;
	}

	public Chan[] GetChanData(){
		//Standard chan data
		Chan chan_data[] = new Chan[] {
				new Chan(R.drawable.icon_4chan,"4chan",context.getResources().getString(R.string.chan_descr_4chan),context.getResources().getString(R.string.chan_URL_4chan)),
				new Chan(R.drawable.icon_7chan,"7chan",context.getResources().getString(R.string.chan_descr_7chan),context.getResources().getString(R.string.chan_URL_4chan)),
				new Chan(R.drawable.icon_420chan,"420chan",context.getResources().getString(R.string.chan_descr_420chan),context.getResources().getString(R.string.chan_URL_4chan))
		};

		return sortData(chan_data);

	}

	public Board[] GetBoardData(String sChan) {
		//TODO: Take all the text out of the code and into a XML
		if (sChan=="4chan"){
			Chan fourchan = new Chan(R.drawable.icon_4chan,"4chan",context.getResources().getString(R.string.chan_descr_4chan),context.getResources().getString(R.string.chan_URL_4chan));
			Board board_data[] = new Board[] {
					new Board(fourchan, "a", "Anime & Manga",""),
					new Board(fourchan, "b","Random",""),
					new Board(fourchan, "c","Anime/Cute",""),
					new Board(fourchan, "d","Hentai/Alternative",""),
					new Board(fourchan, "e","Ecchi",""),
					new Board(fourchan, "g","Technology",""),
					new Board(fourchan, "gif","Animated GIF",""),
					new Board(fourchan, "h","Hentai",""),
					new Board(fourchan, "hr","High Resolution",""),
					new Board(fourchan, "k","Weapons",""),
					new Board(fourchan, "m","Mecha",""),
					new Board(fourchan, "o","Auto",""),
					new Board(fourchan, "p","Photography",""),
					new Board(fourchan, "r","Request",""),
					new Board(fourchan, "s","Sexy Beautiful Women",""),
					new Board(fourchan, "t","Torrents",""),
					new Board(fourchan, "u","Yuri",""),
					new Board(fourchan, "v","Video Games",""),
					new Board(fourchan, "vg","Video Game Generals",""),
					new Board(fourchan, "w","Anime/Wallpapers",""),
					new Board(fourchan, "wg","Wallpapers/General",""),
					new Board(fourchan, "i","Oekaki",""),
					new Board(fourchan, "ic","Artwork/Critique",""),
					new Board(fourchan, "r9k","ROBOT9001",""),
					new Board(fourchan, "cm","Cute/Male",""),
					new Board(fourchan, "hm","Handsome Men",""),
					new Board(fourchan,"y","Yaoi",""),
					new Board(fourchan,"3","3DCG",""),
					new Board(fourchan,"adv","Advice",""),
					new Board(fourchan,"an","Animals & Nature",""),
					new Board(fourchan,"cgl","Cosplay & EGL",""),
					new Board(fourchan,"ck","Food & Cooking",""),
					new Board(fourchan,"co","Comics & Cartoons",""),
					new Board(fourchan,"diy","Do-It-Yourself",""),
					new Board(fourchan,"fa","Fashion",""),
					new Board(fourchan,"fit","Health & Fitness",""),
					new Board(fourchan,"hc","Hardcore",""),
					new Board(fourchan,"int","International",""),
					new Board(fourchan,"jp","Otaku Culture",""),
					new Board(fourchan,"lit","Literature",""),
					new Board(fourchan,"mlp","Pony",""),
					new Board(fourchan,"mu","Music",""),
					new Board(fourchan,"n","Transportation",""),
					new Board(fourchan,"po","Papercraft & Origami",""),
					new Board(fourchan,"pol","Politically Incorrect",""),
					new Board(fourchan,"sci","Science & Math",""),
					new Board(fourchan,"soc","Social",""),
					new Board(fourchan,"sp","Sports",""),
					new Board(fourchan,"tg","Traditional Games",""),
					new Board(fourchan,"toy","Toys",""),
					new Board(fourchan,"trv","Travel",""),
					new Board(fourchan,"tv","Television & Film",""),
					new Board(fourchan,"vp","Pokémon",""),
					new Board(fourchan,"wsg","Worksafe GIF",""),
					new Board(fourchan,"x","Paranormal","")
			};
			return sortData(board_data);
		} else if (sChan=="7chan"){
			Chan sevenchan = new Chan(R.drawable.icon_7chan,"7chan",context.getResources().getString(R.string.chan_descr_7chan),context.getResources().getString(R.string.chan_URL_4chan));
			Board board_data[] = new Board[] {
					new Board(sevenchan,"a","Anime & Manga",""), //TODO
					new Board(sevenchan, "b","Random","")

			};
			return sortData(board_data);
		} else if (sChan=="420chan") {

		}
		Chan nullchan = new Chan(R.drawable.icon_7chan,"NULL",context.getResources().getString(R.string.chan_descr_7chan),context.getResources().getString(R.string.chan_URL_4chan));
		Board null_data[] = new Board[] {
				new Board(nullchan, "null","Null Board",""),
		};
		return null_data;
	}
	
	public List<ChanThread> GetThreads(String sChan, String sBoard) {
		List<ChanThread> threadList = new ArrayList();
		Jsoup j = null;
		Document page = new Document("null");
		try {
			page = j.connect(GetBoardURL(sChan,sBoard)).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return threadList; //TODO Proper no network view
		}

		
		Elements content = page.getElementsByAttributeValue("class", "board");
		Elements threadContents = content.get(0).getElementsByAttributeValue("class", "thread");
		for (int i = 0; i < threadContents.size(); i++)
		{
			//Get ID
			Element currentThread = threadContents.get(i);
			int id = Integer.parseInt(currentThread.attr("id").replaceAll("[^\\d]", ""));
			//Get thread post
			Element imageThumb = currentThread.getElementsByAttributeValue("class", "file").get(0).getElementsByAttributeValue("class","fileThumb").get(0);
			String imageURL = imageThumb.getElementsByAttribute("src").get(0).attr("src");
			Element thread = currentThread.getElementsByAttributeValue("class", "postMessage").get(0);
			String threadPost = thread.html();
			threadList.add(new ChanThread(id, "Null", threadPost, "http:" + imageURL, "null"));
		}
		return threadList;
	}

	private String GetBoardURL(String sChan, String sBoard){
		Board temp[] = GetBoardData(sChan);
		for (int i = 0; i < temp.length; i++)
		{
			if (temp[i].getBoardName() == sBoard) {
				return temp[i].getBoardURL();
			}
		}
		return "error";
	}
	
	private boolean isOnline() {
	    ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}
