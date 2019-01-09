package com.blackmud.tools.RecipeManager.data;

public class Server {
	private String title = null;
	private String url = null;
	private String name = null;
	private String username = null;
	private String password = null;
	
	public Server(String title, String url, String name) {
		this.title = title;
		this.url = url;
		this.name = name;
	}
	
	public Server(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString() {
		return getTitle();
	}
}
