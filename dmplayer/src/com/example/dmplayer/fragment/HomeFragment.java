package com.example.dmplayer.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.dmplayer.R;
import com.example.dmplayer.dao.MyFavorDao;
import com.example.dmplayer.pager.BasePager;
import com.example.dmplayer.pager.FindPager;
import com.example.dmplayer.pager.MusicSheetPager;
import com.example.dmplayer.pager.MyMusicPager;
import com.example.dmplayer.pager.SearchPager;
import com.viewpagerindicator.TabPageIndicator;

public class HomeFragment extends BaseFragment {

	//--------------------控件定义在此---------------------
	private TabPageIndicator mIndicator;
	private ViewPager mViewPagerHome;
	//--------------------Adapter数据---------------------
	private String[] mTabIndex = new String[]{"我的音乐", "音乐架", "搜索", "发现"};
	private ArrayList<BasePager> mPagerList;
	public ArrayList<BasePager> getmPagerList() {
		return mPagerList;
	}

	public void setmPagerList(ArrayList<BasePager> mPagerList) {
		this.mPagerList = mPagerList;
	}

	private HomeAdapter mHomeAdapter;
	public int mFavorNumber;

	private View mRootView;
	
	private MyFavorDao mMyFavorDao;

	@Override
	public View initViews() {
		mRootView = View.inflate(mActivity, R.layout.fragment_home_main, null);
		mIndicator = (TabPageIndicator) mRootView.findViewById(R.id.indic_home);
		mViewPagerHome = (ViewPager) mRootView.findViewById(R.id.vp_home);
		return mRootView;
	}

	@Override
	public void initData() {
		mMyFavorDao = new MyFavorDao(mActivity);
		//初始化4个主页的viewpager
		mPagerList = new ArrayList<BasePager>();
		mPagerList.add(new MyMusicPager(mActivity));
		mPagerList.add(new MusicSheetPager(mActivity));
		mPagerList.add(new SearchPager(mActivity));
		mPagerList.add(new FindPager(mActivity));

		mHomeAdapter = new HomeAdapter();
		mViewPagerHome.setAdapter(mHomeAdapter);
		mIndicator.setViewPager(mViewPagerHome);
	}

	//show/hide Control
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		((MyMusicPager)mPagerList.get(0)).setFavorNumber();
		
	}
	
	//-----------------------------监听器，适配器定义在此------------------------
	public class HomeAdapter extends PagerAdapter{

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
		return false;
	}

}
