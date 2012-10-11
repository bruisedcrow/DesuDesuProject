package com.desudesu;

import java.util.ArrayList;
import java.util.List;

public class ChanThread extends Board {
	private int iOPId;
	private String sOPName;
	private String sOPPost;
	private String sImageName;
	private String sImageExtension;
	private List<Post> posts;
	
	public ChanThread(Board board, int id, String sOPName, String sPost, String sImageName, String sImageExtension) {
		super(board.getChan(), board.getBoardLetter(), board.getBoardName(), board.getBoardDescription(),board.isFavourite());
		this.iOPId = id;
		this.sOPName = sOPName;
		this.sOPPost = sPost;
		this.sImageName = sImageName;
		this.sImageExtension = sImageExtension;
		posts = new ArrayList<Post>();
	}
	public int getOPId() {
		return iOPId;
	}
	public void setOPId(int id) {
		this.iOPId = id;
	}
	public String getOPName() {
		if (sOPName == "Null") {
			return "Anonymous " + iOPId;
		}
		return sOPName + " " + iOPId;
	}
	public void setOPName(String sOPName) {
		this.sOPName = sOPName;
	}
	public String getOPPost() {
		return sOPPost;
	}
	public void setOPPost(String sPost) {
		this.sOPPost = sPost;
	}
	public String getThumbUrl() {
		return "http://0.thumbs.4chan.org/" + getBoardLetter() + "/thumb/" + sImageName + "s.jpg";
	}
	public void setImageName(String sImageName) {
		this.sImageName = sImageName;
	}
	
	public void setImageExtension(String sImageExtension) {
		this.sImageExtension = sImageExtension;
	}
	public String getImageUrl() {
		return "https://images.4chan.org/" + getBoardLetter() + "/src/" + sImageName + sImageExtension;
	}
	
	public String getChanThreadUniqueName(){
		return getChanName() + "//" + getBoardName() + "//" + iOPId;
	}
}
