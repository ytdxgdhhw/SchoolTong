package com.cc.adapter;

import java.util.List;

import com.cc.activity.FlashActivity;
import com.cc.activity.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout.LayoutParams;

public class ImageViewPagerAdapter extends PagerAdapter {
	

	private Context context;
	private List<Integer> data;
	public ImageViewPagerAdapter(Context context,List<Integer> data){
		this.context=context;
		this.data=data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View)object);
		
	}
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		// TODO Auto-generated method stub
		int resId=data.get(position);
		ImageView ivImg=new ImageView(context);
		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		ivImg.setLayoutParams(params);//创建layout
		ivImg.setScaleType(ScaleType.FIT_XY);
		ivImg.setImageResource(resId);
		ivImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(position==getCount()-1){
					next();
				}
			}
		});
		container.addView(ivImg);//将View添加到ViewPager上
		return ivImg;
	}
	private void next(){
		FlashActivity mActivity=(FlashActivity)context;
		Intent intent=new Intent(context,MainActivity.class);
		mActivity.startActivity(intent);
		mActivity.finish();
	}
}
