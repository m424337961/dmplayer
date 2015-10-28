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
		//ֹͣ����
		player.stop();
		//�ͷ�ռ�õ���Դ����ʱplayer�����Ѿ��ϵ���
		player.release();
		player = null;
		if(timer != null){
			timer.cancel();
			timer = null;
		}
	}
	
	
	//��ȡ�Ƿ��ǵ�һ�ο�ʼ����
	public boolean isFirstPlay(){
		return isFirstPlay;
	}
	
	//��ȡmediaplayer
	public MediaPlayer getMediaPlayer(){
		return player;
	}
	//��ȡmediaPlayer��״̬
	public boolean isPlay(){
		return isPlay;
	}
	
	//��ȡmediaplayer���ŵ�path
	public AudioInfo getAudioInfo(){
		return audioInfo;
	}
	
	//��������
	public void play(AudioInfo info){
		//����
		audioInfo = info;
		String url = audioInfo.getPath();
		isFirstPlay = false;
		player.reset();
		isPlay = true;
		try {
			//���ض�ý���ļ�
			player.setDataSource(url);
//			player.setDataSource("http://192.168.13.119:8080/bzj.mp3");
//			player.prepare();
			player.prepareAsync();
//			player.start();
			player.setOnPreparedListener(new OnPreparedListener() {
				//׼�����ʱ���˷�������
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
	
	//��������
	public void continuePlay(){
		player.start();
		isPlay = true;
	}
	
	//��ͣ����
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
					//��ȡ������ʱ��
					int duration = player.getDuration();
					//��ȡ������ǰ���Ž���
					int currentPosition= player.getCurrentPosition();
					Message msg = PlayerFragment.handler.obtainMessage();
					//�ѽ��ȷ�װ����Ϣ������
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
