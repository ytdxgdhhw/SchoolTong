package com.cc.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.cc.R;
import com.cc.entity.NewsItem;

public class SchoolNewsDetailActivity extends Activity {
	private TextView title,date;
	private WebView content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cc_news_detail); 
		title=(TextView)findViewById(R.id.title);
		date=(TextView)findViewById(R.id.date);
		content=(WebView)findViewById(R.id.content);
		NewsItem item=(NewsItem)getIntent().getSerializableExtra("news");
		title.setText(item.getTitle());
		date.setText(item.getTime());
//		content.setText(item.getContent());
//		content.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
//		content.loadUrl("http://192.168.0.1:8080");
//		content.setWebViewClient(new WebViewClient(){
//			 
//		});
		content.setBackgroundColor(Color.WHITE);
		WebSettings settings=content.getSettings();
		settings.setDefaultTextEncodingName("UTF-8");
		content.loadDataWithBaseURL("http://192.168.0.1:8080", item.getContent(), "text/html", "UTF-8", null);
	}
	
}
