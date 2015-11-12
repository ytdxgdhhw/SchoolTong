package com.cc.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.cc.R;
import com.cc.utils.FileUitlity;

public class DownLoadService extends Service {
	private Thread thread;
	private NotificationManager nm;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		final String url = intent.getStringExtra("url");
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				//
				download(url);
				//停止服务
				stopSelf();
			}
		});
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void notifyNotification(int progress){
		//创建Notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setContentTitle("下载");
		builder.setProgress(100, progress, false);
		builder.setSmallIcon(R.drawable.cc_unread_msg);
		nm.notify(0, builder.build());
	}
	
	public void notifyFinishNotification(){
		//创建Notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setContentTitle("提示");
		builder.setContentText("下载完成，点击查看！");
		builder.setSmallIcon(R.drawable.cc_unread_msg);
		//安装的文件在哪
		File parent= FileUitlity.getInstance(this).makeDir("apk");
		File apk =new File(parent, "cc.apk");
		
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setAction(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive"); 
		
		PendingIntent intent =
				PendingIntent.getActivity(
						this, 
						0, 
						i,
						PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setAutoCancel(true);
		builder.setContentIntent(intent);
		nm.notify(0, builder.build());
	}
	
	
	/**
	 * 安装APK文件:标准
	 */
	public void installApk() {
		File parent= FileUitlity.getInstance(this).makeDir("apk");
		File apk =new File(parent, "cc.apk");
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setAction(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive"); 
		startActivity(i);
	}
	/**
	 * 下载
	 */
	public void download(String urlStr){
		HttpURLConnection conn = null;
		URL url = null;
		InputStream is = null;
		OutputStream os= null;
		int contentLength = 0;
		int curLoadLength=0;
		try {
			url = new URL(urlStr);
			File parent= FileUitlity.getInstance(this).makeDir("apk");
			os = new FileOutputStream(new File(parent, "cc.apk"));
			conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			if(conn.getResponseCode()==200){
				//文件的总长度
				contentLength =conn.getContentLength();
				is= conn.getInputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while((len=is.read(buffer))!=-1){
					//
					curLoadLength+=len;
					os.write(buffer, 0, len);
					//计算进度
					int progress=(int)((curLoadLength*1.0/contentLength)*100);
					notifyNotification(progress);
				}
				os.flush();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(is!=null)
					is.close();
				if(os!=null){
					os.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.disconnect();
		}
		notifyFinishNotification();
	}
	
	
}
