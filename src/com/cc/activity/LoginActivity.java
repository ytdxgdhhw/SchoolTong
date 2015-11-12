package com.cc.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cc.R;
import com.cc.application.CCApplication;
import com.cc.dao.CCUserDao;
import com.cc.entity.CCUser;
import com.cc.utils.Constant;
import com.cc.utils.StringPostRequest;
import com.cc.utils.UrlUtil;
import com.google.gson.Gson;

public class LoginActivity extends Activity {
	private List<CCUser> cCUser;
	private CheckBox checkBox1,checkBox2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initAction();
		initView();
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
		tvTitle.setText("登陆");
		tvTitle.setTextColor(Color.WHITE);
		tvTitle.setTextSize(20);
		tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tvTitle.setGravity(Gravity.LEFT);
		actionBar.setCustomView(tvTitle);
	}
	private RequestQueue requestQueue;
	private Button btLogin;
	private EditText etName,etPwd;
	private void initView() {
		// TODO Auto-generated method stub
		requestQueue=CCApplication.getInstance().getRequestQueue();
		btLogin=(Button)findViewById(R.id.btLogin);
		etName=(EditText)findViewById(R.id.etName);
		etPwd=(EditText)findViewById(R.id.etPwd);
		checkBox1=(CheckBox)findViewById(R.id.checkBox1);
		checkBox2=(CheckBox)findViewById(R.id.checkBox2);
		setCheckBoxState();
	}

	private void setCheckBoxState() {
		// TODO Auto-generated method stub
		SharedPreferences sp=getSharedPreferences(Constant.SP_FILE_NAME, MODE_PRIVATE);
		boolean remPwd=sp.getBoolean("REM_PWD", true);
		boolean autoLogin=sp.getBoolean("AUTO_LOGIN", true);
		checkBox1.setChecked(remPwd);
		checkBox2.setChecked(autoLogin);
		checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				SharedPreferences sp=getSharedPreferences(Constant.SP_FILE_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor=sp.edit();
				editor.putBoolean("REM_PWD", arg1);
				editor.commit();
				
			}
		});
		checkBox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				SharedPreferences sp=getSharedPreferences(Constant.SP_FILE_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor=sp.edit();
				editor.putBoolean("AUTO_LOGIN", arg1);
				editor.commit();
				if(arg1){
					checkBox1.setChecked(true);
				}
			}
		});
	}

	public void login(View v){
		final String name=etName.getText().toString();
		final String pwd=etPwd.getText().toString();
			if(TextUtils.isEmpty(name)){
				Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			if(TextUtils.isEmpty(pwd)){
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
		StringPostRequest request=new StringPostRequest(UrlUtil.SCHOOL_LOGIN_URL, 
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
					try {
						JSONObject jsonObj=new JSONObject(arg0);
						if(jsonObj.has("info")){
							//登录失败
						}else{
							//解析
							Gson gson=new Gson();
							CCUser cCUser=gson.fromJson(arg0, CCUser.class);
							//保存用户信息
							CCApplication.getInstance().setUser(cCUser);
							//保存到本地数据库，根据用户设置，是否保存用户名和密码
							CCUserDao userDao=new CCUserDao(getApplicationContext());
							userDao.SaveOrUpdateCCUser(cCUser);
							
							SharedPreferences sp=getSharedPreferences(Constant.SP_FILE_NAME, MODE_PRIVATE);
							SharedPreferences.Editor editor=sp.edit();
							editor.putString("userName", name);
							editor.putString("userPwd", pwd);
							editor.commit();
							setResult(RESULT_OK);
							finish();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}, 
				new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						
					}
				});
		request.putParams("userName", name);
		request.putParams("pwd", pwd);
		requestQueue.add(request);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
