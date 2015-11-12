package com.cc.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

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

public class StartActivity extends Activity {
	private ActionBar actionBar;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		initView();
		actionBar = getActionBar();
		actionBar.hide();
	}

	private Handler mHandler = new Handler();

	private void initView() {
		// TODO Auto-generated method stub
		SharedPreferences sp = getSharedPreferences(Constant.SP_FILE_NAME,
				MODE_PRIVATE);
		boolean flag = sp.getBoolean("isFirst", true);
		if (flag) {
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(StartActivity.this,
							FlashActivity.class);
					startActivity(intent);
					finish();
				}
			}, 3000);
		} else {
			boolean autoLogin = sp.getBoolean("AUTO_LOGIN", true);
		final	String userName = sp.getString("userName", null);
		final	String userPwd = sp.getString("userPwd", null);
			if (autoLogin && !TextUtils.isEmpty(userName)
					&& !TextUtils.isEmpty(userPwd)) { 
				// 登陆
				// 定义一个请求
				StringPostRequest request = new StringPostRequest(
						UrlUtil.SCHOOL_LOGIN_URL, new Listener<String>() {
							@Override
							public void onResponse(String arg0) {
								System.out.println(arg0);
								try {
									JSONObject jsonObj = new JSONObject(arg0);
									if (!jsonObj.has("info")) {
										// 解析
										Gson gson = new Gson();
										CCUser cCUser = gson.fromJson(arg0,
												CCUser.class);
										// 保存用户信息
										CCApplication.getInstance().setUser(
												cCUser);
										// 保存到本地数据库
										CCUserDao userDao=new CCUserDao(getApplicationContext());
										userDao.SaveOrUpdateCCUser(cCUser);
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Intent intent = new Intent(StartActivity.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							}
						}, new ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError arg0) {
								// 根据sp中保存的用户名和密码，从本地库中取出用户信息
								CCUserDao userDao=new CCUserDao(getApplicationContext());
								CCUser user=userDao.findLoginUserByUno(userName);
								CCApplication.getInstance().setUser(user);
								Intent intent = new Intent(StartActivity.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							}
						});

				request.putParams("userName", userName);
				request.putParams("pwd", userPwd);
				CCApplication.getInstance().getRequestQueue().add(request);
			} else {
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Intent intent = new Intent(StartActivity.this,
								MainActivity.class);
						startActivity(intent);
						finish();
					}
				}, 3000);
			}

		}
		// mHandler.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// //判断应该跳转到哪个界面
		//
		// SharedPreferences sp=getSharedPreferences("isOpen", MODE_PRIVATE);
		// boolean flag=sp.getBoolean("isFirst", true);
		// Intent intent;
		// if(flag){
		// intent=new Intent(StartActivity.this,FlashActivity.class);
		// }else{
		// intent=new Intent(StartActivity.this,MainActivity.class);
		// }
		// startActivity(intent);
		// finish();
		// }
		// }, 2000);

	}

}
