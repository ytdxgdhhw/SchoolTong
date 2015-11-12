package com.cc.activity;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.cc.R;
import com.cc.SubjectActivity;
import com.cc.adapter.ZhuanOneAdapter;
import com.cc.application.CCApplication;
import com.cc.entity.ThemeNews;
import com.cc.utils.UrlUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class Zhuan_01Activity extends Activity implements OnRefreshListener2<ListView>, OnItemClickListener {
	private RequestQueue requestQueue;
	private ImageLoader imageLoader;
	private PullToRefreshListView listView;
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuan_layout );
		requestQueue=CCApplication.getInstance().getRequestQueue();
		imageLoader=new ImageLoader(requestQueue,CCApplication.getInstance().getImageCache());
		initListView();
		initAction();
	}
		@SuppressLint("NewApi")
	private void initAction() {
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
		TextView tvTitle=new TextView(this);
		tvTitle.setText("更多专题");
		tvTitle.setTextColor(Color.WHITE);
		tvTitle.setTextSize(20);
		tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tvTitle.setGravity(Gravity.LEFT);
		actionBar.setCustomView(tvTitle);
	}
	private List<ThemeNews> listNews;
	private ZhuanOneAdapter adapter;  

	private void initListView() {
		// TODO Auto-generated method stub
		
		listView=(PullToRefreshListView)findViewById(R.id.listZhuan);
		listNews=new ArrayList<ThemeNews>();
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(this);
		listView.setOnItemClickListener(this);
		adapter=new ZhuanOneAdapter(this, listNews);
		listView.setAdapter(adapter);  
		listView.setRefreshing();
		loadData();

		
	}
	private void loadData() {
		// TODO Auto-generated method stub
		StringRequest request=new StringRequest(UrlUtil.SCHOOL_NEWS_URL+"?dataType=subject&pageCount=2", 
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
						Gson gson=new Gson();
						List<ThemeNews> list=gson.fromJson(arg0, new TypeToken<List<ThemeNews>>(){}.getType());
						listNews.addAll(list);
						
						adapter.notifyDataSetChanged();
					}
				}, 
				new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(Zhuan_01Activity.this, "网络加载失败！", Toast.LENGTH_SHORT).show();
					}
				});
		requestQueue.add(request);
		adapter.notifyDataSetChanged();
	
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		listView.onRefreshComplete();
	}
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		more=listNews.get(arg2-1);
		switch(arg2){
		case 1:
			turn1();
			break;
		case 2:
			turn2();
			break;
		}
	
	}
	private ThemeNews more;
	
	private void turn1() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,SubjectActivity.class);
		intent.putExtra("more", more);
		this.startActivity(intent);
	}
	private void turn2() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,SubjectActivity.class);
		intent.putExtra("more", more);
		this.startActivity(intent);
	}
	
}
