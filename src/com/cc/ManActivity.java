package com.cc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cc.adapter.ContactSelectAdapter;
import com.cc.adapter.ContactSelectAdapter.onContactSelectedListener;
import com.cc.application.CCApplication;
import com.cc.entity.Contact;
import com.cc.utils.StringPostRequest;
import com.cc.utils.UrlUtil;

public class ManActivity extends Activity implements OnItemClickListener,onContactSelectedListener   {
	private ListView lvSelector;
	private List<Contact> mData;
	 
	private ContactSelectAdapter adapterTwo;
	private TextView rightNow;
	private RequestQueue requestQueue;
	private LinearLayout mNavContainer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_man);
		initAction();
		requestQueue=CCApplication.getInstance().getRequestQueue();
		lvSelector=(ListView)findViewById(R.id.lvSelector);
		mNavContainer=(LinearLayout)findViewById(R.id.navContainer);
		rightNow=(TextView)findViewById(R.id.rightNow);
		lvSelector.setOnItemClickListener(this);
		initDataOne();
		
	
		
	
	}
	private void initDataOne() {
		Contact root=new Contact(null, "校园通", 0);
		addNav(root);
		List<Contact> rootChild=new ArrayList<Contact>();
		Contact majorAll=new Contact(null,"专业",Contact.ALL_MAJOR);
		Contact roleAll=new Contact(null,"职务",Contact.ALL_ROLE);
		rootChild.add(majorAll);
		rootChild.add(roleAll);
		root.setNodes(rootChild);
		
		mData=new ArrayList<Contact>();
		mData.add(new Contact("01","专业",Contact.ALL_MAJOR));
		mData.add(new Contact("02","职务",Contact.ALL_ROLE));
		adapterTwo=new ContactSelectAdapter(mData, this);
		lvSelector.setAdapter(adapterTwo);
		adapterTwo.setListener(this);
		adapterTwo.notifyDataSetChanged();
		
	}
