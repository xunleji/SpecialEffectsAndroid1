package com.example.specialeffectsandroid.zhuanpan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//自定义的转盘View
public class WheelView extends SurfaceView implements SurfaceHolder.Callback {

	private float screenHight, screenWidth;// 屏幕的宽和高
	private float radius;// 绘制圆的半径
	private float circleRadius; // 半径
	private float startAngle;// 开始角度
	private float sweepAngle; // 扫过的角度
	private float speed; // 速度
	private float acceleration; // 加速度
	private int itemCount;
	private int[] itemColor;
	private String[] titleString;
	private double[] ratioArray;
	private Paint mPaint;
	private Canvas mCanvas;
	private Path path;
	private SurfaceViewThread myThread;
	private SurfaceHolder holder;
	private boolean done = false;
	private boolean surfaceExist = false;
	private boolean rotateEnabled = false;
	private boolean clockwise = true;

	public WheelView(Context context, AttributeSet attr) {
		super(context, attr);
		initial();
	}

	public void initial() {
		// 创建一个新的SurfaceHolder， 并分配这个类作为它的回调(callback)
		holder = getHolder();
		holder.addCallback(this);

		mPaint = new Paint();
		
		

		itemColor = new int[] {
				0xFFFFFFFF,// 白色
				0xFFB0E0E6,// 粉蓝色　
				0xFF444444,// 深灰色
				0xFF008B8B,// 暗青色
				0xFFFFA500,// 橙色
				0xFF7FFF00,// 黄绿色
				0xFFF08080,// 亮珊瑚色
				0xFFB0C4DE // 亮钢兰色
		};
		
		//选项个数
		itemCount = 5;
		// 转盘选项的名称
		titleString = new String[] { "美餐一顿", "一起购物", "运动", "唱歌", "看电影" };
		// 所占百分比,这里平均分配
		ratioArray = new double[] { 20, 20, 20, 20, 20};
		// 半径
		radius = 120;
		circleRadius = 15;
		startAngle = 0;
		// 加速度
		acceleration = 0;
		speed = 0;
	}

	public void setDirection(boolean bool, float sp) {
		clockwise = bool;
		speed = sp;
		acceleration = Math.abs(speed) / 100;
	}

	public void rotateEnable() {
		rotateEnabled = true;
	}

	public void rotateDisable() {
		rotateEnabled = false;
	}

	public void start() {
		if (myThread == null) {
			myThread = new SurfaceViewThread();
		}
		if (surfaceExist) {
			myThread.start();
		}
	}

	public void stopRotate() {
		// 杀死渲染线程
		if (myThread != null) {
			myThread = null;
			done = true;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		if (myThread != null) {
			myThread.start();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		surfaceExist = true;
		// 高度
		screenHight = getHeight();
		screenWidth = getWidth();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		surfaceExist = false;
		myThread = null;
		done = true;
	}

	class SurfaceViewThread extends Thread {

		public SurfaceViewThread() {
			// TODO Auto-generated constructor stub
		}

		public void updateView() {

			SurfaceHolder surfaceHolder = holder;
			mCanvas = surfaceHolder.lockCanvas();

			float f1 = screenWidth / 2;
			float f2 = (float) (screenHight / 2.5);
			// 填充一下
			mCanvas.drawColor(0xff969696);
			mCanvas.save();

			// *********************************画边上渐变的圆环出来*********************************
			Paint localPaint = new Paint();
			localPaint.setAntiAlias(true);
			localPaint.setStyle(Paint.Style.STROKE);// 风格为圆环
			localPaint.setStrokeWidth(circleRadius); // 圆环宽度
			RadialGradient radialGradient = new RadialGradient(f1, f2, radius
					+ circleRadius, new int[] { Color.GREEN, Color.GRAY,
					Color.MAGENTA, Color.YELLOW, Color.BLACK }, null,
					TileMode.MIRROR);// 环形渐变
			localPaint.setShader(radialGradient);// 设置渐变
			mCanvas.drawCircle(f1, f2, radius, localPaint);
			mCanvas.save();

			// 确定参考区域
			float f3 = f1 - radius;
			float f4 = f2 - radius;
			float f5 = f1 + radius;
			float f6 = f2 + radius;
			RectF rectF = new RectF(f3, f4, f5, f6);

			// *********************************画每个区域的颜色块*********************************
			drawItem(rectF);

			// *********************************画中间的黑色指示器*********************************
			Paint localPaint2 = new Paint();
			localPaint2.setAntiAlias(true);

			localPaint2.setStrokeWidth(5);
			localPaint2.setColor(Color.BLACK);
			mCanvas.drawLine(f1, f2 + radius / 5, f1, f2 - radius / 2,
					localPaint2);
			mCanvas.drawCircle(f1, f2, 6, localPaint2);
			mCanvas.save();

			
			// 使能转动
			if (rotateEnabled) {
				if (clockwise) {
					startAngle += speed;
					speed -= acceleration;
				} else {
					startAngle -= speed;
					speed -= acceleration;
				}
				// 速度等于0则停下来
				if (speed <= 0) {
					rotateEnabled = false;
				}
			} else {
				// 避免进入了以后不点开始startAngel太大，其实没有什么关系
				startAngle -= 360;
			}
			// 解锁Canvas，并渲染当前图像
			surfaceHolder.unlockCanvasAndPost(mCanvas);
		}

		// 画上各个Item的名称
		public void drawText(RectF localRectf, float localStartAngle,
				float localSweepAngle, String str) {
			path = new Path();
			// float pading = (sweepAngle-str.length())/2;

			// 在这里注意了，这里path中的sweepAngle为正值，所以逆时针转的时候应该判断一下
			if (localSweepAngle > 0) {
				path.addArc(localRectf, localStartAngle, localSweepAngle);
			}
			path.addArc(localRectf, localStartAngle - localSweepAngle,
					-localSweepAngle);
			mCanvas.drawTextOnPath(str, path, 5, -10, mPaint);
			mCanvas.save();
		}

		public void drawItem(RectF localRectf) {
			for (int i = 0; i < itemCount; i++) {
				mPaint.setColor(itemColor[i]);
				sweepAngle = (float) (360 * ratioArray[i] / 100);
				if (!clockwise) {
					sweepAngle = 0 - sweepAngle;
				}
				mCanvas.drawArc(localRectf, startAngle, sweepAngle, true,
						mPaint);
				mCanvas.save();
				drawText(localRectf, startAngle, sweepAngle, titleString[i]);
				startAngle += sweepAngle;
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			// 公共在这里处理
			mPaint.setAntiAlias(true);
			while (!done) {
				updateView();
			}
		}
	}
}