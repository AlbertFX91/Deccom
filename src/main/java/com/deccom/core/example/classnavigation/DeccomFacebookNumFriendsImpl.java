package com.deccom.core.example.classnavigation;

public class DeccomFacebookNumFriendsImpl extends DeccomRESTDataRecoverImpl implements DeccomFacebookDataRecover {
	
	private String username;
	
	public DeccomFacebookNumFriendsImpl() {
		super("api.facebook.com/user/albrojfer1/friends", "$.numFriends");
		this.username = "albrojfer1";
	}
	
	public DeccomFacebookNumFriendsImpl(String username) {
		super();
		this.setUrl("api.facebook.com/user/"+username+"/friends");
		this.setXpath("$.numFriends");
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	
}
