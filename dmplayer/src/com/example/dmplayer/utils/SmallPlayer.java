package com.example.dmplayer.utils;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class SmallPlayer {

	public MediaPlayer mMediaPlayer;
	public boolean isPlay;
	
	public SmallPlayer() {
		isPlay = false;
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}
	
	public void playFromLocal(String path){
		try {
			isPlay = true;
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void play(){
		mMediaPlayer.start();
		isPlay = true;
	}

	public void pause(){
		isPlay = false;
		mMediaPlayer.pause();
	}
	
	public void stop(){
		isPlay = false;
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}
