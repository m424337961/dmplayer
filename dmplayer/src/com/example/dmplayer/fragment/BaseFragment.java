package com.example.dmplayer.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment{

	protected Activity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			backHandlerInterface = (BackHandlerInterface) getActivity();
		} catch (Exception e) {
			throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
		}
	}
	
	//fragment创建
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
		if (!(getActivity() instanceof BackHandlerInterface)) {
			throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
		} else {
			backHandlerInterface = (BackHandlerInterface) getActivity();
		}
	}

	//处理fragment布局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initViews();

	}

	//依附activiity创建完成
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
		initBackHandler();
	}

	public void initBackHandler() {
		backHandlerInterface.setSelectedFragment(this);
	}
	
	//子类必须实现初始化布局的方法
	public abstract View initViews();

	//初始化数据
	public void initData(){

	};
	
	protected BackHandlerInterface backHandlerInterface;  
	public interface BackHandlerInterface {  
		public void setSelectedFragment(BaseFragment backHandledFragment);  
	}  

	protected boolean mHandledPress = false;

	public abstract boolean onBackPressed();
}
