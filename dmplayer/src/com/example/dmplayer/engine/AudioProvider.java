package com.example.dmplayer.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.dmplayer.domain.AudioInfo;

public class AudioProvider extends MediaProviderFactory {

	private Context context;

	public AudioProvider(Context context) {
		this.context = context;
	}

	@Override
	public List<?> getList() {
		List<AudioInfo> list = null;
		if (context != null) {
			Cursor cursor = context.getContentResolver().query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
					null, null);
			if (cursor != null) {
				list = new ArrayList<AudioInfo>();
				while (cursor.moveToNext()) {
					int id = cursor.getInt(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
					String title = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
					String album = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
					String artist = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
					String path = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
					String displayName = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
					String mimeType = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
					long duration = cursor
							.getInt(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
					long size = cursor
							.getLong(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
					
					AudioInfo audio = new AudioInfo(id, title, album, artist, path,
							displayName, mimeType, duration, size);
					list.add(audio);
				}
				cursor.close();
			}
		}
		return list;
	}
	
	public static String getPath(String name, List<AudioInfo> list ){
		
		for(AudioInfo audioInfo : list){
			if(audioInfo.getTitle().equals(name)){
				return audioInfo.getPath();
			}
		}
		return null;
	}
	
	public static int getNumber(String artist, List<AudioInfo> list){
		int number = 0;
		for(AudioInfo audioInfo : list){
			if(audioInfo.getArtist().equals(artist)){
				number++;
			}
		}
		return number;
	}
	
	
	
	public static AudioInfo getInfoFromName(String name, List<AudioInfo> list){
		
		for(AudioInfo audioInfo : list){
			if(audioInfo.getTitle().equals(name)){
				return audioInfo;
			}
		}
		return null;
	}

}