private void addNav(Contact root) {
		
		TextView navRoot=new TextView(this);
		navRoot.setText(root.getName());
		navRoot.setTag(root);
		navRoot.setTextSize(20);
//		navRoot.
		LinearLayout.LayoutParams lp=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		lp.gravity=Gravity.CENTER_VERTICAL;
		mNavContainer.addView(navRoot);
		navRoot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//找到被点击的位置,遍历容器
				int index=mNavContainer.indexOfChild(arg0);
				int length=mNavContainer.getChildCount()-index-1;
				mNavContainer.removeViews(index+1, length);
				//更新listView数据
				Contact contact=(Contact)arg0.getTag();
				mData.clear();
				mData.addAll(contact.getNodes());
				adapterTwo.notifyDataSetChanged();
			}
		});
	}    
	private ActionBar actionBar;
	@SuppressLint("NewApi")
	private void initAction() {
	// TODO Auto-generated method stub
	actionBar=super.getActionBar();
	actionBar.show();
	actionBar.setDisplayShowHomeEnabled(true);
	actionBar.setDisplayHomeAsUpEnabled(true);
//	actionBar.setHomeAsUpIndicator(R.drawable.back_move_details_normal_night);
	actionBar.setDisplayShowTitleEnabled(false);
	ColorDrawable colorDrawable=new ColorDrawable(Color.TRANSPARENT);
	actionBar.setIcon(colorDrawable);
	actionBar.setDisplayShowCustomEnabled(true);
	TextView tvTitle=new TextView(this);
	tvTitle.setText("联系人");
	tvTitle.setTextColor(Color.WHITE);
	tvTitle.setTextSize(20);
	tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
			LayoutParams.WRAP_CONTENT));
	tvTitle.setGravity(Gravity.LEFT);
	actionBar.setCustomView(tvTitle);
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		case R.id.OKOK:
			Intent intent=new Intent();
			Contact n=new Contact();
			n.setNodes(selectedData);
			intent.putExtra("result", n);
			setResult(1,intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Contact contact=(Contact)adapterTwo.getItem(arg2);
		if(contact.getType()!=Contact.STUDENT){
			addNav(contact);
			reLoadData(contact);
			}
	}
	public void reLoadData(final Contact contact) {
		// TODO Auto-generated method stub
		String url=UrlUtil.SCHOOL_CONTACT_URL;
		StringPostRequest request=new StringPostRequest(url, 
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub\
						List<Contact> list=null;
						switch (contact.getType()) {
						case Contact.ALL_MAJOR:
							list=parserMajor(arg0);
							break;
						case Contact.ALL_ROLE:
							list=parserRole(arg0);
							break;
						case Contact.MAJOR:
							list=parserClass(arg0);
							break;
						case Contact.ROLE:
						case Contact.CLZSS:
							list=parserStudent(arg0);
							break;
							
						default:
							break;
						}
						//展现在当前listView里
						if(list!=null&&list.size()!=0){
							contact.setNodes(list);
							
							mData.clear();
							mData.addAll(list);
							adapterTwo.notifyDataSetChanged();
							
						}
					
					}
				},
				new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						
					}
				});
		//根据联系人的类型传递不同的参数
		switch (contact.getType()) {
		case Contact.ALL_MAJOR:
			request.putParams("dataType", "major");
			break;
		case Contact.ALL_ROLE:
			request.putParams("dataType", "role");
			break;
		case Contact.MAJOR:
			request.putParams("dataType","majortoclass");
			request.putParams("majorId", contact.getId());
			break;
		case Contact.ROLE:
			request.putParams("dataType","roletostudent");
			request.putParams("role", contact.getName());
			break;
		case Contact.CLZSS:
			request.putParams("dataType","classtostudent");
			request.putParams("classNo", contact.getId());
			break;
		default:
			break;
		}
		//发送请求
		requestQueue.add(request);
	}
	/**
	 * 解析MAJOR
	 * @param result
	 * @return
	 */
	public List<Contact> parserMajor(String result){
		List<Contact> list=new ArrayList<Contact>();
		if(TextUtils.isEmpty(result)){
			return list;
		}
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject job=jsonArray.getJSONObject(i);
				Contact contact=new Contact();
				contact.setId(job.getString("id"));
				contact.setName(job.getString("name"));
				contact.setType(Contact.MAJOR);
			list.add(contact);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<Contact> parserRole(String result){
		List<Contact> list=new ArrayList<Contact>();
		if(TextUtils.isEmpty(result)){
			return list;
		}
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject job=jsonArray.getJSONObject(i);
				Contact contact=new Contact();
				contact.setName(job.getString("role"));
				contact.setType(Contact.ROLE);
			list.add(contact);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<Contact> parserClass(String result){
		List<Contact> list=new ArrayList<Contact>();
		if(TextUtils.isEmpty(result)){
			return list;
		}
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject job=jsonArray.getJSONObject(i);
				Contact contact=new Contact();
				contact.setId(job.getString("id"));
				contact.setName(job.getString("className"));
				contact.setType(Contact.CLZSS);
			list.add(contact);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<Contact> parserStudent(String result){
		List<Contact> list=new ArrayList<Contact>();
		if(TextUtils.isEmpty(result)){
			return list;
		}
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject job=jsonArray.getJSONObject(i);
				Contact contact=new Contact();
				contact.setId(job.getString("uno"));
				contact.setName(job.getString("stuName"));
				contact.setType(Contact.STUDENT);
			list.add(contact);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}	

	private List<Contact> selectedData =new ArrayList<Contact>();
	@Override
	public void onContactSelectChanged(Contact contact) {
		// TODO Auto-generated method stub
		if(contact.isSelected()){
			selectedData.add(contact);
		}else{
			selectedData.remove(contact);
		}
		StringBuilder sb=new StringBuilder();
		for(Contact c: selectedData){
			sb.append(c.getName()).append(",");
		}
		
		rightNow.setText(sb.toString());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.man, menu);
		return true;
	}

}
