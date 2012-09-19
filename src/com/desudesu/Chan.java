package com.desudesu;

public class Chan {
	private int cIcon;
	private String cName;
	private String cDescription;
	private String cURL;
	public int getChanIcon() {
		return cIcon;
	}
	public void setChanIcon(int icon) {
		this.cIcon = icon;
	}
	public String getChanName() {
		return cName;
	}
	public void setChanName(String name) {
		this.cName = name;
	}
	public String getChanDescription() {
		return cDescription;
	}
	public void setChanDescription(String description) {
		this.cDescription = description;
	}
	public Chan(int icon, String name, String description, String URL) {
		super();
		this.cIcon = icon;
		this.cName = name;
		this.cDescription = description;
		this.cURL = URL;
	}
	public String getChanURL() {
		return cURL;
	}
	public void setChanURL(String cURL) {
		this.cURL = cURL;
	}

}
