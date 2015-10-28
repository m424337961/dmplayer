package com.example.dmplayer.pager.localmusic;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dmplayer.R;
import com.example.dmplayer.domain.AudioInfo;
import com.example.dmplayer.pager.BasePager;

public class AlbumPager extends BasePager {

	private List<AudioInfo> mLocaolSongList;
	
	public AlbumPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	protected View initView() {
		return null;
	}

	@Override
	public void initData() {

	}

}
