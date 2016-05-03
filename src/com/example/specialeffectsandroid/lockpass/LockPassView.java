package com.example.specialeffectsandroid.lockpass;

import com.example.specialeffectsandroid.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.Paint.Cap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LockPassView extends View {

	Paint linePaint = new Paint();

	Paint textPaint = new Paint();

	Path path = new Path();

	Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock);
	int defaultBitmapRadius = defaultBitmap.getWidth() / 2;

	Bitmap selectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_lock_area);
	int selectedBitmapDiameter = selectedBitmap.getWidth();
	int selectedBitmapRadius = selectedBitmapDiameter / 2;

	Bitmap indicateBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_lock_area_next);
	Bitmap tempBitmap = null;

	PointInfo[] points = new PointInfo[9];

	int width, height;

	int moveX, moveY;

	boolean isUp = false;

	StringBuffer lockString = new StringBuffer();

	Matrix matrix = new Matrix();

	public LockPassView(Context context) {
		super(context);
		this.setBackgroundColor(Color.WHITE);
		initLinePaint(linePaint);
		initTextPaint(textPaint);
	}

	public LockPassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundColor(Color.WHITE);
		initLinePaint(linePaint);
		initTextPaint(textPaint);
	}

	private void initTextPaint(Paint paint) {
		textPaint.setTextSize(30);
		textPaint.setAntiAlias(true);
		textPaint.setTypeface(Typeface.MONOSPACE);
	}

	private void initLinePaint(Paint paint) {
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(defaultBitmap.getWidth());
		paint.setAntiAlias(true);
		// 画笔笔刷类型 如影响画笔但始末端//设置笔刷的样式 Paint.Cap.Round ,Cap.SQUARE等分别为圆形、方形
		paint.setStrokeCap(Cap.ROUND);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		width = getWidth();
		height = getHeight();
		if (width != 0 && height != 0) {
			initPoints(points);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private void initPoints(PointInfo[] points) {

		int len = points.length;

		int seletedSpacing = (width - selectedBitmapDiameter * 3) / 4;

		int seletedX = seletedSpacing;
		int seletedY = height - width + seletedSpacing;

		int defaultX = seletedX + selectedBitmapRadius - defaultBitmapRadius;
		int defaultY = seletedY + selectedBitmapRadius - defaultBitmapRadius;

		for (int i = 0; i < len; i++) {
			if (i == 3 || i == 6) {
				seletedX = seletedSpacing;
				seletedY += selectedBitmapDiameter + seletedSpacing;

				defaultX = seletedX + selectedBitmapRadius - defaultBitmapRadius;
				defaultY += selectedBitmapDiameter + seletedSpacing;

			}
			points[i] = new PointInfo(i, defaultX, defaultY, seletedX, seletedY, selectedBitmapRadius,
					selectedBitmapDiameter);

			seletedX += selectedBitmapDiameter + seletedSpacing;
			defaultX += selectedBitmapDiameter + seletedSpacing;

		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	private int startX = 0, startY = 0;

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawText("输入的密码是：" + lockString, 0, 40, textPaint);

		if (moveX != 0 && moveY != 0 && startX != 0 && startY != 0) {
			canvas.drawLine(startX, startY, moveX, moveY, linePaint);
		}

		drawNinePoint(canvas, linePaint);

		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		boolean flag = true;

		if (isUp) {//

			finishDraw();

			flag = false;

		} else {
			handlingEvent(event);

			flag = true;

		}
		return flag;
	}

	private void handlingEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			moveX = (int) event.getX();
			moveY = (int) event.getY();
			for (PointInfo temp : points) {
				if (temp.isInMyPlace(moveX, moveY) && temp.isNotSelected()) {
					temp.setSelected(true);
					startX = temp.getCenterX();
					startY = temp.getCenterY();
					int len = lockString.length();
					if (len != 0) {
						int preId = lockString.charAt(len - 1) - 48;
						points[preId].setNextId(temp.getId());
					}
					lockString.append(temp.getId());
					break;
				}
			}

			invalidate(0, height - width, width, height);
			break;

		case MotionEvent.ACTION_DOWN:
			int downX = (int) event.getX();
			int downY = (int) event.getY();
			for (PointInfo temp : points) {
				if (temp.isInMyPlace(downX, downY)) {
					temp.setSelected(true);
					startX = temp.getCenterX();
					startY = temp.getCenterY();
					lockString.append(temp.getId());
					break;
				}
			}
			invalidate(0, height - width, width, height);
			break;

		case MotionEvent.ACTION_UP:
			startX = startY = moveX = moveY = 0;
			isUp = true;
			invalidate();
			break;
		default:
			break;
		}
	}

	private void finishDraw() {
		for (PointInfo temp : points) {
			temp.setSelected(false);
			temp.setNextId(temp.getId());
		}
		lockString.delete(0, lockString.length());
		isUp = false;
		invalidate();
	}

	private void drawNinePoint(Canvas canvas, Paint paint) {

		for (PointInfo pointInfo : points) {
			if (pointInfo.hasNextId()) {
				int n = pointInfo.getNextId();
				canvas.drawLine(pointInfo.getCenterX(), pointInfo.getCenterY(), points[n].getCenterX(),
						points[n].getCenterY(), paint);
			}
		}

		for (PointInfo pointInfo : points) {
			if (pointInfo.isSelected()) {
				if (pointInfo.hasNextId()) {
					matrix.reset();
					int i = (int) Math.abs(Math.random() * 1000 - 640);
					matrix.setRotate(i);
					tempBitmap = Bitmap.createBitmap(indicateBitmap, 0, 0, indicateBitmap.getWidth(),
							indicateBitmap.getHeight(), matrix, false);
					canvas.drawBitmap(tempBitmap, pointInfo.getSeletedX(), pointInfo.getSeletedY(), paint);
				} else {
					canvas.drawBitmap(selectedBitmap, pointInfo.getSeletedX(), pointInfo.getSeletedY(), paint);
				}
			}
			canvas.drawBitmap(defaultBitmap, pointInfo.getDefaultX(), pointInfo.getDefaultY(), paint);
		}

	}

}
