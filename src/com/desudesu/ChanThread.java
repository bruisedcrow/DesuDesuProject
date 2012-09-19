package com.desudesu;

public class ChanThread {
	private int id;
	private String name;
	private String sPost;
	private String thumbUrl;
	private String imageUrl;
	private String sChan;
	private String sBoard;
	private String threadUrl;
	
	public ChanThread(int id, String name, String sPost, String thumbUrl,
			String imageUrl) {
		super();
		this.id = id;
		this.name = name;
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
		if (name == "Null") {
			return "Anonymous " + id;
		}
		return name + " " + id;
	}
	public void setName(String title) {
		this.name = name;
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
	public String getChan() {
		return sChan;
	}
	public void setChan(String sChan) {
		this.sChan = sChan;
	}
	public String getThreadUrl() {
		return threadUrl;
	}
	public void setThreadUrl(String threadUrl) {
		this.threadUrl = threadUrl;
	}
	public String getBoard() {
		return sBoard;
	}
	public void setBoard(String sBoard) {
		this.sBoard = sBoard;
	}
}
