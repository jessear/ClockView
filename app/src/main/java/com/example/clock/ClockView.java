package com.example.clock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class ClockView extends View{
	private float mRadius;//外圈的半径
	private float mSmlRadius; //指针小圆的半径
	private float mSecondLen;//秒针的长度
	private float mMinuteLen;//分针的长度
	private float mHourLen;//时针的长度
	private float mTextSize;//字体的大小
	private float mStrokeWidth;//线宽
	private boolean mBold;//粗体
	private float mDistance=50f;//秒针末端延伸的长度
	private SimpleDateFormat mDateFormat;//日期格式
	public ClockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
		mRadius=array.getFloat(R.styleable.ClockView_radius, 0f);
		mSmlRadius=array.getFloat(R.styleable.ClockView_smlRadius, 0f);
		mTextSize=array.getFloat(R.styleable.ClockView_textSize, 0f);
		mStrokeWidth=array.getFloat(R.styleable.ClockView_strokeWidth, 0f);
		mBold=array.getBoolean(R.styleable.ClockView_textBold, false);
		mSecondLen=mRadius*0.83f+mDistance;//0.83�������Լ��������ͬ
		mMinuteLen=(mSecondLen-mDistance)*0.88f;
		mHourLen=mMinuteLen*0.88f;
		mDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEEE");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStrokeWidth(mStrokeWidth);
		paint.setAntiAlias(true);// 设置画笔的锯齿效果
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.WHITE);
		canvas.translate(canvas.getWidth()/2-mRadius,mRadius); //改变画布位置
		canvas.drawCircle(mRadius, mRadius, mRadius, paint);//外圈
		Calendar calendar=Calendar.getInstance();
		Date date=calendar.getTime();
		int seconds=date.getSeconds();
		int minutes=date.getMinutes();
		int hours=date.getHours();

		paint.setColor(Color.WHITE);
		canvas.drawLine(mRadius, mRadius,
				mRadius+(float) (mHourLen*Math.sin(((hours+minutes/60f)*30)*Math.PI/180)),
				mRadius-(float) (mHourLen*Math.cos(((hours+minutes/60f)*30)*Math.PI/180)),paint);//时针指针

		paint.setColor(Color.BLUE);
		canvas.drawLine(mRadius, mRadius,
				mRadius+(float) (mMinuteLen*Math.sin(minutes*6*Math.PI/180)),
				mRadius-(float) (mMinuteLen*Math.cos(minutes*6*Math.PI/180)),paint);//分针指针
		paint.setColor(Color.RED);
		canvas.drawLine(
				mRadius-(float)((mDistance*Math.sin(seconds*6*Math.PI/180))),
				mRadius+(float)((mDistance*Math.cos(seconds*6*Math.PI/180))),
				mRadius+(float)((mSecondLen-mDistance)*Math.sin(seconds*6*Math.PI/180)),
				mRadius-(float)((mSecondLen-mDistance)*Math.cos(seconds*6*Math.PI/180)),paint);//秒针指针

		paint.setStyle(Style.FILL);
		canvas.drawCircle(mRadius, mRadius,mSmlRadius, paint);

		paint.setColor(Color.WHITE);
		paint.setFakeBoldText(mBold);
		paint.setTextSize(mTextSize);
		for(int i=1;i<=12;i++){
			canvas.drawText(i+"",
					(float) (mRadius-mTextSize/2+(mRadius-mTextSize*1.5f)*Math.sin(30*i*Math.PI/180)),
					(float) (mRadius+mTextSize/2-(mRadius-mTextSize*1.5f)*Math.cos(30*i*Math.PI/180)), paint);//刻度
		}

		paint.setTextSize(mTextSize*2);
		String str=mDateFormat.format(date);
		canvas.drawText(str, mRadius-getTextWidth(paint, str)/2, mRadius*2+100, paint);
	}

	public void updateTime(){
		invalidate();
	}
	/**
	 * 获取字体的长度
	 * @param paint
	 * @param str
	 * @return
	 */
	private int getTextWidth(Paint paint, String str) {
		int iRet = 0;
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len];
			paint.getTextWidths(str, widths);
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);
			}
		}
		return iRet;
	}


}
