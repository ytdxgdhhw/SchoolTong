package com.cc;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cc.application.CCApplication;
import com.cc.utils.Constant;
import com.cc.utils.StringPostRequest;
import com.cc.utils.UrlUtil;

public class ReSetActivity extends Activity implements OnClickListener {
	private RequestQueue requestQueue;
	private EditText etPwdYes,etPwdNew,etPwdOld;
	private Button btYes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_re_set);
		requestQueue = CCApplication.getInstance().getRequestQueue();
		etPwdOld=(EditText)findViewById(R.id.etPwdOld);
		etPwdNew=(EditText)findViewById(R.id.etPwdNew);
		etPwdYes=(EditText)findViewById(R.id.etPwdYes);
		btYes=(Button )findViewById(R.id.btYes);
		btYes.setOnClickListener(this);
		initAction();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.re_set, menu);
	
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
		actionBar.setHomeAsUpIndicator(R.drawable.back_move_details_normal_night);
		actionBar.setDisplayShowTitleEnabled(false);
		ColorDrawable colorDrawable=new ColorDrawable(Color.TRANSPARENT);
		actionBar.setIcon(colorDrawable);
		actionBar.setDisplayShowCustomEnabled(true);
		TextView tvTitle=new TextView(this);
		tvTitle.setText("密码修改");
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
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btYes:
			readData();
			
			break;

		default:
			break;
		}
	}
	String pwd=null;
	String oldPwd,newPwd,yesPwd;
	private void readData() {
		// TODO Auto-generated method stub
		SharedPreferences sp = getSharedPreferences(Constant.SP_FILE_NAME, MODE_PRIVATE);
		pwd= sp.getString("userPwd", "N");	
		oldPwd=etPwdOld.getText().toString();
		newPwd=etPwdNew.getText().toString();
		yesPwd=etPwdYes.getText().toString();
		if(oldPwd.equals(pwd)&&
				newPwd.equals(yesPwd)){
			writeData(newPwd);				
		}else{
			Toast.makeText(this,"修改失败", Toast.LENGTH_LONG).show();
		}
		finish();
	
	}
	private void writeData(String code){
		StringPostRequest postRequest=new StringPostRequest(UrlUtil.SCHOOL_LOGIN_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Toast.makeText(ReSetActivity.this, "修改成功", Toast.LENGTH_LONG).show();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(ReSetActivity.this, "网络连接失败，重新修改", Toast.LENGTH_LONG).show();			
					}
				});
		postRequest.putParams("userName",CCApplication.getInstance().getUser().getUno());
		postRequest.putParams("pwd",newPwd);
		postRequest.putParams("update", "1");
		requestQueue.add(postRequest);
	}
}
