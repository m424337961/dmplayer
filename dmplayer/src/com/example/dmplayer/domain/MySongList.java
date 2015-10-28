package com.example.dmplayer.domain;

public class MySongList {
	public String listName;
	public String usrId;
	public int songNumber;
	
	public MySongList(String listName, String usrId, int songNumber) {
		this.listName = listName;
		this.usrId = usrId;
		this.songNumber = songNumber;
	}
}
