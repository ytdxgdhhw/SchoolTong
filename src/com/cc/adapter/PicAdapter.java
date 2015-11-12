package com.cc.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.cc.R;
import com.cc.application.CCApplication;
import com.cc.entity.News;

public class PicAdapter extends PagerAdapter {
	private List<News> list;
	private Context context;
	private ImageLoader loader;
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		News news=list.get(position);
		ImageView view=(ImageView)LayoutInflater.from(context).inflate(R.layout.layout_top_pic, null);
		
		ImageListener listener=ImageLoader.getImageListener(view, R.drawable.cc_default_news_img, R.drawable.cc_default_news_img_fail);
		loader.get(news.getImg(), listener);
		container.addView(view);
		return view;
	}

	public PicAdapter( Context context ,List<News> list) {
		super();
		this.list = list;
		this.context = context;
		loader=new ImageLoader(CCApplication.getInstance().getRequestQueue(), CCApplication.getInstance().getImageCache());
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

}
