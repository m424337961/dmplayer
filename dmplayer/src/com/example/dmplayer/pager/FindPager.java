package com.example.dmplayer.pager;

import com.example.dmplayer.HomeActivity;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class FindPager extends BasePager {

	public FindPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	protected View initView() {
		TextView text = new TextView(mActivity);
		text.setText("·¢ÏÖ");
		text.setTextColor(Color.BLACK);
		text.setTextSize(22);
		text.setGravity(Gravity.CENTER);
		return text;
	}

	@Override
	public void initData() {

	}

}
