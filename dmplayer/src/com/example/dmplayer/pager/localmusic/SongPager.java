package com.example.dmplayer.pager.localmusic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dmplayer.HomeActivity;
import com.example.dmplayer.R;
import com.example.dmplayer.domain.AudioInfo;
import com.example.dmplayer.engine.AudioProvider;
import com.example.dmplayer.fragment.ContentFragment;
import com.example.dmplayer.global.GlobalPlayList;
import com.example.dmplayer.pager.BasePager;
import com.example.dmplayer.pager.MyMusicPager;

public class SongPager extends BasePager {

	private List<AudioInfo> mLocaolSongList;
	private Map<String, String> mSongMap;
	private ListView mLvLocalSongName;
	private SongListAdapter mAdapter;
	
	public SongPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	protected View initView() {
		View view = View.inflate(mActivity, R.layout.pager_local_music_song, null);
		mLvLocalSongName = (ListView) view.findViewById(R.id.lv_local_music_song);
		return view;
	}

	@Override
	public void initData() {
		mLocaolSongList = MyMusicPager.mLocaolSongList;
		mSongMap = new HashMap<String, String>();
		for(AudioInfo localsong : mLocaolSongList){
			String songName = localsong.getTitle();
			String artist = localsong.getArtist();
			mSongMap.put(songName, artist);
		}
		mAdapter = new SongListAdapter();
		mLvLocalSongName.setAdapter(mAdapter);
		mLvLocalSongName.setOnItemClickListener(new LocalSongItemClickListener());
	}

	//-----------------------------监听器，适配器定义在此------------------------
	class SongListAdapter extends BaseAdapter{

		Object[] key;
		@Override
		public int getCount() {
			return mSongMap.size();
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
			if(key == null){
				key =  mSongMap.keySet().toArray();
				Arrays.sort(key); 
			}
			holder.tv_name .setText(key[position] + "");
			holder.tv_artist.setText(mSongMap.get(key[position]));
			
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

	class LocalSongItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			HomeActivity homeUi = (HomeActivity) mActivity;
			homeUi.mi.stop();
			Object[] key =  mSongMap.keySet().toArray();
			Arrays.sort(key);
			String songName = (String) key[position];
			AudioInfo info = AudioProvider.getInfoFromName(songName, mLocaolSongList);
			homeUi.mi.play(info);
			homeUi.content.setPlayImageView(homeUi.mi);
			homeUi.content.setTitle(songName, info.getArtist());
			GlobalPlayList.currentList = GlobalPlayList.LOCAL_LIST;
		}
	}
	
	static class ViewHolder{
		TextView tv_name;
		TextView tv_artist;
	}
}
