package com.cc;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class InternetActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internet);
		initAction();
		initData();
	}
	private TextView tv3,tv4,tv5,tv6,tv7,tv8,tv9;
	private void initData() {
		// TODO Auto-generated method stub
		tv3=(TextView)findViewById(R.id.tv3);
		tv4=(TextView)findViewById(R.id.tv4);
		tv5=(TextView)findViewById(R.id.tv5);
		tv6=(TextView)findViewById(R.id.tv6);
		tv7=(TextView)findViewById(R.id.tv7);
		tv8=(TextView)findViewById(R.id.tv8);
		tv9=(TextView)findViewById(R.id.tv9);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		tv5.setOnClickListener(this);
		tv6.setOnClickListener(this);
		tv7.setOnClickListener(this);
		tv8.setOnClickListener(this);
		tv9.setOnClickListener(this);
		
	}
	private Intent intent=null;
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.tv3:
			intent=new Intent();
			intent.setData(Uri.parse("http://www.ccec.edu.cn/"));
			intent.setAction(Intent.ACTION_VIEW);
			this.startActivity(intent);
			break;
		case R.id.tv4:
			intent=new Intent();
			intent.setData(Uri.parse("http://www.ccec.edu.cn/"));
			intent.setAction(Intent.ACTION_VIEW);
			this.startActivity(intent);
			break;	
		case R.id.tv5:
			intent=new Intent();
			intent.setData(Uri.parse("http://www.ccec.edu.cn/"));
			intent.setAction(Intent.ACTION_VIEW);
			this.startActivity(intent);
			break;
		case R.id.tv6:
			intent=new Intent();
			intent.setData(Uri.parse("http://www.ccec.edu.cn/"));
			intent.setAction(Intent.ACTION_VIEW);
			this.startActivity(intent);
			break;
		case R.id.tv7:
			intent=new Intent();
			intent.setData(Uri.parse("http://www.ccec.edu.cn/"));
			intent.setAction(Intent.ACTION_VIEW);
			this.startActivity(intent);
			break;	
		case R.id.tv8:
			intent=new Intent();
			intent.setData(Uri.parse("http://www.ccec.edu.cn/"));
			intent.setAction(Intent.ACTION_VIEW);
			this.startActivity(intent);
			break;
		case R.id.tv9:
			intent=new Intent();
			intent.setData(Uri.parse("http://www.ccec.edu.cn/"));
			intent.setAction(Intent.ACTION_VIEW);
			this.startActivity(intent);
			break;	
		default:
			break;
		}
	}	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.internet, menu);
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
	tvTitle.setText("±ãÀû¹¤¾ß");
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


	
}
