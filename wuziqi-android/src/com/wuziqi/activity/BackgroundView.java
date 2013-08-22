package com.wuziqi.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.CalendarContract.Attendees;
import android.util.AttributeSet;
import android.view.View;

public class BackgroundView extends View {
	Paint paint;

	public BackgroundView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initBackgroundView();
	}

	public BackgroundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initBackgroundView();
	}

	public BackgroundView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initBackgroundView();
	}

	protected void initBackgroundView() {
		setFocusable(true);
		paint = new Paint(); // 设置一个笔刷大小是3的黑色的画笔
		paint.setColor(Color.BLACK);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(1);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int i, j;
		float lenth = (float) (Math
				.min(getMeasuredHeight(), getMeasuredWidth()) / 16.0 * 14);
		float radius = (float) (lenth / 30.0);
		float startX = (float) ((getMeasuredWidth() - lenth) / 2.0);
		float startY = (float) ((getMeasuredHeight() - lenth) / 2.0);
		// 画横线
		for (i = 0; i < 15; i++) {
			canvas.drawLine(startX, startY + (float) (lenth / 14.0 * i), startX
					+ lenth, startY + (float) (lenth / 14.0 * i), paint);
		}
		// 画竖线
		for (j = 0; j < 15; j++) {
			canvas.drawLine(startX + (float) (lenth / 14.0 * j), startY, startX
					+ (float) (lenth / 14.0 * j), startY + lenth, paint);
		}
	}
}
