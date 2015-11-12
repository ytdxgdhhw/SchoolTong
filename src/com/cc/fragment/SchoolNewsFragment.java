package com.cc.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cc.R;
import com.cc.activity.SchoolNewsDetailActivity;
import com.cc.adapter.FragmentNewsListAdapter;
import com.cc.application.CCApplication;
import com.cc.dao.NewsItemDao;
import com.cc.entity.NewsItem;
import com.cc.utils.ExecutorManager;
import com.cc.utils.StringPostRequest;
import com.cc.utils.UrlUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class SchoolNewsFragment extends BaseFragment implements OnRefreshListener2<ListView>, OnItemClickListener {
	private FragmentNewsListAdapter adapter;
	private List<NewsItem> mData;  
	private long startTime;//上一次加载时间
	private boolean isInit=false;//表明fragment的控件  
	private 	PullToRefreshListView listView;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.mData=new ArrayList<NewsItem>();
		this.adapter=new FragmentNewsListAdapter(mData,getActivity());
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.cc_fragment_news_list, null);
		 listView=
				(PullToRefreshListView)v.findViewById(R.id.newslist);
		listView.setAdapter(adapter);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(this);
		listView.setOnItemClickListener(this);
		startTime=System.currentTimeMillis();
		isInit=true;
		//判断--第一页加载
		if(isVisible){
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					refreshData();
				}
			}, 500);
			
		}
		return v;
	}
	/*
	 * 刷新数据的方法
	 */
	private void updateLocalData(final List<NewsItem> items){
		ExecutorManager.getInstance().execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				NewsItemDao itemDao=new NewsItemDao(getActivity().getApplicationContext());
				itemDao.addNewsItem(items);
			}
		});
	}
	public void refreshData(){
		listView.setRefreshing();
		String type=getArguments().getString("type");
		NewsItemDao newsDao=new NewsItemDao(getActivity().getApplicationContext());
		List<NewsItem> list=newsDao.findNewsItemByPageTag(type);
		if(list.size()>0){
			mData.addAll(list);
			adapter.notifyDataSetChanged();
		}
		StringPostRequest request=new StringPostRequest(
				UrlUtil.SCHOOL_NEWS_URL, 
				new Listener<String>() {
 
					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
						Gson gson=new Gson();
						List<NewsItem> items=gson.fromJson(arg0, new TypeToken<ArrayList<NewsItem>>(){}.getType());
						if(items!=null&&items.size()>0){
							mData.clear();
							mData.addAll(items);
							adapter.notifyDataSetChanged();
							updateLocalData(items);
						}
						listView.onRefreshComplete();
					}
				}, 
				new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						listView.onRefreshComplete();
					}
				});
//		String type=getArguments().getString("type");
		request.putParams("dataType", "news");
		request.putParams("pageTag",type);
		request.putParams("pageTagFlag", "0");
		request.putParams("pageNum", "0");
	//	request.setTag("toPostforNews");
		CCApplication.getInstance().getRequestQueue().add(request);
	}
/* 
 * 加载数据的方法
 * (non-Javadoc)
 * @see com.jredu.cc.fragment.BaseFragment#LazyLoadData()
 */
	@Override
	public void LazyLoadData() {
		// TODO Auto-generated method stub
		long endTime=System.currentTimeMillis();
		if(isVisible&&isInit){
		if((mData.size()>0&&endTime-startTime>1000*60)||mData.size()<=0);
			refreshData();
			startTime=System.currentTimeMillis();
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
	// TODO Auto-generated method stub
	 
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
	// TODO Auto-generated method stub
	
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(getActivity(),SchoolNewsDetailActivity.class);
		NewsItem item=(NewsItem)adapter.getItem(arg2-1);
		intent.putExtra("news",item );
		startActivity(intent);
	}
	

}
