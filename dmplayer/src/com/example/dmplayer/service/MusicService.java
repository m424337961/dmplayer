package com.example.dmplayer.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.example.dmplayer.domain.AudioInfo;
import com.example.dmplayer.fragment.PlayerFragment;

public class MusicService extends Service {

	private MediaPlayer player;
	private Timer timer;
	private boolean isPlay = false;
	private boolean isFirstPlay = true;
	private AudioInfo audioInfo;
	
	@Override
	public IBinder onBind(Intent intent) {
		return new MusicController();
	}

	class MusicController extends Binder implements MusicInterface{


		@Override
		public void pause() {
			MusicService.this.pause();
		}

		@Override
		public void continuePlay() {
			MusicService.this.continuePlay();
		}

		@Override
		public void seekTo(int progress) {
			MusicService.this.seekTo(progress);
			
		}

		@Override
		public MediaPlayer getMediaPlayer() {
			return MusicService.this.getMediaPlayer();
		}

		@Override
		public boolean isPlay() {
			return MusicService.this.isPlay();
		}

		@Override
		public void play(AudioInfo info) {
			MusicService.this.play(info);
		}

		@Override
		public boolean isFirstPlay() {
			return isFirstPlay;
		}

		@Override
		public void stop() {
			MusicService.this.stop();
		}

		@Override
		public AudioInfo getAudioInfo() {
			return MusicService.this.getAudioInfo();
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		player = new MediaPlayer();
		player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				isFirstPlay = true;
			}
		});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//停止播放
		player.stop();
		//释放占用的资源，此时player对象已经废掉了
		player.release();
		player = null;
		if(timer != null){
			timer.cancel();
			timer = null;
		}
	}
	
	
	//获取是否是第一次开始播放
	public boolean isFirstPlay(){
		return isFirstPlay;
	}
	
	//获取mediaplayer
	public MediaPlayer getMediaPlayer(){
		return player;
	}
	//获取mediaPlayer的状态
	public boolean isPlay(){
		return isPlay;
	}
	
	//获取mediaplayer播放的path
	public AudioInfo getAudioInfo(){
		return audioInfo;
	}
	
	//播放音乐
	public void play(AudioInfo info){
		//重置
		audioInfo = info;
		String url = audioInfo.getPath();
		isFirstPlay = false;
		player.reset();
		isPlay = true;
		try {
			//加载多媒体文件
			player.setDataSource(url);
//			player.setDataSource("http://192.168.13.119:8080/bzj.mp3");
//			player.prepare();
			player.prepareAsync();
//			player.start();
			player.setOnPreparedListener(new OnPreparedListener() {
				//准备完毕时，此方法调用
				@Override
				public void onPrepared(MediaPlayer mp) {
					player.start();
					addTimer();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	//继续播放
	public void continuePlay(){
		player.start();
		isPlay = true;
	}
	
	//暂停播放
	public void pause(){
		player.pause();
		isPlay = false;
	}
	
	public void stop(){
		player.stop();
		isPlay = false;
	}
	
	public void seekTo(int progress){
		player.seekTo(progress);
	}
	public void addTimer(){
		if(timer == null){
			timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					//获取歌曲总时长
					int duration = player.getDuration();
					//获取歌曲当前播放进度
					int currentPosition= player.getCurrentPosition();
					Message msg = PlayerFragment.handler.obtainMessage();
					//把进度封装至消息对象中
					Bundle bundle = new Bundle();
					bundle.putInt("duration", duration);
					bundle.putInt("currentPosition", currentPosition);
					msg.setData(bundle);
					PlayerFragment.handler.sendMessage(msg);
				}
			}, 5, 1000);
		}
	}
}
