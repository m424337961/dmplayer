package com.example.dmplayer.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dmplayer.db.MyFavorDbHelper;
import com.example.dmplayer.domain.AudioInfo;

public class MyFavorDao {

	private MyFavorDbHelper helper;
	private SQLiteDatabase db;
	
	public MyFavorDao(Context context) {
		helper = new MyFavorDbHelper(context);
	}
	
	public void add(String song, String artist, String path){
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("song", song);
		values.put("artist", artist);
		values.put("path", path);
		db.insert("myfavor", null, values);
		db.close();
	}
	
	public boolean find(String song){
		boolean result = false;
		db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from myfavor where song=?", new String[]{song});
		if(cursor.moveToNext()){
			result = true;
		}
		cursor.close();
		db.close();
		return result;
	}
	
	public List<AudioInfo> findAll(){
		List<AudioInfo>  result = new ArrayList<AudioInfo>();
		db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select song, artist, path from myfavor order by _id desc", null);
		while(cursor.moveToNext()){
			AudioInfo info = new AudioInfo();
			String song = cursor.getString(0);
			String artist = cursor.getString(1);
			String path = cursor.getString(2);
			info.setTitle(song);
			info.setArtist(artist);
			info.setPath(path);
			result.add(info);
		}
		cursor.close();
		db.close();		
		return result;
	}
	
}
