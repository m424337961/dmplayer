package com.example.dmplayer.pager.localmusic;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.dmplayer.pager.BasePager;

public class DirPager extends BasePager {

	public DirPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	protected View initView() {
		TextView text = new TextView(mActivity);
		text.setText("ÎÄ¼þ¼Ð");
		text.setTextColor(Color.BLACK);
		text.setTextSize(22);
		text.setGravity(Gravity.CENTER);
		return text;
	}

	@Override
	public void initData() {

	}

}
