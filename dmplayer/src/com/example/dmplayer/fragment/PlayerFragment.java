package com.example.dmplayer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.dmplayer.HomeActivity;
import com.example.dmplayer.R;
import com.example.dmplayer.dao.MyFavorDao;
import com.example.dmplayer.domain.AudioInfo;
import com.example.dmplayer.pager.MyMusicPager;
import com.example.dmplayer.service.MusicInterface;
import com.example.dmplayer.utils.MyTimeUtils;

public class PlayerFragment extends BaseFragment implements OnClickListener{

	//控件定义
	public View mRootView;
	private ImageView mIvPlay;
	private static SeekBar mSbPlay;
	private RelativeLayout mRlPlayerPlay;
	private static TextView mTvTime;
	private static TextView mTvAllTime;
	private TextView mTvPlaySongName;
	private TextView mTvPlayArtist;
	private ImageView mIvFavor;

	private HomeActivity homeUI;

	private MyFavorDao mFavorDao;

	@Override
	public View initViews() {
		homeUI = (HomeActivity) mActivity;

		mFavorDao = new MyFavorDao(mActivity);

		mRootView = View.inflate(mActivity, R.layout.fragment_home_player, null);
		mIvPlay = (ImageView) mRootView.findViewById(R.id.iv_home_play);
		mSbPlay = (SeekBar) mRootView.findViewById(R.id.sb_player);
		mRlPlayerPlay = (RelativeLayout) mRootView.findViewById(R.id.rl_home_play);
		mTvTime = (TextView) mRootView.findViewById(R.id.tv_player_time);
		mTvAllTime = (TextView) mRootView.findViewById(R.id.tv_player_all_time);
		mTvPlaySongName = (TextView) mRootView.findViewById(R.id.tv_play_song_name);
		mTvPlayArtist = (TextView) mRootView.findViewById(R.id.tv_play_artist);
		mIvFavor = (ImageView) mRootView.findViewById(R.id.iv_play_favor);

		//设置监听
		mRlPlayerPlay.setOnClickListener(this);
		mSbPlay.setOnSeekBarChangeListener(new PlayerSeekBarListener());
		mIvFavor.setOnClickListener(this);
		return mRootView;
	}

	public void setPlayImageView(MusicInterface musicInterface) {
		mMusicInterface = musicInterface;
		//设置歌手歌曲名字
		boolean isPlay = musicInterface.isPlay();
		if(isPlay == true){
			mIvPlay.setImageResource(R.drawable.player_pause);
		}else{
			mIvPlay.setImageResource(R.drawable.player_play);
		}
		AudioInfo info = mMusicInterface.getAudioInfo();
		if(info != null){
			//设置是否是我的喜欢列表
			boolean isFavor = mFavorDao.find(info.getTitle());
			if(isFavor == true){
				mIvFavor.setBackgroundResource(R.drawable.bubble_favour_red);
			}else{
				mIvFavor.setBackgroundResource(R.drawable.bubble_favour);
			}
		}else{
			mIvFavor.setBackgroundResource(R.drawable.bubble_favour);
		}

	}

	public void setTitle(MusicInterface mi){
		AudioInfo audioInfo = mi.getAudioInfo();
		if(audioInfo != null){
			mTvPlaySongName.setText(audioInfo.getTitle());
			mTvPlayArtist.setText(audioInfo.getArtist());
		}else{
			mTvPlaySongName.setText("");
			mTvPlayArtist.setText("");
		}

	}


	@Override
	public void initData() {

	}

	private void playMusic(){
		//		mMusicInterface = homeUI.mi;
		boolean isFirstPlay = mMusicInterface.isFirstPlay();
		boolean isPlay = mMusicInterface.isPlay();
		if((isFirstPlay == true)){
			if(isPlay == false){
				//				MyMusicPager pager = (MyMusicPager) mPagerList.get(0);
				//				List<AudioInfo> songList = pager.getmLocaolSongList();
				//				String path = songList.get(0).getPath();
				//				musicInterface.play(path);
				//				mIvPlay.setImageResource(R.drawable.player_pause);
			}
		}else{
			if(isPlay == true){
				mMusicInterface.pause();
				mIvPlay.setImageResource(R.drawable.player_play);
			}else{
				mMusicInterface.continuePlay();
				mIvPlay.setImageResource(R.drawable.player_pause);
			}
		}
		homeUI.content.setPlayImageView(mMusicInterface);
	}

	//--------------------------------------------------监听器适配器定义在此
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_home_play:
			playMusic();
			break;
		case R.id.iv_play_favor:
			addFavorMusic();
			break;
		default:
			break;
		}		
	}

	private void addFavorMusic() {
		MyFavorDao dao =  new  MyFavorDao(mActivity);
		AudioInfo audioInfo = mMusicInterface.getAudioInfo();
		boolean isFavor = mFavorDao.find(audioInfo.getTitle());
		//设置是否是我的喜欢列表
		if(isFavor == false){
			mIvFavor.setBackgroundResource(R.drawable.bubble_favour_red);
			dao.add(audioInfo.getTitle(), audioInfo.getArtist(), audioInfo.getPath());
		}
		((MyMusicPager)(homeUI.content.mHomeFragment.getmPagerList().get(0))).setFavorNumber();
	}

	class PlayerSeekBarListener implements OnSeekBarChangeListener{

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			//根据拖动的进度改变音乐播放进度
			int progress = seekBar.getProgress();
			//改变播放进度
			homeUI.mi.seekTo(progress);
			mTvTime.setText(MyTimeUtils.intToMinu(progress));
		}
	}

	//Handler
	private static int mAllTime;
	public static Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle bundle = msg.getData();
			int duration = bundle.getInt("duration");
			int currentPostition = bundle.getInt("currentPosition");
			//刷新进度条的进度
			//刷新进度条的进度
			mAllTime = duration;
			mTvTime.setText(MyTimeUtils.intToMinu(currentPostition));
			mTvAllTime.setText(MyTimeUtils.intToMinu(duration));
			mSbPlay.setMax(duration);
			mSbPlay.setProgress(currentPostition);
		}
	};
	private MusicInterface mMusicInterface;

	@Override
	public boolean onBackPressed() {

		return false;
	}

}
