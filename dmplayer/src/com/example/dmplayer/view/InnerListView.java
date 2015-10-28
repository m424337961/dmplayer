package com.example.dmplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ListView;


public class InnerListView extends ListView {

	public InnerListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public InnerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InnerListView(Context context) {
		super(context);
	}
	
	@Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
	
	@Override
    protected void onDetachedFromWindow() {
      try {
        super.onDetachedFromWindow();
      } catch(IllegalArgumentException iae) {
        // Workaround for http://code.google.com/p/android/issues/detail?id=22751
      }
    }

}
