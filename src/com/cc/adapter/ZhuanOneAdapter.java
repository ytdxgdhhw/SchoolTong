package com.cc.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.cc.R;
import com.cc.application.CCApplication;
import com.cc.entity.ThemeNews;

public class ZhuanOneAdapter extends BaseAdapter {
	private Context context;
	private List<ThemeNews> data;
	private ImageLoader imageLoader;
	public ZhuanOneAdapter(Context context, List<ThemeNews> data) {
		super();
		this.context = context;
		this.data = data;
		this.imageLoader = new ImageLoader(CCApplication.getInstance().getRequestQueue(), 
				CCApplication.getInstance().getImageCache());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	private  class ViewHolder{
		public TextView tvoneTitle,tvoneSub;
		public ImageView ivone;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		ThemeNews news=data.get(arg0);
		if(arg1==null){
			holder=new ViewHolder();
			arg1=LayoutInflater.from(context).inflate(R.layout.zhuanone_layout, null);
			holder.tvoneTitle=(TextView)arg1.findViewById(R.id.tvoneTitle);
			holder.tvoneSub=(TextView)arg1.findViewById(R.id.tvoneSub);
			holder.ivone=(ImageView)arg1.findViewById(R.id.ivone);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder)arg1.getTag();
		}
		holder.tvoneTitle.setText(news.getSubject_title());
		holder.tvoneSub.setText(news.getSubject_detail());
		ImageListener listener=imageLoader.getImageListener(holder.ivone,
				 R.drawable.cc_default_news_img, R.drawable.cc_default_news_img_fail);
		this.imageLoader.get(news.getSubject_url(), listener);
		return arg1;
	}

}
