package com.cc.adapter;

import java.util.ArrayList;
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
import com.cc.entity.News;

public class SubjectAdapter extends BaseAdapter {

	private Context context;
	private List<News> listNews=new ArrayList<News>();
	private ImageLoader mImageLoader;
	public SubjectAdapter(Context context, List<News> listNews) {
		super();
		this.context = context;
		this.listNews = listNews;
		this.mImageLoader=new ImageLoader(CCApplication.getInstance().getRequestQueue(),
				CCApplication.getInstance().getImageCache());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listNews.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listNews.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder{
		private TextView title;
		private ImageView img;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		News news=listNews.get(position);
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.subject_layout,null);
			viewHolder.title=(TextView)convertView.findViewById(R.id.ivTitle);
			viewHolder.img=(ImageView)convertView.findViewById(R.id.ivImg);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder)convertView.getTag();
		}
		viewHolder.title.setText(news.getTitle());
		
		ImageListener listener=ImageLoader.
				getImageListener(
						viewHolder.img, 
						R.drawable.cc_default_news_img, 
						R.drawable.cc_default_news_img_fail);
		this.mImageLoader.get(news.getImg(), listener);
		
		return convertView;
	}
	
	

}
