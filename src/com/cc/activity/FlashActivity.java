package com.cc.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout.LayoutParams;

import com.cc.R;
import com.cc.utils.Constant;

public class FlashActivity extends Activity  {
	private ViewPager listView;

	private int[] drawables={
			R.drawable.wip_bk_dust,
			R.drawable.wip_bk_fog,
			R.drawable.wip_bk_na,
			R.drawable.wip_bk_snow
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_layout);
		listView=(ViewPager)findViewById(R.id.vpFlash);
		final List<View> list=new ArrayList<View>();
		for(int i=0;i<drawables.length;i++){
			ImageView v=new ImageView(this);
			LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			v.setLayoutParams(params);//´´½¨layout
			v.setScaleType(ScaleType.FIT_XY);
			v.setImageResource(drawables[i]);
			list.add(v);
		}
		IndicatorPageAdapter adapter=new IndicatorPageAdapter(list);
		listView.setAdapter(adapter);
		listView.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if(arg0==drawables.length-1){
					ImageView imageView=(ImageView)list.get(arg0);
					imageView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							SharedPreferences sp=getSharedPreferences(Constant.SP_FILE_NAME, MODE_PRIVATE);
							SharedPreferences.Editor editor=sp.edit();
							editor.putBoolean("isFirst", false);
							editor.commit();
							startActivity(new Intent(FlashActivity.this,MainActivity.class));
							finish();
						}
					});
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}


	private class IndicatorPageAdapter extends PagerAdapter{
		private List<View> views;
		public IndicatorPageAdapter(List<View> views) {
			super();
			this.views = views;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(this.views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			View v=views.get(position);
			container.addView(v);
			return v;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
	}

}
