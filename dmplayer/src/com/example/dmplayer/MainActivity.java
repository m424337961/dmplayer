package com.example.dmplayer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.dmplayer.utils.Player;

public class MainActivity extends ActionBarActivity {

	private Button mButton;
	private SeekBar mSeekBar;
	private Player mPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_my_music);
	}
//		mButton = (Button) findViewById(R.id.btn);
//		mSeekBar = (SeekBar) findViewById(R.id.seekBar);
//		mSeekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
//		mPlayer = new Player(mSeekBar);
//		mPlayer.playUrl("http://192.168.1.8:8080/tma.mp3");
//	}
//
//	public void startMusic(View view){
//		if(mPlayer.isPlay == true){
//			mPlayer.pause();
//			mPlayer.isPlay = false;
//		}else{
//			mPlayer.play();
//			mPlayer.isPlay = true;
//		}
//	}
//	
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		mPlayer.stop();
//	}
//	
//	class SeekBarChangeEvent implements OnSeekBarChangeListener {
//		int progress;
//
//		@Override
//		public void onProgressChanged(SeekBar seekBar, int progress,
//				boolean fromUser) {
//			// 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
//			this.progress = progress * mPlayer.mediaPlayer.getDuration()
//					/ seekBar.getMax();
//		}
//
//		@Override
//		public void onStartTrackingTouch(SeekBar seekBar) {
//
//		}
//
//		@Override
//		public void onStopTrackingTouch(SeekBar seekBar) {
//			// seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
//			mPlayer.mediaPlayer.seekTo(progress);
//		}
//
//	}
}
