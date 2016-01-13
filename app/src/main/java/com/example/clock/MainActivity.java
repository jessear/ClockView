package com.example.clock;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.app.Activity;

public class MainActivity extends Activity {
	private ClockView clockView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//±£³ÖÆÁÄ»³£ÁÁ 
		setContentView(R.layout.activity_main);
		clockView=(ClockView) findViewById(R.id.clockView);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						handler.sendEmptyMessage(1);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	private Handler handler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			clockView.updateTime();
		};
	};
}
