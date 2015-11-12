package com.cc.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cc.R;
import com.cc.application.CCApplication;
import com.cc.dao.CCUserDao;
import com.cc.entity.CCUser;
import com.cc.utils.FileUitlity;
import com.cc.utils.StringPostRequest;
import com.cc.utils.UrlUtil;

public class PersionActivity extends Activity {
	private ImageView imageView;
	private TextView info1,info2,info3,info4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_layout);
		initAction();
		initData();
		imageView=(ImageView)findViewById(R.id.imageView);
	}
	private void initData() {
		// TODO Auto-generated method stub
		info1=(TextView)findViewById(R.id.info1);
		info2=(TextView)findViewById(R.id.info2);
		info3=(TextView)findViewById(R.id.info3);
		info4=(TextView)findViewById(R.id.info4);
		CCUser user=CCApplication.getInstance().getUser();
		info1.setText(user.getStuName());
		info2.setText(user.getUno());
		info3.setText(user.getMajorNO());
		info4.setText(user.getClassNO());
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
		tvTitle.setText("个人信息");
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
	private View view;
	private Button btEsc;
	public void doPopup(View v){
		view=LayoutInflater.from(this).inflate(R.layout.popup_layout, null);
		final PopupWindow popupWindow=new PopupWindow(view,
				ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setAnimationStyle(R.style.mypopupwindow_anim_style);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable());
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		btEsc=(Button)view.findViewById(R.id.btEsc);
		btEsc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss(); 
			}
		});
		
	}
	private static final  int TAKE_FROM_CAPTURE=1; //拍照
	private static final int TAKE_FROM_ALBUM=2;	//本地相册
	private static final int RESULT_PHOTO = 3;//裁剪结果
	String photoPath;
	public void doPhoto(View v){
		Intent intent = 
				new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		File parent= FileUitlity.getInstance(this).makeDir("jredu_head");
		photoPath=parent.getPath()
				+File.separator
				+System.currentTimeMillis()
				+".png";
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoPath) ));
		startActivityForResult(intent, TAKE_FROM_CAPTURE);
	}
	public void doSearch(View v){
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, TAKE_FROM_ALBUM);
	}
	private Bitmap b;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode!=RESULT_OK)
			return;
		switch (requestCode) {
		case TAKE_FROM_CAPTURE:
			startPhotoZoom(Uri.fromFile(new File(photoPath)));
			break;
		case TAKE_FROM_ALBUM:
			Cursor cursor= this.getContentResolver().query(data.getData(), 
							new String[]{MediaStore.Images.Media.DATA}, null, null, null);
			cursor.moveToFirst();
			 photoPath=
					 cursor.getString(
							 cursor.getColumnIndex(
									 MediaStore.Images.Media.DATA
									 ));
			cursor.close();
			startPhotoZoom(Uri.fromFile(new File(photoPath)));
			//imageView.setImageURI(Uri.fromFile(new File(photoPath)));
			break;
		case RESULT_PHOTO:
			Bundle bundle= data.getExtras();
			if(bundle!=null){
				 b= bundle.getParcelable("data");
				imageView.setImageBitmap(b);
				Diolog();
			}
			break;

		default:
			break;
		}
	}
	
	
	private void Diolog() {
		// TODO Auto-generated method stub
		Drawable icon=super.getResources().getDrawable(R.drawable.hhw);
		Dialog dialog=new AlertDialog.Builder(this)
		.setTitle("确认信息").setMessage("确定上传头像吗？").setIcon(icon).
		setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {//清除缓存文件
				// TODO Auto-generated method stub
				String n=convertBitmap2String(b);
				updatePhoto(n);
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
	}
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_PHOTO);
	}
	public final String convertBitmap2String(Bitmap b){
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer=out.toByteArray();
		byte[] encode=Base64.encode(buffer, Base64.DEFAULT);
		return new String(encode);
	}
	public void updatePhoto(String photoStr){
		final CCUser user=CCApplication.getInstance().getUser();
		
		StringPostRequest request=new StringPostRequest(UrlUtil.SCHOOL_LOGIN_URL, 
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
						//保存到本地数据库
						user.setImg(arg0);
						CCUserDao userDao=new CCUserDao(getApplicationContext());
						userDao.SaveOrUpdateCCUser(user);
						CCApplication.getInstance().setUser(user);
					}
				}, 
				new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(PersionActivity.this, "上传失败……", Toast.LENGTH_SHORT).show();
					}
				});
		
		request.putParams("userName",user.getUno());
		request.putParams("pwd",user.getPwd());
		request.putParams("headImage","1");
		request.putParams("uhead",photoStr);
	}
}
