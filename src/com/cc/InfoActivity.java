package com.cc;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ApplicationErrorReport.CrashInfo;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		initAction();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
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
	tvTitle.setText("消息平台");
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
		Intent intent=new Intent(this,CreatInfoActivity.class);
		startActivity(intent);
	}

}
