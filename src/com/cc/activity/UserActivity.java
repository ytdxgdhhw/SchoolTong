package com.cc.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.cc.CollectActivity;
import com.cc.R;
import com.cc.ReSetActivity;
import com.cc.application.CCApplication;
import com.cc.entity.CCUpdate;
import com.cc.entity.CCUser;
import com.cc.entity.ThemeNews;
import com.cc.utils.UrlUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserActivity extends Activity implements OnClickListener {
	private RelativeLayout rlUser;
	private Button btTuichu;
	private RelativeLayout reSet;
	private TextView textView1,textView2;
	private RequestQueue requestQueue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		requestQueue=CCApplication.getInstance().getRequestQueue();
		initAction();
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		rlUser=(RelativeLayout)findViewById(R.id.rlUser);
		btTuichu=(Button)findViewById(R.id.btTuichu);
		reSet=(RelativeLayout)findViewById(R.id.reSet);
		textView1=(TextView)findViewById(R.id.textView1);
		textView2=(TextView)findViewById(R.id.textView2);
		CCUser user=CCApplication.getInstance().getUser();
		textView1.setText(user.getStuName());
		textView2.setText(user.getUno());
		btTuichu.setOnClickListener(this);
		rlUser.setOnClickListener(this);
		reSet.setOnClickListener(this);
	}
	private void turn3() {
		// TODO Auto-generated method stub
		System.exit(0);
//		Intent intent=new Intent(this,LoginActivity.class);
//		this.startActivity(intent);
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
		tvTitle.setText("设置");
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
		switch(arg0.getId()){
		case R.id.rlUser:
			turn4();
			break;
		
		case R.id.btTuichu:
			turn3();
			
			break;
		case R.id.reSet:
			turn5();
			break;
		}
	}
	private void turn5() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,ReSetActivity.class);
		this.startActivity(intent);
	}
	private void turn4() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,PersionActivity.class);
		this.startActivity(intent);
	}
	public void doCollect(View v){
		Intent intent=new Intent(this,CollectActivity.class);
		this.startActivity(intent);
	}
	
	private CCUpdate update;
	private int vcode;
	public void gengXin(View v) throws NameNotFoundException{
	//	怎么对单个的具体实现类进行解析
		StringRequest request=new StringRequest(UrlUtil.SCHOOL_UPDATE,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
						Gson gson=new Gson();
						update=gson.fromJson(arg0,CCUpdate.class);
						if(update!=null){
						vcode=update.getVid();
						}
					}
				}, 
				new Response.ErrorListener() {//走这一步

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(UserActivity.this, "网络加载失败！", Toast.LENGTH_SHORT).show();
					}
				});
		requestQueue.add(request);	
		
	if(vcode!=0){
		//将版本编号和当前按装的编号进行对比
		PackageManager pm= getPackageManager();
		PackageInfo pki= pm.getPackageInfo("com.cc", 0);
		int curCode= pki.versionCode;
		if(vcode>curCode){
			//版本有更新
			//准备下载
			Drawable icon=super.getResources().getDrawable(R.drawable.hhw);
			Dialog dialog=new AlertDialog.Builder(this)
			.setTitle("确认信息").setMessage("确认更新吗？").setIcon(icon).
			setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {//清除缓存文件
					// TODO Auto-generated method stub
					update();
					dialog.dismiss();
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();
				}
			}).create();
			dialog.show();
		
		}else{
			Toast.makeText(this, "已经是最新版本", Toast.LENGTH_SHORT).show();
		}
	}
}
	private void update(){
		Intent intent = new Intent(this, DownLoadService.class);
		intent.putExtra("url", "http://192.168.0.1:8080/SchoolLife/apk/cc.apk");
		startService(intent);
	}
}
