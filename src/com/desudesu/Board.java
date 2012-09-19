package com.desudesu;

public class Board extends Chan {
	public Board(Chan chan, String name, String letter, String description) {
		super(chan.getChanIcon(), chan.getChanName(), chan.getChanDescription(), chan.getChanURL());
		this.bLetter = letter;
		this.bName = name;
		this.bDescription = description;
	}
	
	private String bLetter;
	private String bName;
	private String bDescription;
	public String getLetter() {
		return bLetter;
	}
	public void setLetter(String letter) {
		this.bLetter = letter;
	}
	public String getBoardName() {
		return bName;
	}
	public void setBoardName(String name) {
		this.bName = name;
	}
	public String getBoardDescription() {
		return bDescription;
	}
	public void setBoardDescription(String description) {
		this.bDescription = description;
	}
	public String getBoardURL(){
		//Use letter and chan URL to get board URL
		return getChanURL() + getLetter() + "/";
	}
}