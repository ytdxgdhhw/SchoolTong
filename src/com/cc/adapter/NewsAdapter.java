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
import com.cc.entity.News;



public class NewsAdapter extends BaseAdapter {
	private List<News> list;
	private Context context;
	private ImageLoader mLoader;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	private class ViewHolder{
		public ImageView ivPic;
		public TextView tvTitle,tvTime;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		News news=list.get(arg0);
		ViewHolder holder=null;
		if(arg1==null){
			holder=new ViewHolder();
			arg1=LayoutInflater.from(context).inflate(R.layout.layout_news, null);
			holder.ivPic=(ImageView)arg1.findViewById(R.id.ivPic);
			holder.tvTitle=(TextView)arg1.findViewById(R.id.tvTitle);
			holder.tvTime=(TextView)arg1.findViewById(R.id.tvTime);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder)arg1.getTag();
		}
		//holder.ivPic.setImageResource(news.getImgRes());
		holder.tvTime.setText(news.getTime());
		holder.tvTitle.setText(news.getTitle());
		
		ImageListener listener=ImageLoader.getImageListener(holder.ivPic, R.drawable.cc_default_news_img, R.drawable.cc_default_news_img_fail);
		this.mLoader.get(news.getImg(), listener);
		return arg1;
	}

	public NewsAdapter(List<News> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		this.mLoader=new ImageLoader(CCApplication.getInstance().getRequestQueue(), CCApplication.getInstance().getImageCache());
	}

}
