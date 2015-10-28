package com.example.dmplayer.fragment;

import java.util.List;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dmplayer.HomeActivity;
import com.example.dmplayer.R;
import com.example.dmplayer.dao.MyFavorDao;
import com.example.dmplayer.domain.AudioInfo;
import com.example.dmplayer.engine.AudioProvider;
import com.example.dmplayer.global.GlobalPlayList;
import com.example.dmplayer.pager.MyMusicPager;

public class MyFavorFragment extends BaseFragment {

	private HomeActivity homeUI;
	private View mRootView;
	private ListView mLvFavor;
	private FavorListAdapter mAdapter;
	private MyFavorDao mFavorDao;
	private List<AudioInfo> mLocaolSongList;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			mAdapter = new FavorListAdapter();
			mLvFavor.setAdapter(mAdapter);
		};
	};

	@Override
	public View initViews() {
		homeUI = (HomeActivity) mActivity;
		
		mFavorDao = new MyFavorDao(mActivity);
		
		mRootView = View.inflate(mActivity, R.layout.fragment_my_favor, null);
		mLvFavor = (ListView) mRootView.findViewById(R.id.lv_my_favor);
		mLvFavor.setOnItemClickListener(new FavorItemClickListener());
		
		return mRootView;
	}
	
	private List<AudioInfo> mMyFavorList;

	@Override
	public void initData() {
		
	}

	@Override
	public boolean onBackPressed() {
		if (!mHandledPress) {
			homeUI.content.switchContentFragment(homeUI.content.mFavorFragment, homeUI.content.mHomeFragment);
			homeUI.content.mHomeFragment.initBackHandler();
			mHandledPress = false;
			return true;
		}
		return false;
	}

	//hide/show生命周期方法
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(hidden == true){
			//隐藏
		}else{
			//显示
			new Thread(){
				public void run() {
					mLocaolSongList = MyMusicPager.mLocaolSongList;
					mMyFavorList = mFavorDao.findAll();
					handler.sendEmptyMessage(0);
				};
			}.start();
		}
	}
	
	//-----------------------------监听器，适配器定义在此------------------------
	class FavorListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mMyFavorList.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.list_item_local_song_name, null);
				holder = new ViewHolder();
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_local_song_name);
				holder.tv_artist = (TextView) convertView.findViewById(R.id.tv_local_song_artist);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			AudioInfo audioInfo = mMyFavorList.get(position);
			holder.tv_name .setText(audioInfo.getTitle());
			holder.tv_artist.setText(audioInfo.getArtist());
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
	
	class ViewHolder{
		TextView tv_name;
		TextView tv_artist;
	}
	
	class FavorItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			HomeActivity homeUi = (HomeActivity) mActivity;
			homeUi.mi.stop();
			ViewHolder holder = (ViewHolder) view.getTag();
			String song = holder.tv_name.getText().toString();
			AudioInfo info = AudioProvider.getInfoFromName(song, mLocaolSongList);
			homeUi.mi.play(info);
			homeUi.content.setPlayImageView(homeUi.mi);
			homeUi.content.setTitle(song, info.getArtist());
			GlobalPlayList.currentList = GlobalPlayList.FAVOR_LIST;
		}
	}
}
