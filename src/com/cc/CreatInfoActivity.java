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
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cc.adapter.ContactSelectAdapter;
import com.cc.adapter.ContactSelectAdapter.onContactSelectedListener;
import com.cc.application.CCApplication;
import com.cc.entity.Contact;
import com.cc.utils.StringPostRequest;
import com.cc.utils.UrlUtil;

public class CreatInfoActivity extends Activity implements onContactSelectedListener{
	private View view;
	private ListView lvSearch,lvResult;
	private ContactSelectAdapter adapter;
	private ContactSelectAdapter adapterTwo;
	private List<Contact> mData; 
	private RequestQueue requestQueue;
	private EditText edSerch;
	DisplayMetrics dm;
	private int sWidth,sHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creat_info);
		dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm); 
		initAction();
		requestQueue=CCApplication.getInstance().getRequestQueue();
		
		edSerch=(EditText)findViewById(R.id.edSerch);
		mData=new ArrayList<Contact>();
		
		adapter=new ContactSelectAdapter(mData,this);
		adapterTwo=new ContactSelectAdapter(selectedData,this);
		lvResult=(ListView)findViewById(R.id.lvResult);
		lvResult.setAdapter(adapterTwo);	
	}
	String search=null;
	private void initData() {
		// TODO Auto-generated method stub
		StringPostRequest request=new StringPostRequest(UrlUtil.SCHOOL_CONTACT_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String result) {
						// TODO Auto-generated method stub
						List<Contact> list=null;
						list=parserMajor(result);
						if(list!=null&&list.size()!=0){
							
							mData.clear();
							mData.addAll(list);
							adapter.notifyDataSetChanged();
							
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(CreatInfoActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
						
					}
				}) ;
		search=edSerch.getText().toString();
		request.putParams("dataType", "student");
		request.putParams("name", search);
		requestQueue.add(request);
		
	}
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
				
				contact.setName(job.getString("stuName"));
			list.add(contact);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.creat_info, menu);
		return true;
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
	tvTitle.setText("新建消息");
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
		case R.id.sendInfo:
			turn();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void turn() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,ManActivity.class);
		startActivityForResult(intent, 1);
	}
	public void doPopup(View v){
		sWidth=dm.widthPixels;
		sHeight=dm.heightPixels;
		int[] position=new int[2];
		edSerch.getLocationInWindow(position);
		
		int btnHeight=edSerch.getHeight();
		int popuHeight=sHeight-position[1]-btnHeight;
		view=LayoutInflater.from(this).inflate(R.layout.search_layout, null);
		final PopupWindow popupWindow=new PopupWindow(view,
				sWidth,popuHeight);
		lvSearch=(ListView)view.findViewById(R.id.lvSearch);
		lvSearch.setAdapter(adapter);
		adapter.setListener(this);
		popupWindow.setAnimationStyle(R.style.mypopupwindow_anim_style);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable());
		popupWindow.showAsDropDown(edSerch);
		initData();
		
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
		adapterTwo.notifyDataSetChanged();
		
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1&&resultCode==1){
			Contact dataName=(Contact)data.getSerializableExtra("result");
			if(dataName!=null){
				selectedData.clear();
				selectedData.addAll(dataName.getNodes());
			}
		}
		adapterTwo.notifyDataSetChanged();
	}
	
}
