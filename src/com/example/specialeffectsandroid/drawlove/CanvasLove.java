package com.example.specialeffectsandroid.drawlove;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CanvasLove extends SurfaceView implements SurfaceHolder.Callback,Runnable{

	private boolean mbloop = false;
	private SurfaceHolder holder = null;
	private Canvas canvas = null;
	private int mCount = 0;
	private int y = 50;
	
	public CanvasLove(Context context) {
		super(context);
		holder = this.getHolder();
		holder.addCallback(this);
		this.setFocusable(true);
		this.setKeepScreenOn(true);
		mbloop = true;
		// TODO Auto-generated constructor stub
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		new Thread(this).start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mbloop = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(mbloop){
			try{
				Thread.sleep(200);
			}catch (Exception e) {
				// TODO: handle exception
			}synchronized (holder) {
				Draw();
			}
		}
	}


	private void Draw() {
		// TODO Auto-generated method stub
		canvas = holder.lockCanvas();
		try{
			if(holder ==null || canvas ==null){
				return ;
			}
			if(mCount <100){
				mCount++;
			}else{
				mCount = 0;
			}
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			canvas.drawRect(0, 0, 320, 480, paint);
			switch (mCount%6) {
			case 0:
				paint.setColor(Color.BLUE);
				break;
			case 1:
				paint.setColor(Color.GREEN);
				break;
			case 2:
				paint.setColor(Color.RED);
				break;
			case 3:
				paint.setColor(Color.YELLOW);
				break;
			case 4:
				paint.setColor(Color.argb(255, 255, 181, 216));
				break;
			case 5:
				paint.setColor(Color.argb(255, 0, 255, 255));
				break;
			default:
				paint.setColor(Color.WHITE);
				break;
			}
			int i,j;
			double x,y,r;
			for(i = 0;i<=90;i++){
				for(j = 0;j<=90;j++){
					r = Math.PI/45*i*(1-Math.sin(Math.PI/45*j))*20;
					x = r*Math.cos(Math.PI/45*j)*Math.sin(Math.PI/45*i)+320/2;
					y = -r*Math.sin(Math.PI/45*j)+400/4;
					canvas.drawPoint((float)x, (float)y, paint);
				}
			}
			paint.setTextSize(32);
			paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.ITALIC));
			RectF  rect = new RectF(60, 400, 260, 405);
			canvas.drawRoundRect(rect, (float)1.0, (float)1.0, paint);
			canvas.drawText("louis koo", 75, 400, paint);
			holder.unlockCanvasAndPost(canvas);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
