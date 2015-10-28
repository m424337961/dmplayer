package com.example.dmplayer.service;

import android.media.MediaPlayer;

import com.example.dmplayer.domain.AudioInfo;


public interface MusicInterface {

	void play(AudioInfo info);
	void pause();
	void continuePlay();
	void seekTo(int progress);
	MediaPlayer getMediaPlayer();
	boolean isPlay();
	boolean isFirstPlay();
	void stop();
	AudioInfo getAudioInfo();
}
