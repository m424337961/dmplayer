package com.example.dmplayer.pager.localmusic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dmplayer.R;
import com.example.dmplayer.domain.AudioInfo;
import com.example.dmplayer.engine.AudioProvider;
import com.example.dmplayer.pager.BasePager;
import com.example.dmplayer.pager.MyMusicPager;
import com.example.dmplayer.view.InnerListView;

public class ArtistPager extends BasePager {

	private  Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			mParentAdapter = new ArtistListAdapter();
			mLvLocalSongArtist.setAdapter(mParentAdapter);
		};
	};
	private List<AudioInfo> mLocaolSongList;
	private ListView mLvLocalSongArtist;
	private List<String> mFirstWordList;
	private Map<String, ArrayList<String>> mArtistMap;
	private Map<String, ArrayList<String>> mArtistMapNotNull;
	private ArtistListAdapter mParentAdapter;

	public ArtistPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	protected View initView() {
		View view = View.inflate(mActivity, R.layout.pager_local_music_artist, null);
		mLvLocalSongArtist = (ListView) view.findViewById(R.id.lv_local_music_artist);
		return view;
	}

	@Override
	public void initData() {
		mLocaolSongList = MyMusicPager.mLocaolSongList;
		mArtistMap = new HashMap<String, ArrayList<String>>();
		mArtistMapNotNull = new HashMap<String, ArrayList<String>>();
		Thread thread = new Thread(){
			public void run() {
				sortByFirstWord();
				handler.sendEmptyMessage(0);
			};
		};
		thread.start();
	}


	//-----------------------------监听器，适配器定义在此------------------------
	class ArtistListAdapter extends BaseAdapter{

		Object[] key;

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			if (observer != null) {
				super.unregisterDataSetObserver(observer);
			}
		}

		@Override
		public int getCount() {
			return mArtistMapNotNull.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.list_item_local_song_artist, null);
				holder = new ViewHolder();
				holder.tv_first = (TextView) convertView.findViewById(R.id.tv_local_song_artist_first_word);
				holder.lv_artist = (InnerListView) convertView.findViewById(R.id.lv_local_song_artist_item);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}

			if(key == null){
				key =  mArtistMapNotNull.keySet().toArray();
				Arrays.sort(key); 
			}
			holder.tv_first .setText(key[position] + "");
			ArrayList<String> artist = (ArrayList<String>) mArtistMap.get(key[position]+"");
			if(artist != null){
				ArtistListComAdapter ChildAdapter = new ArtistListComAdapter(artist);
				holder.lv_artist.setAdapter(ChildAdapter);
			}
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

	//-----------------------------监听器，适配器定义在此------------------------
	class ArtistListComAdapter extends BaseAdapter{

		ArrayList<String> artist;
		public ArtistListComAdapter(ArrayList<String> artist){
			this.artist = artist;
			Collections.sort(artist);
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			if (observer != null) {
				super.unregisterDataSetObserver(observer);
			}
		}

		@Override
		public int getCount() {
			return artist.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewChildHolder holder;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.list_item_local_song_name_item, null);
				holder = new ViewChildHolder();
				holder.tv_artist = (TextView) convertView.findViewById(R.id.tv_local_song_artist_list_item_artist);
				holder.tv_number = (TextView) convertView.findViewById(R.id.tv_local_song_artist_list_item_number);
				convertView.setTag(holder);
			}else{
				holder = (ViewChildHolder) convertView.getTag();
			}
			String art = artist.get(position);
			holder.tv_artist.setText(art);
			int number = AudioProvider.getNumber(art, mLocaolSongList);
			holder.tv_number.setText(number + "首");
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
		TextView tv_first;
		InnerListView lv_artist;
	}

	static class ViewChildHolder{
		TextView tv_artist;
		TextView tv_number;
	}

	private void sortByFirstWord() {

		ArrayList<String> artistA = new ArrayList<String>();
		ArrayList<String> artistB = new ArrayList<String>();
		ArrayList<String> artistC = new ArrayList<String>();
		ArrayList<String> artistD = new ArrayList<String>();
		ArrayList<String> artistE = new ArrayList<String>();
		ArrayList<String> artistF = new ArrayList<String>();
		ArrayList<String> artistG = new ArrayList<String>();
		ArrayList<String> artistH = new ArrayList<String>();
		ArrayList<String> artistI = new ArrayList<String>();
		ArrayList<String> artistJ = new ArrayList<String>();
		ArrayList<String> artistK = new ArrayList<String>();
		ArrayList<String> artistL = new ArrayList<String>();
		ArrayList<String> artistM = new ArrayList<String>();
		ArrayList<String> artistN = new ArrayList<String>();
		ArrayList<String> artistO = new ArrayList<String>();
		ArrayList<String> artistP = new ArrayList<String>();
		ArrayList<String> artistQ = new ArrayList<String>();
		ArrayList<String> artistR = new ArrayList<String>();
		ArrayList<String> artistS = new ArrayList<String>();
		ArrayList<String> artistT = new ArrayList<String>();
		ArrayList<String> artistU = new ArrayList<String>();
		ArrayList<String> artistV = new ArrayList<String>();
		ArrayList<String> artistW = new ArrayList<String>();
		ArrayList<String> artistX = new ArrayList<String>();
		ArrayList<String> artistY = new ArrayList<String>();
		ArrayList<String> artistZ = new ArrayList<String>();
		ArrayList<String> artistChina = new ArrayList<String>();

		for(AudioInfo info : mLocaolSongList){
			String artist = info.getArtist();	
			String firtWord = artist.charAt(0)+"";
			if(firtWord.equals("a") || firtWord.equals("A")){
				if(!artistA.contains(artist)){
					artistA.add(artist);
				}
			}else if(firtWord.equals("b") || firtWord.equals("B")){
				if(!artistB.contains(artist)){
					artistB.add(artist);
				}
			}else if(firtWord.equals("c") || firtWord.equals("C")){
				if(!artistC.contains(artist)){
					artistC.add(artist);
				}
			}else if(firtWord.equals("d") || firtWord.equals("D")){
				if(!artistD.contains(artist)){
					artistD.add(artist);
				}
			}else if(firtWord.equals("e") || firtWord.equals("E")){
				if(!artistE.contains(artist)){
					artistE.add(artist);
				}
			}else if(firtWord.equals("f") || firtWord.equals("F")){
				if(!artistF.contains(artist)){
					artistF.add(artist);
				}
			}else if(firtWord.equals("g") || firtWord.equals("G")){
				if(!artistG.contains(artist)){
					artistG.add(artist);
				}
			}else if(firtWord.equals("h") || firtWord.equals("H")){
				if(!artistH.contains(artist)){
					artistH.add(artist);
				}
			}else if(firtWord.equals("i") || firtWord.equals("I")){
				if(!artistI.contains(artist)){
					artistI.add(artist);
				}
			}else if(firtWord.equals("j") || firtWord.equals("J")){
				if(!artistJ.contains(artist)){
					artistJ.add(artist);
				}
			}else if(firtWord.equals("k") || firtWord.equals("K")){
				if(!artistK.contains(artist)){
					artistK.add(artist);
				}
			}else if(firtWord.equals("l") || firtWord.equals("L")){
				if(!artistL.contains(artist)){
					artistL.add(artist);
				}
			}else if(firtWord.equals("m") || firtWord.equals("M")){
				if(!artistM.contains(artist)){
					artistM.add(artist);
				}
			}else if(firtWord.equals("n") || firtWord.equals("N")){
				if(!artistN.contains(artist)){
					artistN.add(artist);
				}
			}else if(firtWord.equals("o") || firtWord.equals("O")){
				if(!artistO.contains(artist)){
					artistO.add(artist);
				}
			}else if(firtWord.equals("p") || firtWord.equals("P")){
				if(!artistP.contains(artist)){
					artistP.add(artist);
				}
			}else if(firtWord.equals("q") || firtWord.equals("Q")){
				if(!artistQ.contains(artist)){
					artistQ.add(artist);
				}
			}else if(firtWord.equals("r") || firtWord.equals("R")){
				if(!artistR.contains(artist)){
					artistR.add(artist);
				}
			}else if(firtWord.equals("s") || firtWord.equals("S")){
				if(!artistS.contains(artist)){
					artistS.add(artist);
				}
			}else if(firtWord.equals("t") || firtWord.equals("T")){
				if(!artistT.contains(artist)){
					artistT.add(artist);
				}
			}else if(firtWord.equals("u") || firtWord.equals("U")){
				if(!artistU.contains(artist)){
					artistU.add(artist);
				}
			}else if(firtWord.equals("v") || firtWord.equals("V")){
				if(!artistV.contains(artist)){
					artistV.add(artist);
				}
			}else if(firtWord.equals("w") || firtWord.equals("W")){
				if(!artistW.contains(artist)){
					artistW.add(artist);
				}
			}else if(firtWord.equals("x") || firtWord.equals("X")){
				if(!artistX.contains(artist)){
					artistX.add(artist);
				}
			}else if(firtWord.equals("y") || firtWord.equals("Y")){
				if(!artistY.contains(artist)){
					artistY.add(artist);
				}
			}else if(firtWord.equals("z") || firtWord.equals("Z")){
				if(!artistZ.contains(artist)){
					artistZ.add(artist);
				}
			}else{
				if(!artistChina.contains(artist)){
					artistChina.add(artist);
				}
			}
		}
		mArtistMap.put("A", artistA);
		mArtistMap.put("B", artistB);
		mArtistMap.put("C", artistC);
		mArtistMap.put("D", artistD);
		mArtistMap.put("E", artistE);
		mArtistMap.put("F", artistF);
		mArtistMap.put("G", artistG);
		mArtistMap.put("H", artistH);
		mArtistMap.put("I", artistI);
		mArtistMap.put("J", artistJ);
		mArtistMap.put("K", artistK);
		mArtistMap.put("L", artistL);
		mArtistMap.put("M", artistM);
		mArtistMap.put("N", artistN);
		mArtistMap.put("O", artistO);
		mArtistMap.put("P", artistP);
		mArtistMap.put("Q", artistQ);
		mArtistMap.put("R", artistR);
		mArtistMap.put("S", artistS);
		mArtistMap.put("T", artistT);
		mArtistMap.put("U", artistU);
		mArtistMap.put("V", artistV);
		mArtistMap.put("W", artistW);
		mArtistMap.put("X", artistX);
		mArtistMap.put("Y", artistY);
		mArtistMap.put("Z", artistZ);
		mArtistMap.put("其他", artistChina);

		Iterator iter = mArtistMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String  key = (String) entry.getKey();
			ArrayList<String>  value = (ArrayList<String>) entry.getValue();
			if(value.size() != 0){
				mArtistMapNotNull.put(key, value);
			}
		}
	}


}
