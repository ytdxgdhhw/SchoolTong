package com.cc.fragment;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
	//用于表明当前Fragment是否可见
	//Fragment懒加载   
	protected boolean isVisible;
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			isVisible=true;
			LazyLoadData();
		}else{
			isVisible=false;
		}
	}
	public abstract void LazyLoadData();
}
