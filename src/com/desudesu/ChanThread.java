package com.desudesu;

public class ChanThread extends Board {
	private int id;
	private String sOPName;
	private String sPost;
	private String thumbUrl;
	private String imageUrl;
	
	public ChanThread(Board board, int id, String sOPName, String sPost, String thumbUrl,
			String imageUrl) {
		super(board.getChan(), board.getLetter(), board.getBoardName(), board.getBoardDescription());
		this.id = id;
		this.sOPName = sOPName;
		this.sPost = sPost;
		this.thumbUrl = thumbUrl;
		this.imageUrl = imageUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		if (sOPName == "Null") {
			return "Anonymous " + id;
		}
		return sOPName + " " + id;
	}
	public void setName(String sOPName) {
		this.sOPName = sOPName;
	}
	public String getPost() {
		return sPost;
	}
	public void setPost(String sPost) {
		this.sPost = sPost;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getChanThreadUniqueName(){
		return getChanName() + "//" + getBoardName() + "//" + id;
	}
}
