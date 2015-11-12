package com.cc.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.R;
import com.cc.entity.NewsItem;

public class FragmentNewsListAdapter extends BaseAdapter {
	private List<NewsItem> mData;
	private LayoutInflater mLayoutInflater; 
	public FragmentNewsListAdapter(List<NewsItem> mData,
			Context mContext) {
		super();
		this.mData = mData;
		this.mLayoutInflater = mLayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	public static class ViewHolder{
		public TextView titile,time;
		public ImageView img;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(arg1==null){
			arg1=this.mLayoutInflater.inflate(R.layout.cc_fragment_item, null);
			holder=new ViewHolder();
			holder.titile=(TextView)arg1.findViewById(R.id.textView1);
			holder.time=(TextView)arg1.findViewById(R.id.textView2); 
			holder.img=(ImageView)arg1.findViewById(R.id.imageView1);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder)arg1.getTag();
		}
		NewsItem item=this.mData.get(arg0);
		holder.titile.setText(item.getTitle());
		holder.time.setText(item.getTime());
		return arg1;
	}

}
