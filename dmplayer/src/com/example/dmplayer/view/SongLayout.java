package com.example.dmplayer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dmplayer.R;

@SuppressLint("NewApi")
public class SongLayout extends LinearLayout {

	private ImageView mImageView;
	private TextView mTvTitle;
	private TextView mTvNum;
	private String mTitle;
	private String mNumber;
	private int mDrawable;
	
	public SongLayout(Context context) {
		super(context);
		initView(context);
	}

	public SongLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTitle = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.dmplayer", "mytitle");
		mNumber = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.dmplayer", "number");
		String drawable = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.dmplayer", "mysrc");
		String replace = drawable.replace("@", "");
		mDrawable = Integer.parseInt(replace);
		initView(context);
	}

	
	public SongLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {
		View.inflate(context, R.layout.song_layout, this);
		mImageView = (ImageView) findViewById(R.id.iv_song_item);
		mTvTitle = (TextView) findViewById(R.id.tv_song_item_title);
		mTvNum = (TextView) findViewById(R.id.tv_song_item_number);
		
		mTvTitle.setText(mTitle);
		mTvNum.setText(mNumber);
		mImageView.setBackgroundResource(mDrawable);
	}
	
	public void setTitle(String title){
		mTvTitle.setText(title);
	}
	
	public void setNumber(int number){
		mTvNum.setText(number + "สื");
	}
	
}
