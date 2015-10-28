package com.example.dmplayer.pager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dmplayer.HomeActivity;
import com.example.dmplayer.R;
import com.example.dmplayer.domain.AudioInfo;
import com.example.dmplayer.domain.MySongList;
import com.example.dmplayer.engine.AudioProvider;
import com.example.dmplayer.engine.MediaProviderFactory;
import com.example.dmplayer.view.SongLayout;

public class MyMusicPager extends BasePager implements OnClickListener{

	//ListView相关元素
	private ListView mLvSongList;
	private ArrayList<MySongList> mMySongList;
	private SongListAdapter mAdapter;
	public static List<AudioInfo> mLocaolSongList;
	private LinearLayout mLlMyMusic;
	
	//所有控件
	private SongLayout mSlLocal;
	private SongLayout mSlFavor;
	
	public MyMusicPager(Activity mActivity) {
		super(mActivity);
		mMySongList = new ArrayList<MySongList>();
		mMySongList.add(new MySongList("新建歌单1", "VIP用户8888", 1));
		mMySongList.add(new MySongList("新建歌单2", "VIP用户8888", 2));
		mMySongList.add(new MySongList("新建歌单3", "VIP用户8888", 3));
	}

	@Override
	protected View initView() {
		View mRootView = View.inflate(mActivity, R.layout.pager_my_music, null);
		View mHeaderView = View.inflate(mActivity, R.layout.header_list_my_music, null);
		View mFootView = View.inflate(mActivity, R.layout.footer_list_my_music, null);
		//findview
		mLvSongList = (ListView) mRootView.findViewById(R.id.lv_music);
		mSlLocal = (SongLayout) mHeaderView.findViewById(R.id.sl_music_local_music);
		mSlFavor = (SongLayout) mHeaderView.findViewById(R.id.sl_music_local_favor);
		
		mLvSongList.addHeaderView(mHeaderView);
		mLvSongList.addFooterView(mFootView);
		
		mLvSongList.setFocusable(true);
		//注册监听
		mSlLocal.setOnClickListener(this);
		mSlFavor.setOnClickListener(this);
		return mRootView;
	}

	@Override
	public void initData() {
		new initDataThread().start();
		
	}
	
	
	class initDataThread extends Thread{
		@Override
		public void run() {
			MediaProviderFactory provider = new AudioProvider(mActivity);
			mLocaolSongList = (List<AudioInfo>) provider.getList();
			mHandler.sendEmptyMessage(0);
		}
	}
	
	private Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			mSlLocal.setNumber(mLocaolSongList.size());
			mAdapter = new SongListAdapter();
			mLvSongList.setAdapter(mAdapter);
		};
	};
	
	//-----------------------------监听器，适配器定义在此------------------------
	class SongListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mMySongList.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.song_list_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_song_list);
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_song_list_name);
				holder.tv_number = (TextView) convertView.findViewById(R.id.tv_song_list_number);
				holder.tv_user = (TextView) convertView.findViewById(R.id.tv_song_list_user);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			MySongList songlist = mMySongList.get(position);
			holder.tv_name.setText(songlist.listName);
			holder.tv_number.setText(songlist.listName+ "首");
			holder.tv_user.setText(songlist.usrId);
			
			return convertView;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}
	
	static class ViewHolder{
		TextView tv_name;
		TextView tv_user;
		ImageView iv_icon;
		TextView tv_number;
	}

	
	//点击事件监听
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sl_music_local_music:
			enterLocalMusic();
			break;

		case R.id.sl_music_local_favor:
			enterMyFavor();
			break;
		default:
			break;
		}
		
	}
	
	private void enterMyFavor() {
		HomeActivity homeUI =  (HomeActivity) mActivity;
		homeUI.content.switchContentFragment(homeUI.content.mHomeFragment, homeUI.content.mFavorFragment);
		homeUI.content.mFavorFragment.initBackHandler();
	}

	private void enterLocalMusic() {
		HomeActivity homeUI =  (HomeActivity) mActivity;
		homeUI.content.switchContentFragment(homeUI.content.mHomeFragment, homeUI.content.mLocalMusicFragment);
		homeUI.content.mLocalMusicFragment.initBackHandler();
	}

	//Getter Setter
	public static List<AudioInfo> getmLocaolSongList() {
		return mLocaolSongList;
	}

}
