package com.cc;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.cc.activity.SubjectMessageActivity;
import com.cc.adapter.SubjectAdapter;
import com.cc.application.CCApplication;
import com.cc.entity.News;
import com.cc.entity.ThemeNews;
import com.cc.utils.StringPostRequest;
import com.cc.utils.UrlUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class SubjectActivity extends Activity implements OnRefreshListener2<ListView>, OnItemClickListener {
	private RequestQueue requestQueue;
	private ThemeNews more;
	private ImageLoader imageLoader; 
	private SubjectAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);
		Intent intent=super.getIntent();
		requestQueue=CCApplication.getInstance().getRequestQueue();
		imageLoader=new ImageLoader(requestQueue, CCApplication.getInstance().getImageCache());
		if(intent!=null){
			more=(ThemeNews)intent.getSerializableExtra("more");
		
		}
		initTopNew();
		initAction();
		intiListView();
		
	}
	private PullToRefreshListView listView;
	private void intiListView() {
		// TODO Auto-generated method stub
		adapter=new SubjectAdapter(this,mList);
		listView=(PullToRefreshListView)findViewById(R.id.subView);
		listView.setMode(Mode.BOTH);	
		listView.setOnRefreshListener(this);
		listView.setRefreshing(true);
		
		listView.setAdapter(adapter);
		initTheme();
		listView.setOnItemClickListener(this);
	}

	private List<ThemeNews> list=null;
	private List<News> mList=new ArrayList<News>();
	private void initTheme() {
		// TODO Auto-generated method stub
		
		StringPostRequest postRequest=new StringPostRequest(UrlUtil.SCHOOL_NEWS_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
						Gson gson=new Gson();
						List<News> date=gson.fromJson(
								arg0, 
								new TypeToken<ArrayList<News>>(){}.getType());
//						more=list.get(0);
						if(date!=null){
							mList.clear();
							mList.addAll(date);
							adapter.notifyDataSetChanged();
						}
					}
				}, 
				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(SubjectActivity.this, "Õ¯¬Áº”‘ÿ ß∞‹£°", Toast.LENGTH_SHORT).show();
					}
				});
		postRequest.putParams("dataType", "news");
		postRequest.putParams("pageTag",more.getSubject_id()+"");
		postRequest.putParams("pageTageFlag", "1");
		requestQueue.add(postRequest);		
	}
	private ActionBar actionBar;
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
		tvTitle.setText("◊®Ã‚");
		tvTitle.setTextColor(Color.WHITE);
		tvTitle.setTextSize(20);
		tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tvTitle.setGravity(Gravity.LEFT);
		actionBar.setCustomView(tvTitle);
	}
	private TextView tvtt,tvvv;
	private ImageView ivtt;
	private void initTopNew() {
		// TODO Auto-generated method stub
		tvtt=(TextView)findViewById(R.id.tvtt);
		tvvv=(TextView)findViewById(R.id.tvvv);
		ivtt=(ImageView)findViewById(R.id.ivtt);
		tvtt.setText(more.getSubject_title());
		tvvv.setText(more.getSubject_detail());
		ImageListener listener=ImageLoader.getImageListener(ivtt, 
				R.drawable.cc_default_news_img, R.drawable.cc_default_news_img_fail);
		imageLoader.get(more.getSubject_url(), listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private Handler mHandler=new Handler();
	@Override
	public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				refreshView.onRefreshComplete();
			}
		}, 3000);
	}

	@Override
	public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
	mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				refreshView.onRefreshComplete();
			}
		}, 3000);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		News subjectNews=mList.get(arg2-1);
		Toast.makeText(this, subjectNews.getTitle(),Toast.LENGTH_SHORT).show();
		
		Intent intent=new Intent(this,SubjectMessageActivity.class);
		
		intent.putExtra("news", subjectNews);
		startActivity(intent);
	}
}
