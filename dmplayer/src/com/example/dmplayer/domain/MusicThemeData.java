package com.example.dmplayer.domain;

import java.util.ArrayList;

public class MusicThemeData {

	public int retcode;
	public ArrayList<MusicTheme> theme;
	
	public class MusicTheme{
		public String id;
		public String theme;
		public String url;
	}
	

}
