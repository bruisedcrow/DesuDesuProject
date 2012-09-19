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
			if (! prefs.getBoolean(data[i].getChanName(), false)){
				temp[j] = data[i];
				j++;
			}
		}
		for(int i=0; i<n; i++){
			if (prefs.getBoolean(data[i].getChanName(), false)){
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
			if (! prefs.getBoolean(data[i].getBoardName(), false)){
				temp[j] = data[i];
				j++;
			}
		}
		for(int i=0; i<n; i++){
			if (prefs.getBoolean(data[i].getBoardName(), false)){
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
					new Board(fourchan, "s","Sexy Beautiful Women","http://boards.4chan.org/s/"),
					new Board(fourchan, "t","Torrents","http://boards.4chan.org/t/"),
					new Board(fourchan, "u","Yuri","http://boards.4chan.org/u/"),
					new Board(fourchan, "v","Video Games","http://boards.4chan.org/v/"),
					new Board(fourchan, "vg","Video Game Generals","http://boards.4chan.org/vg/"),
					new Board(fourchan, "w","Anime/Wallpapers","http://boards.4chan.org/w/"),
					new Board(fourchan, "wg","Wallpapers/General","http://boards.4chan.org/wg/"),
					new Board(fourchan, "i","Oekaki","http://boards.4chan.org/i/"),
					new Board(fourchan, "ic","Artwork/Critique","http://boards.4chan.org/ic/"),
					new Board(fourchan, "r9k","ROBOT9001","http://boards.4chan.org/r9k/"),
					new Board(fourchan, "cm","Cute/Male","http://boards.4chan.org/cm/"),
					new Board(fourchan, "hm","Handsome Men","http://boards.4chan.org/hm/"),
					new Board(fourchan,"y","Yaoi","http://boards.4chan.org/y/"),
					new Board(fourchan,"3","3DCG","http://boards.4chan.org/3/"),
					new Board(fourchan,"adv","Advice","http://boards.4chan.org/adv/"),
					new Board(fourchan,"an","Animals & Nature","http://boards.4chan.org/an/"),
					new Board(fourchan,"cgl","Cosplay & EGL","http://boards.4chan.org/cgl/"),
					new Board(fourchan,"ck","Food & Cooking","http://boards.4chan.org/ck/"),
					new Board(fourchan,"co","Comics & Cartoons","http://boards.4chan.org/co/"),
					new Board(fourchan,"diy","Do-It-Yourself","http://boards.4chan.org/diy/"),
					new Board(fourchan,"fa","Fashion","http://boards.4chan.org/fa/"),
					new Board(fourchan,"fit","Health & Fitness","http://boards.4chan.org/fit/"),
					new Board(fourchan,"hc","Hardcore","http://boards.4chan.org/hc/"),
					new Board(fourchan,"int","International","http://boards.4chan.org/int/"),
					new Board(fourchan,"jp","Otaku Culture","http://boards.4chan.org/jp/"),
					new Board(fourchan,"lit","Literature","http://boards.4chan.org/lit/"),
					new Board(fourchan,"mlp","Pony","http://boards.4chan.org/mlp/"),
					new Board(fourchan,"mu","Music","http://boards.4chan.org/mu/"),
					new Board(fourchan,"n","Transportation","http://boards.4chan.org/n/"),
					new Board(fourchan,"po","Papercraft & Origami","http://boards.4chan.org/po/"),
					new Board(fourchan,"pol","Politically Incorrect","http://boards.4chan.org/pol/"),
					new Board(fourchan,"sci","Science & Math","http://boards.4chan.org/sci/"),
					new Board(fourchan,"soc","Social","http://boards.4chan.org/soc/"),
					new Board(fourchan,"sp","Sports","http://boards.4chan.org/sp/"),
					new Board(fourchan,"tg","Traditional Games","http://boards.4chan.org/tg/"),
					new Board(fourchan,"toy","Toys","http://boards.4chan.org/toy/"),
					new Board(fourchan,"trv","Travel","http://boards.4chan.org/trv/"),
					new Board(fourchan,"tv","Television & Film","http://boards.4chan.org/tv/"),
					new Board(fourchan,"vp","Pokémon","http://boards.4chan.org/vp/"),
					new Board(fourchan,"wsg","Worksafe GIF","http://boards.4chan.org/wsg/"),
					new Board(fourchan,"x","Paranormal","http://boards.4chan.org/x/")
			};
			return sortData(board_data);
		} else if (sChan=="7chan"){
			Chan sevenchan = new Chan(R.drawable.icon_7chan,"7chan",context.getResources().getString(R.string.chan_descr_7chan),context.getResources().getString(R.string.chan_URL_4chan));
			Board board_data[] = new Board[] {
					new Board(sevenchan,"a","Anime & Manga","http://boards.4chan.org/a/"), //TODO
					new Board(sevenchan, "b","Random","http://boards.4chan.org/b/")

			};
			return sortData(board_data);
		} else if (sChan=="420chan") {

		}
		Chan nullchan = new Chan(R.drawable.icon_7chan,"NULL",context.getResources().getString(R.string.chan_descr_7chan),context.getResources().getString(R.string.chan_URL_4chan));
		Board null_data[] = new Board[] {
				new Board(nullchan, "z","Null Board","http://boards.4chan.org/sci/"),
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
