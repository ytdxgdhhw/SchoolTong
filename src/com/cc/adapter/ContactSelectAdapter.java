package com.cc.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cc.R;
import com.cc.entity.Contact;

public class ContactSelectAdapter extends BaseAdapter {
	private List<Contact> mData;
	private Context mContext;
	private onContactSelectedListener listener;





	public void setListener(onContactSelectedListener listener) {
		this.listener = listener;
	}

	public ContactSelectAdapter(List<Contact> mData, Context mContext) {
		super();
		this.mData = mData;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(arg1==null){
			arg1=LayoutInflater.from(mContext).inflate(R.layout.list_view_item, null);
			holder=new ViewHolder();
			holder.name=(TextView)arg1.findViewById(R.id.name);
			holder.ck=(CheckBox)arg1.findViewById(R.id.ck);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder)arg1.getTag();
		}
		final Contact contact=(Contact)getItem(arg0);
		holder.name.setText(contact.getName());
		if(contact.getType()==Contact.ALL_MAJOR||contact.getType()==Contact.ALL_ROLE){
			holder.ck.setVisibility(View.GONE);
		}else{
			holder.ck.setVisibility(View.VISIBLE);
		}
		if(contact.isSelected()){
			holder.ck.setChecked(true);
		}else{
			holder.ck.setChecked(false);
		}
		holder.ck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				CheckBox ck=(CheckBox)arg0;
				contact.setSelected(ck.isChecked());
				if(listener!=null){
					listener.onContactSelectChanged(contact);
				}
			}
		});
		return arg1;
	}
	public static class ViewHolder{
		public TextView name;
		public CheckBox ck;
	}
	public interface onContactSelectedListener{
		public void onContactSelectChanged(Contact contact);
	}
}
