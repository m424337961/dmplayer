package com.example.dmplayer.utils;

public class MyTimeUtils {

	public static String intToMinu(int ms){
		int seconds = ms/1000;
		int minutes = seconds/60;
		int second = seconds%60;
		String date;
		if(second < 10){
			date = minutes + ":0" + second;
		}else{
			date = minutes + ":" + second;
		}
		
		return date;
	}
}
