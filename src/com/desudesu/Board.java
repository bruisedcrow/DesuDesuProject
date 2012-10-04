package com.desudesu;

public class Board extends Chan {
	public Board(Chan chan, String letter, String name, String description, boolean favourite) {
		super(chan.getChanIcon(), chan.getChanName(), chan.getChanDescription(), chan.getChanURL());
		this.bLetter = letter;
		this.bName = name;
		this.bDescription = description;
		this.setFavourite(favourite);
	}
	
	private String bLetter;
	private String bName;
	private String bDescription;
	private boolean favourite;
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
	
	public String getBoardUniqueName(){
		return getChanName() + "//" + bName;
	}
	
	public Chan getChan(){
		return new Chan(getChanIcon(), getChanName(), getChanDescription(), getChanURL());
	}
	public boolean isFavourite() {
		return favourite;
	}
	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}
}