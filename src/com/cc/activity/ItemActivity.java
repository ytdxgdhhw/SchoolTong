package com.cc.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.cc.R;
import com.cc.adapter.NewsPagerAdapter;
import com.cc.entity.Channel;
import com.cc.fragment.SchoolNewsFragment;

public class ItemActivity extends FragmentActivity {
	private PagerSlidingTabStrip tabs;
	private ViewPager pagers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cc_newslist);
		initView();
	
		initActionBar();
	}
	
private ActionBar actionBar;
	@SuppressLint("NewApi")
	private void initActionBar() {
		// TODO Auto-generated method stub
		actionBar=super.getActionBar();
		actionBar.show();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setHomeAsUpIndicator(R.drawable.back_move_details_normal_night);
		actionBar.setDisplayShowTitleEnabled(false);
		ColorDrawable colorDrawable=new ColorDrawable(Color.TRANSPARENT);
		actionBar.setIcon(colorDrawable);
		actionBar.setDisplayShowCustomEnabled(true);
		initData();
	}


	private void initView() {
		// TODO Auto-generated method stub
		tabs=(PagerSlidingTabStrip)findViewById(R.id.tabs);
		pagers=(ViewPager)findViewById(R.id.pagers);
	}
	@SuppressLint("NewApi")
	private void initData(){
		Channel channel=(Channel)getIntent().getSerializableExtra("channel");
		List<Channel> children=channel.getChildren();
		TextView tvTitle=new TextView(this);
		tvTitle.setText(channel.getcTitle());
		tvTitle.setTextColor(Color.WHITE);
		tvTitle.setTextSize(20);
		tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tvTitle.setGravity(Gravity.LEFT);
		actionBar.setCustomView(tvTitle);
		//设置标题
//		actionBar.setTitle(channel.getcTitle());
		//为ViewPager构造数据
		List<Fragment> viewPagerData=new ArrayList<Fragment>();
		for(int i=0;i<children.size();i++){
			Fragment v=new SchoolNewsFragment();
			Bundle bundle=new Bundle(); 
			bundle.putString("type", children.get(i).getcId()); 
			v.setArguments(bundle);
			viewPagerData.add(v);
		}
//		ViewPagerBaserAdapter adapter=new ViewPagerBaserAdapter(viewPagerData,children);
		NewsPagerAdapter adapter=new NewsPagerAdapter(getSupportFragmentManager(),
				viewPagerData,children);
		pagers.setAdapter(adapter);
		tabs.setViewPager(pagers);
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
