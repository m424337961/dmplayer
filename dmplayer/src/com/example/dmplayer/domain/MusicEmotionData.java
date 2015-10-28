package com.example.dmplayer.domain;

import java.util.ArrayList;

public class MusicEmotionData {

	public int retcode;
	public ArrayList<MusicEmotion> emotion;
	
	public class MusicEmotion{
		public String id;
		public String emotion;
		public String url;
	}
	

}
