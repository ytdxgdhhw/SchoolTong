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
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.cc.InfoActivity;
import com.cc.InternetActivity;
import com.cc.R;
import com.cc.SubjectActivity;
import com.cc.adapter.NewsAdapter;
import com.cc.adapter.PicAdapter;
import com.cc.application.CCApplication;
import com.cc.entity.Channel;
import com.cc.entity.DataManager;
import com.cc.entity.News;
import com.cc.entity.ThemeNews;
import com.cc.entity.CCUser;
import com.cc.utils.StringPostRequest;
import com.cc.utils.UrlUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.slidingmenu.lib.SlidingMenu;


public class MainActivity extends Activity implements OnRefreshListener2<ListView>,OnClickListener{
	private PullToRefreshListView listView;
	private List<News> listNews;
	private NewsAdapter adapter;
	private RequestQueue requestQueue;
	private ImageLoader loader;
	private SlidingMenu slidingMenu;
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		requestQueue=CCApplication.getInstance().getRequestQueue();
		loader=new ImageLoader(requestQueue, 
				CCApplication.getInstance().getImageCache());

		initListView();
		initHeaderView();
		initAction();
		initSlidingMenu();
		initSlideClick();
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
		tvTitle.setText("校园通");
		tvTitle.setTextColor(Color.WHITE);
		tvTitle.setTextSize(20);
		tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tvTitle.setGravity(Gravity.LEFT);
		actionBar.setCustomView(tvTitle);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case android.R.id.home:
			slidingMenu.toggle();
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void initSlidingMenu() {
		// TODO Auto-generated method stub
		slidingMenu=new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setMenu(R.layout.left_menu_layout);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_left_offset);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setTouchModeAbove(slidingMenu.TOUCHMODE_MARGIN);//边缘模式     
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);//设置阴影 效果
	}
	private ViewPager viewPager;
	private RadioGroup radioGroup;
	private List<News> listPic;
	private PicAdapter picAdapter;
	private TextView tvTitle;
	private TextView tvTime;
	private ImageView ivPic;
	private TextView tvMore;
	private RelativeLayout rl;
	private void initHeaderView() {
		// TODO Auto-generated method stub
		ListView view=listView.getRefreshableView();
		View headerView=LayoutInflater.from(this).inflate(R.layout.layout_header, null);
		viewPager=(ViewPager)headerView.findViewById(R.id.vpPic);
		tvTitle=(TextView)headerView.findViewById(R.id.tvTitle);
		tvTime=(TextView)headerView.findViewById(R.id.tvTime);
		ivPic=(ImageView)headerView.findViewById(R.id.ivPic);
		listPic=new ArrayList<News>();
		tvMore=(TextView)headerView.findViewById(R.id.tvMore);
		tvMore.setOnClickListener(this);
		rl=(RelativeLayout)headerView.findViewById(R.id.rl);
		rl.setOnClickListener(this);
		picAdapter=new PicAdapter(this, listPic);
		radioGroup=(RadioGroup)headerView.findViewById(R.id.rgTop);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(arg1);
			}
		});
		viewPager.setAdapter(picAdapter);
		loadPicRes();
		for(int i=0;i<2;i++){
			RadioButton v=(RadioButton)LayoutInflater.from(this).inflate(R.layout.radiobutton, null);
			v.setId(i);
			radioGroup.addView(v);
		}
		radioGroup.check(0);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				radioGroup.check(arg0);
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
		view.addHeaderView(headerView);
		initTheme();
	
	}
	private List<ThemeNews> list=null;
	private ThemeNews more;
	private void initTheme() {
		// TODO Auto-generated method stub
		StringRequest request=new StringRequest(UrlUtil.SCHOOL_NEWS_URL+"?dataType=subject&pageCount=1",
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
						Gson gson=new Gson();
						list=gson.fromJson(arg0, new TypeToken<List<ThemeNews>>(){}.getType());
						more=list.get(0);
						tvTitle.setText(list.get(0).getSubject_title());
						tvTime.setText(list.get(0).getSubject_date());
						
						ImageListener listener=ImageLoader.getImageListener(ivPic,R.drawable.cc_default_news_img, R.drawable.cc_default_news_img_fail);
						loader.get(list.get(0).getSubject_url(), listener);
					}
				}, 
				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "网络加载失败！", Toast.LENGTH_SHORT).show();
					}
				});
		requestQueue.add(request);
	}

	private void loadPicRes() {
		// TODO Auto-generated method stub
		StringRequest request=new StringRequest(UrlUtil.SCHOOL_NEWS_URL+"?dataType=news&isTop=1",
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
						Gson gson=new Gson();
						List<News> list=gson.fromJson(arg0, new TypeToken<List<News>>(){}.getType());
						listPic.addAll(list);
						
						picAdapter.notifyDataSetChanged();
					}
				}, 
				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "网络加载失败！", Toast.LENGTH_SHORT).show();
					}
				});
		requestQueue.add(request);
		picAdapter.notifyDataSetChanged();
	}

	private void initListView() {
		// TODO Auto-generated method stub
		
		listView=(PullToRefreshListView)super.findViewById(R.id.lvNews);
		listNews=new ArrayList<News>();
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(this);
		adapter=new NewsAdapter(listNews, this);
		listView.setAdapter(adapter);
		listView.setRefreshing();
		loadData();

	
	}
	
	private void loadData() {
		// TODO Auto-generated method stub
		
		StringRequest request=new StringRequest(UrlUtil.SCHOOL_NEWS_URL+"?dataType=news",
				new Listener<String>() {

					@Override
					public void onResponse(String result) {
						// TODO Auto-generated method stub
						if(result!=null){
							Gson gson=new Gson();
							List<News> data=gson.fromJson(result, new TypeToken<ArrayList<News>>(){}.getType());
						
							if(data!=null){
								listNews.clear();
								listNews.addAll(data);
								adapter.notifyDataSetChanged();
								
							}
							listView.onRefreshComplete();
							//list.addAll(getNewsList(result));
							
						}
					 
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
						listView.onRefreshComplete();
					}
				}) ;
		requestQueue.add(request);
	}

	

	private int pageNo=1;
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		pageNo=1;
		loadData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		pageNo++;
		upData();
	}
	private void upData(){
		StringPostRequest request=new StringPostRequest(UrlUtil.SCHOOL_NEWS_URL+"?dataType=news",
				new Listener<String>() {

					@Override
					public void onResponse(String result) {
						// TODO Auto-generated method stub
						if(result!=null){
							Gson gson=new Gson();
							List<News> data=gson.fromJson(result, new TypeToken<ArrayList<News>>(){}.getType());
							if(data!=null){
								
								listNews.addAll(data);
								adapter.notifyDataSetChanged();
								
							}
							//list.addAll(getNewsList(result));
							
						}
						listView.onRefreshComplete();
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
						listView.onRefreshComplete();
					}
				}) ;
		request.putParams("pageNo", pageNo+"");
		requestQueue.add(request);
	}
	private DataManager dataManager;
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.tvMore:
			turnZhuan();
		break;
		case R.id.rl:
			turnZhuan2();
			break;
		case R.id.l1:	
			turn3();
			break;
		case R.id.l2:
			turn6();
			break;
		case R.id.l3:
			turn7();
			break;
		case R.id.l4:
			turn8();
			break;
		case R.id.l5:
			turn9();
			break;
		case R.id.tvPlace:
			
			turn4();
			break;
		case R.id.ivTouxiang:
			turn5();
			break;
		case R.id.llInfo:
			turn10();
			break;
		}
	}
	private void turn9() {
		// TODO Auto-generated method stub
		Intent intent =new Intent(this,InternetActivity.class);
		startActivity(intent);
	}
	private void turn10() {
		// TODO Auto-generated method stub
		if(CCApplication.getInstance().getUser()!=null){
			Intent intent=new Intent(this,InfoActivity.class);
			startActivity(intent);
		}else{
			Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
		}
	}
	private void turn5(){
		if(CCApplication.getInstance().getUser()!=null){
			Intent intent=new Intent(this,UserActivity.class);
			startActivity(intent);
		}else{
			Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
		}
	}
	private void turn4() {
		// TODO Auto-generated method stub
		if(CCApplication.getInstance().getUser()==null){
		
		Intent intent=new Intent(this,LoginActivity.class);
		startActivityForResult(intent,0);
		}
	}
	private void turn3() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,ItemActivity.class);
		Channel channel=dataManager.getSchoolChannel();
		intent.putExtra("channel", channel);
		intent.putExtra("title", channel.getcTitle());
		startActivity(intent);
	}
	private void turn6() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,ItemActivity.class);
		Channel channel=dataManager.getSchoolChannel1();
		intent.putExtra("channel", channel);
		intent.putExtra("title", channel.getcTitle());
		startActivity(intent);
	}
	private void turn7() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,ItemActivity.class);
		Channel channel=dataManager.getSchoolChannel2();
		intent.putExtra("channel", channel);
		intent.putExtra("title", channel.getcTitle());
		startActivity(intent);
	}
	private void turn8() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,ItemActivity.class);
		Channel channel=dataManager.getSchoolChannel3();
		intent.putExtra("channel", channel);
		intent.putExtra("title", channel.getcTitle());
		startActivity(intent);
	}
	private void turnZhuan2() {
		// TODO Auto-generated method stub
		
		Intent intents=new Intent(this,SubjectActivity.class);
		
		intents.putExtra("more", more);
		this.startActivity(intents);
		
	}
	private void turnZhuan() {
		Intent intent=new Intent(this,Zhuan_01Activity.class);
		this.startActivity(intent);
	}
	private  LinearLayout l1,l2,l3,l4,l5;
	private TextView tvPlace;
	private ImageView ivTouxiang;
	private LinearLayout llInfo;
	private void initSlideClick(){
		dataManager=new DataManager();
		l1=(LinearLayout)findViewById(R.id.l1);
		l2=(LinearLayout)findViewById(R.id.l2);
		l3=(LinearLayout)findViewById(R.id.l3);
		l4=(LinearLayout)findViewById(R.id.l4);
		l5=(LinearLayout)findViewById(R.id.l5);
		tvPlace=(TextView)findViewById(R.id.tvPlace);
		tvPlace.setOnClickListener(this);	
		llInfo=(LinearLayout)findViewById(R.id.llInfo);
		llInfo.setOnClickListener(this);
		CCUser cCUser=CCApplication.getInstance().getUser();
	
		if(cCUser!=null){
			tvPlace.setText(cCUser.getUno());
		}
		ivTouxiang=(ImageView)findViewById(R.id.ivTouxiang);	
		//更新头像
		//CCUser u=CCApplication.getInstance().getUser();
		
		l1.setOnClickListener(this);
		l2.setOnClickListener(this);
		l3.setOnClickListener(this);
		l4.setOnClickListener(this);
		l5.setOnClickListener(this);
		ivTouxiang.setOnClickListener(this);
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0&&resultCode==RESULT_OK){
			CCUser cCUser=CCApplication.getInstance().getUser();
			tvPlace.setText(cCUser.getUno());
		}
	}
	private long endTime;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			long currentTime=System.currentTimeMillis();
			if(currentTime-endTime>2000){
				Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
				endTime=System.currentTimeMillis();
			
			}else{
				System.exit(0);
			}
		}
		
		
		return false;
	}

}
