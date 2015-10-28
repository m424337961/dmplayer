package com.example.dmplayer.pager;

import android.app.Activity;
import android.view.View;

public abstract class BasePager {

	protected Activity mActivity;
	public View mRootView;
	
	public BasePager(Activity mActivity) {
		this.mActivity = mActivity;
		mRootView = initView();
	}

	protected abstract View initView() ;
	public abstract void initData();
	
}
