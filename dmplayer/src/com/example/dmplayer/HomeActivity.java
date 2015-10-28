package com.example.dmplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.example.dmplayer.domain.AudioInfo;
import com.example.dmplayer.fragment.BaseFragment;
import com.example.dmplayer.fragment.BaseFragment.BackHandlerInterface;
import com.example.dmplayer.fragment.ContentFragment;
import com.example.dmplayer.fragment.PlayerFragment;
import com.example.dmplayer.service.MusicInterface;
import com.example.dmplayer.service.MusicService;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class HomeActivity extends SlidingFragmentActivity implements BackHandlerInterface{

	private static final String FRAG_CONTENT = "content";
	private static final String FRAG_PLAYER = "player";
	public static ContentFragment content;
	public PlayerFragment player;
	
	//Service相关
	public MusicInterface mi;
	private MyserviceConn conn;
	private Intent intent;
	
	private BaseFragment selectedFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_home);
		setBehindContentView(R.layout.activity_left);

		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		
		
		intent = new Intent(this, MusicService.class);
		startService(intent);
		conn = new MyserviceConn();
		boolean isSuccess = bindService(intent, conn, BIND_AUTO_CREATE);
		initFragment();
	}

	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		content = new ContentFragment();
		player = new PlayerFragment();
		ft.replace(R.id.fl_home, content, FRAG_CONTENT);
		ft.replace(R.id.fl_player, player, FRAG_PLAYER);
		// 提交事务
		ft.commit();
	}
	
	class MyserviceConn implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mi = (MusicInterface) service;
			content.setPlayImageView(mi);
			AudioInfo info = mi.getAudioInfo();
			if(info != null){
				content.setTitle(info.getTitle(), info.getArtist());
			}
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
	}

	@Override
	public void setSelectedFragment(BaseFragment backHandledFragment) {
		this.selectedFragment =   backHandledFragment;	
	}
	
	@Override
    public void onBackPressed() {
        if (selectedFragment == null || !selectedFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
