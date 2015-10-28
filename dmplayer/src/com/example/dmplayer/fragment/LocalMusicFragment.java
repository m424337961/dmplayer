package com.example.dmplayer.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.dmplayer.HomeActivity;
import com.example.dmplayer.R;
import com.example.dmplayer.domain.AudioInfo;
import com.example.dmplayer.pager.BasePager;
import com.example.dmplayer.pager.MyMusicPager;
import com.example.dmplayer.pager.localmusic.AlbumPager;
import com.example.dmplayer.pager.localmusic.ArtistPager;
import com.example.dmplayer.pager.localmusic.DirPager;
import com.example.dmplayer.pager.localmusic.SongPager;
import com.viewpagerindicator.TabPageIndicator;

public class LocalMusicFragment extends BaseFragment{

	private static List<AudioInfo> mLocaolSongList;
	private TabPageIndicator mIndciator;
	private ViewPager mViewPagerLocalMusic;
	private View mRootView;

	//--------------------Adapter数据---------------------
	private String[] mTabIndex = new String[]{"歌曲", "歌手", "专辑", "文件夹"};
	private ArrayList<BasePager> mPagerList;
	private LocalMusicAdapter mLocalMusicAdapter;
	
	private HomeActivity homeUI;

	@Override
	public View initViews() {
		homeUI = (HomeActivity) mActivity;
		mRootView = View.inflate(mActivity, R.layout.fragment_local_music, null);
		mIndciator = (TabPageIndicator) mRootView.findViewById(R.id.indic_local_muisc);
		mViewPagerLocalMusic = (ViewPager) mRootView.findViewById(R.id.vp_local_music);
		return mRootView;
	}

	@Override
	public void initData() {
		mLocaolSongList = MyMusicPager.getmLocaolSongList();

		//初始化4个主页的viewpager
		mPagerList = new ArrayList<BasePager>();
		mPagerList.add(new SongPager(mActivity));
		mPagerList.add(new ArtistPager(mActivity));
		mPagerList.add(new ArtistPager(mActivity));
		mPagerList.add(new DirPager(mActivity));

		mLocalMusicAdapter = new LocalMusicAdapter();
		mViewPagerLocalMusic.setAdapter(mLocalMusicAdapter);
		mIndciator.setViewPager(mViewPagerLocalMusic);
	}

	//-----------------------------监听器，适配器定义在此------------------------
	public class LocalMusicAdapter extends PagerAdapter{

		@Override
		public CharSequence getPageTitle(int position) {
			return mTabIndex[position];
		}

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagerList.get(position);
			pager.initData();
			container.addView(pager.mRootView);
			return pager.mRootView;
		}
	}

	@Override
	public boolean onBackPressed() {
		if (!mHandledPress) {
			homeUI.content.switchContentFragment(homeUI.content.mLocalMusicFragment, homeUI.content.mHomeFragment);
			homeUI.content.mHomeFragment.initBackHandler();
			mHandledPress = false;
            return true;
        }
		return false;
	}
}
