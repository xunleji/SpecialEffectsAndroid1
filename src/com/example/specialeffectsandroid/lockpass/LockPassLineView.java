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

public class LockPassLineView extends View {

	Paint linePaint = new Paint();
	Paint whiteLinePaint = new Paint();
	Paint textPaint = new Paint();
	Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock);
	int defaultBitmapRadius = defaultBitmap.getWidth() / 2;

	Bitmap selectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_lock_area);
	int selectedBitmapDiameter = selectedBitmap.getWidth();
	int selectedBitmapRadius = selectedBitmapDiameter / 2;

	PointInfo[] points = new PointInfo[9];

	PointInfo startPoint = null;

	int width, height;

	int moveX, moveY;

	boolean isUp = false;

	StringBuffer lockString = new StringBuffer();

	public LockPassLineView(Context context) {
		super(context);
		this.setBackgroundColor(Color.WHITE);
		initPaint();
	}

	public LockPassLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundColor(Color.WHITE);
		initPaint();
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
		}

		drawNinePoint(canvas);

		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean flag = true;
		if (isUp) {
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
					startPoint = temp;
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

	private void initPaint() {
		initLinePaint(linePaint);
		initTextPaint(textPaint);
		initWhiteLinePaint(whiteLinePaint);
	}

	private void initTextPaint(Paint paint) {
		textPaint.setTextSize(30);
		textPaint.setAntiAlias(true);
		textPaint.setTypeface(Typeface.MONOSPACE);
	}

	private void initLinePaint(Paint paint) {
		paint.setColor(Color.GRAY);
		paint.setStrokeWidth(defaultBitmap.getWidth());
		paint.setAntiAlias(true);
		paint.setStrokeCap(Cap.ROUND);
	}

	private void initWhiteLinePaint(Paint paint) {
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(defaultBitmap.getWidth() - 5);
		paint.setAntiAlias(true);
		paint.setStrokeCap(Cap.ROUND);

	}

	private void drawNinePoint(Canvas canvas) {

		if (startPoint != null) {
			drawEachLine(canvas, startPoint);
		}

		for (PointInfo pointInfo : points) {

			if (pointInfo.isSelected()) {
				canvas.drawBitmap(selectedBitmap, pointInfo.getSeletedX(), pointInfo.getSeletedY(), null);
			}
			canvas.drawBitmap(defaultBitmap, pointInfo.getDefaultX(), pointInfo.getDefaultY(), null);
		}

	}

	private void drawEachLine(Canvas canvas, PointInfo point) {
		if (point.hasNextId()) {
			int n = point.getNextId();
			drawLine(canvas, point.getCenterX(), point.getCenterY(), points[n].getCenterX(), points[n].getCenterY());
			drawEachLine(canvas, points[n]);
		}
	}

	private void drawLine(Canvas canvas, float startX, float startY, float stopX, float stopY) {
		canvas.drawLine(startX, startY, stopX, stopY, linePaint);
		canvas.drawLine(startX, startY, stopX, stopY, whiteLinePaint);
	}

}
