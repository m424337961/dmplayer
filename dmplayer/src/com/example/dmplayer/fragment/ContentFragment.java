package com.example.dmplayer.fragment;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dmplayer.HomeActivity;
import com.example.dmplayer.R;
import com.example.dmplayer.domain.AudioInfo;
import com.example.dmplayer.pager.MyMusicPager;
import com.example.dmplayer.service.MusicInterface;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class ContentFragment extends BaseFragment implements OnClickListener{


	private static final String HOME_FRAGMENT = "home_fragment";

	private static final String LOCAL_MUSIC_FRAGMENT = "local_music";

	private static final String LOCAL_FAVOR_FRAGMENT = "favor_fragment";

	public View mRootView;

	//--------------------控件定义在此---------------------
	private ImageView mIvPlay;
	private LinearLayout mLvToggle;
	private RelativeLayout mRlPlay;
	private TextView mTvHomeSongName;
	private TextView mTvHomeArtist;

	//Service相关
	private HomeActivity homeUI;

	//子fragment
	public HomeFragment mHomeFragment;
	public LocalMusicFragment mLocalMusicFragment;
	public MyFavorFragment mFavorFragment;

	@Override
	public View initViews() {

		homeUI = (HomeActivity) mActivity;
		mHomeFragment = new HomeFragment();
		mLocalMusicFragment = new LocalMusicFragment();
		mFavorFragment = new MyFavorFragment();

		mRootView = View.inflate(mActivity, R.layout.fragment_home_content, null);
		initHomeFragment();
		mIvPlay = (ImageView) mRootView.findViewById(R.id.iv_home_play);
		mLvToggle = (LinearLayout) mRootView.findViewById(R.id.lv_home_toggle);
		mRlPlay = (RelativeLayout) mRootView.findViewById(R.id.rl_home_play);
		mTvHomeSongName =  (TextView) mRootView.findViewById(R.id.iv_music_home_name);
		mTvHomeArtist =  (TextView) mRootView.findViewById(R.id.iv_home_song_artist);
		mRlPlay.setOnClickListener(this);
		mLvToggle.setOnClickListener(this);
		return mRootView;
	}

	@Override
	public void initData() {
		setTitle("", "");
		MusicInterface musicInterface = homeUI.mi;
		if(musicInterface != null){
			setPlayImageView(musicInterface);
			AudioInfo info = musicInterface.getAudioInfo();
			setTitle(info.getTitle(), info.getArtist());
		}
	}

	public void setPlayImageView(MusicInterface musicInterface) {
		boolean isPlay = musicInterface.isPlay();
		if(isPlay == true){
			mIvPlay.setImageResource(R.drawable.player_pause);
		}else{
			mIvPlay.setImageResource(R.drawable.player_play);
		}
	}

	//-----------------------------监听器，适配器定义在此------------------------
	//点击事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_home_play:
			playMusic();
			break;
		case R.id.lv_home_toggle:
			toggleSlidingMenu();
			break;
		default:
			break;
		}
	}

	private void toggleSlidingMenu() {
		SlidingMenu slidingMenu = homeUI.getSlidingMenu();
		homeUI.player.setPlayImageView(homeUI.mi);
		List<AudioInfo> songList = MyMusicPager.getmLocaolSongList();
		homeUI.player.setTitle(homeUI.mi);
		slidingMenu.toggle(); 
	}

	private void playMusic(){
		homeUI = (HomeActivity) mActivity;
		MusicInterface musicInterface = homeUI.mi;
		boolean isFirstPlay = musicInterface.isFirstPlay();
		boolean isPlay = musicInterface.isPlay();
		if((isFirstPlay == true)){
			if(isPlay == false){
				List<AudioInfo> songList = MyMusicPager.getmLocaolSongList();
				AudioInfo audioInfo = songList.get(0);
				musicInterface.play(audioInfo);
				setTitle(audioInfo.getTitle(), audioInfo.getArtist());
				mIvPlay.setImageResource(R.drawable.player_pause);
			}
		}else{
			if(isPlay == true){
				musicInterface.pause();
				mIvPlay.setImageResource(R.drawable.player_play);
			}else{
				musicInterface.continuePlay();
				mIvPlay.setImageResource(R.drawable.player_pause);
			}
		}
	}

	public void initHomeFragment(){
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.fl_home_main, mHomeFragment, HOME_FRAGMENT);
		ft.add(R.id.fl_home_main, mLocalMusicFragment, LOCAL_MUSIC_FRAGMENT);
		ft.add(R.id.fl_home_main, mFavorFragment, LOCAL_FAVOR_FRAGMENT);
		ft.hide(mLocalMusicFragment);
		ft.hide(mFavorFragment);
		ft.commit();
		
	}

	
	public void switchContentFragment(Fragment from, Fragment to){
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if(from.equals(mHomeFragment)){
			ft.setCustomAnimations(R.anim.tran_in,R.anim.tran_out); 
		}else{
			ft.setCustomAnimations(R.anim.pre_tran_in,R.anim.pre_tran_out); 
		}
		ft.hide(from);
		ft.show(to);
		ft.commit();
	}


	@Override
	public boolean onBackPressed() {
		return false;
	}
	
	public void setTitle(String song, String artist){
		mTvHomeSongName.setText(song);
		mTvHomeArtist.setText(artist);
	}

}
