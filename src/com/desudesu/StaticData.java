package com.desudesu;

import android.content.Context;

public class StaticData {
	//TODO: Put all this in a MySQL server
	Context context;
	final Chan fourchan;
	Chan sevenchan;
	Chan fourtwozerochan;
	Chan nullchan;
	Board[] fourchanBoards;
	Board[] sevenchanBoards;
	Board[] fourtwozerochanBoards;
	Board[] nullchanBoards;
	public StaticData(Context context) {
		super();
		this.context = context;
		fourchan =  new Chan(R.drawable.icon_4chan,"4chan",context.getResources().getString(R.string.chan_descr_4chan),context.getResources().getString(R.string.chan_URL_4chan));
		sevenchan = new Chan(R.drawable.icon_7chan,"7chan",context.getResources().getString(R.string.chan_descr_7chan),context.getResources().getString(R.string.chan_URL_4chan));
		fourtwozerochan = new Chan(R.drawable.icon_420chan,"420chan",context.getResources().getString(R.string.chan_descr_420chan),context.getResources().getString(R.string.chan_URL_4chan));
		nullchan = new Chan(R.drawable.icon_4chan,"NULL CHAN YEAH","Nully null null","nullity");
		fourchanBoards = new Board[] {
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
		sevenchanBoards =  new Board[] {
				new Board(sevenchan,"a","Anime & Manga",""), //TODO: Add the rest
				new Board(sevenchan, "b","Random","")
		};
		nullchanBoards = new Board[] {
				new Board(nullchan, "null","Null Board",""),
		};
	}

}
