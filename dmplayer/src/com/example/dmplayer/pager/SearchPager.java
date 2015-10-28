package com.example.dmplayer.pager;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmplayer.R;
import com.example.dmplayer.domain.MusicEmotionData;
import com.example.dmplayer.domain.MusicEmotionData.MusicEmotion;
import com.example.dmplayer.domain.MusicStyleData;
import com.example.dmplayer.domain.MusicStyleData.MusicStyle;
import com.example.dmplayer.domain.MusicThemeData;
import com.example.dmplayer.domain.MusicThemeData.MusicTheme;
import com.example.dmplayer.global.GlobalContacts;
import com.example.dmplayer.view.NoScrollGridView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class SearchPager extends BasePager {

	//GridView数据
	private NoScrollGridView mGvStyle;
	private NoScrollGridView mGvEmotion;
	private NoScrollGridView mGvTheme;
	private StyleListAdapter mStyleAdapter;
	private EmotionListAdapter mEmotionAdapter;
	private ThemeListAdapter mThemeAdapter;
	private MusicStyleData mMusicStyleData;
	private MusicEmotionData mMusicEmotionData;
	private MusicThemeData mMusicThemeData;
	private ArrayList<MusicStyle> mStyleList;
	private ArrayList<MusicEmotion> mEmotionList;
	private ArrayList<MusicTheme> mThemeList;
	
	public SearchPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	protected View initView() {
		View view = View.inflate(mActivity, R.layout.pager_serach, null);
		mGvStyle = (NoScrollGridView) view.findViewById(R.id.gv_serach_sytle);
		mGvEmotion = (NoScrollGridView) view.findViewById(R.id.gv_serach_emotion);
		mGvTheme = (NoScrollGridView) view.findViewById(R.id.gv_serach_theme);
		return view;
	}

	@Override
	public void initData() {
		getDataFromServer(GlobalContacts.STYLE_URL);
		getDataFromServer(GlobalContacts.EMOTION_URL);
		getDataFromServer(GlobalContacts.THEME_URL);
	}

	private void getDataFromServer(final String Url) {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, Url, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				Log.e("aa", Url);
				String result = responseInfo.result;
				if(Url.equals(GlobalContacts.STYLE_URL)){
					parseData(result, Url);
				}else if(Url.equals(GlobalContacts.EMOTION_URL)){
					parseData(result, Url);
				}else if(Url.equals(GlobalContacts.THEME_URL)){
					Log.e("aa", "THEME_URL");
					parseData(result, Url);
				}
			}
			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("aa", "failure"+Url);
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
			}
		});
	}

	
	//解析网络数据
	protected void parseData(String result, String Url) {
		Gson gson = new Gson();
		if(Url.equals(GlobalContacts.STYLE_URL)){
			mMusicStyleData = gson.fromJson(result, MusicStyleData.class);
			mStyleList = mMusicStyleData.style;
			mStyleAdapter = new StyleListAdapter();
			mGvStyle.setAdapter(mStyleAdapter);
		}else if(Url.equals(GlobalContacts.EMOTION_URL)){
			mMusicEmotionData = gson.fromJson(result, MusicEmotionData.class);
			mEmotionList = mMusicEmotionData.emotion;
			mEmotionAdapter = new EmotionListAdapter();
			mGvEmotion.setAdapter(mEmotionAdapter);
		}else if(Url.equals(GlobalContacts.THEME_URL)){
			mMusicThemeData = gson.fromJson(result, MusicThemeData.class);
			mThemeList = mMusicThemeData.theme;
			mThemeAdapter = new ThemeListAdapter();
			mGvTheme.setAdapter(mThemeAdapter);
		}
	}

	
	//-----------------------------监听器，适配器定义在此------------------------
	class StyleListAdapter extends BaseAdapter{

		BitmapUtils utils;
		public StyleListAdapter(){
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.jazz);
		}

		@Override
		public int getCount() {
			return mStyleList.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.grid_serach_item, null);
				holder = new ViewHolder();
				holder.iv_grid = (ImageView) convertView.findViewById(R.id.iv_search_grid);
				holder.tv_grid = (TextView) convertView.findViewById(R.id.tv_search_grid);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}

			MusicStyle style = mStyleList.get(position);

			String picUrl = GlobalContacts.SERVER_URL + style.url;
			holder.tv_grid.setText(style.style);
			utils.display(holder.iv_grid, picUrl);
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

	class EmotionListAdapter extends BaseAdapter{

		BitmapUtils utils;
		public EmotionListAdapter(){
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.jazz);
		}

		@Override
		public int getCount() {
			return mEmotionList.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.grid_serach_item, null);
				holder = new ViewHolder();
				holder.iv_grid = (ImageView) convertView.findViewById(R.id.iv_search_grid);
				holder.tv_grid = (TextView) convertView.findViewById(R.id.tv_search_grid);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}

			MusicEmotion emotion = mEmotionList.get(position);

			String picUrl = GlobalContacts.SERVER_URL + emotion.url;
			holder.tv_grid.setText(emotion.emotion);
			utils.display(holder.iv_grid, picUrl);
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
	
	class ThemeListAdapter extends BaseAdapter{

		BitmapUtils utils;
		public ThemeListAdapter(){
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.jazz);
		}

		@Override
		public int getCount() {
			Log.e("aa", mThemeList.size()+"");
			return mThemeList.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.grid_serach_item, null);
				holder = new ViewHolder();
				holder.iv_grid = (ImageView) convertView.findViewById(R.id.iv_search_grid);
				holder.tv_grid = (TextView) convertView.findViewById(R.id.tv_search_grid);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}

			MusicTheme theme = mThemeList.get(position);

			String picUrl = GlobalContacts.SERVER_URL + theme.url;
			holder.tv_grid.setText(theme.theme);
			utils.display(holder.iv_grid, picUrl);
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
		ImageView iv_grid;
		TextView tv_grid;
	}

}
