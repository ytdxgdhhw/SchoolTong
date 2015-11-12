package com.cc.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.R;
import com.cc.SubjectActivity;
import com.cc.entity.News;

public class SubjectMessageActivity extends Activity {
	
	
	private int i=1;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case android.R.id.home:
//			Intent intent=new Intent(this,SubjectActivity.class);
//			startActivity(intent);
//			overridePendingTransition(R.anim.back_anim,R.anim.backback_anim);
			finish();
			break;
		case R.id.sub_message:
			if(i==0){
				Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
				item.setTitle("取消收藏");
				i++;
			}else{
				Toast.makeText(this,"取消收藏",Toast.LENGTH_SHORT).show();
				item.setTitle("收藏");
				i--;
				
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private News news;
	private List<News> listNews=new ArrayList<News>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_message);
		intiActionBar();
		Intent intent=super.getIntent();
		if(intent!=null){
			news=(News)intent.getSerializableExtra("news");
		}
		intiView();
	}

	private TextView title;
	private TextView date;
	private TextView content;
	private void intiView() {
		// TODO Auto-generated method stub
		title=(TextView)findViewById(R.id.topNews);
		date=(TextView)findViewById(R.id.topTime);
		content=(TextView)findViewById(R.id.content);
		
		title.setText(news.getTitle());
		date.setText(news.getTime());
		content.setText(news.getContent());
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject_message, menu);
		return true;
	}
	
	private ActionBar actionBar;
	@SuppressLint("NewApi")
	private void intiActionBar() {
		// TODO Auto-generated method stub
		actionBar=super.getActionBar();
		actionBar.show();
		
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeAsUpIndicator(R.drawable.back_move_details_normal_night);
		
		ColorDrawable color=new ColorDrawable(0);
		actionBar.setIcon(color);
		
		
		actionBar.setDisplayUseLogoEnabled(false);
		
		actionBar.setDisplayShowCustomEnabled(true);
		TextView tvTitle=new TextView(this);
		tvTitle.setText("信息详情");
		tvTitle.setTextColor(Color.WHITE);
		tvTitle.setTextSize(22);
		tvTitle.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		actionBar.setCustomView(tvTitle);
		
	}
	


}
