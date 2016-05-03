package com.example.specialeffectsandroid.waterwave;

import com.example.specialeffectsandroid.Diary;
import com.example.specialeffectsandroid.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class WaterWaveView extends SurfaceView implements SurfaceHolder.Callback {

	private int backWidth;
	private int backHeight;
	private short[] buf1;
	private short[] buf2;

	private int[] bitmap1;
	private int[] bitmap2;

	private Bitmap bgImage = null;

	private boolean firstLoad = false;

	WavingThread wavingThread = new WavingThread();

	SurfaceHolder mSurfaceHolder = null;

	private int doubleWidth;

	private int fiveWidth;

	private int loopTime;

	private int bitmapLen;

	public WaterWaveView(Context context) {
		super(context);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (!firstLoad) {
			bgImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.bg);
			bgImage = Bitmap.createScaledBitmap(bgImage, w, h, false);// ���Ŷ���
			backWidth = bgImage.getWidth();
			backHeight = bgImage.getHeight();

			buf2 = new short[backWidth * backHeight];
			buf1 = new short[backWidth * backHeight];

			bitmap2 = new int[backWidth * backHeight];
			bitmap1 = new int[backWidth * backHeight];

			bgImage.getPixels(bitmap1, 0, backWidth, 0, 0, backWidth, backHeight);
			bgImage.getPixels(bitmap2, 0, backWidth, 0, 0, backWidth, backHeight);

			for (int i = 0; i < backWidth * backHeight; ++i) {
				buf2[i] = 0;
				buf1[i] = 0;
			}
			doubleWidth = backWidth << 1;

			fiveWidth = 5 * backWidth;

			loopTime = ((backHeight - 4) * backWidth) >> 1;

			bitmapLen = backWidth * backHeight - 1;

			firstLoad = true;
		}

	}

	public static boolean running = true;

	class WavingThread extends Thread {

		@Override
		public void run() {
			Canvas c = null;
			while (running) {
				c = mSurfaceHolder.lockCanvas();
				makeRipple();
				if (c != null) {
					doDraw(c);
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}

	private void makeRipple() {
		int k = fiveWidth;
		int xoff = 0, yoff = 0;
		int cp = 0;

		int tarClr = 0;
		int i = fiveWidth;
		while (i < loopTime) {

			buf2[k] = (short) (((buf1[k - 2] + buf1[k + 2] + buf1[k - doubleWidth] + buf1[k + doubleWidth]) >> 1) - buf2[k]);

			buf2[k] = (short) (buf2[k] - (buf2[k] >> 5));

			cp = k - doubleWidth - 2;

			xoff = buf2[cp - 2] - buf2[cp + 2];
			yoff = buf2[cp - doubleWidth] - buf2[k - 2];

			tarClr = k + yoff * doubleWidth + xoff;
			if (tarClr > bitmapLen || tarClr < 0) {
				k += 2;
				continue;
			}
			bitmap2[k] = bitmap1[tarClr];
			k += 2;
			++i;
		}

		short[] tmpBuf = buf2;
		buf2 = buf1;
		buf1 = tmpBuf;

	}

	/*****************************************************
	 *****************************************************/
	private void touchWater(int x, int y, int stonesize, int stoneweight) {
		if (x + stonesize > backWidth) {
			return;
		}
		if (y + stonesize > backHeight) {
			return;
		}
		if (x - stonesize < 0) {
			return;
		}
		if (y - stonesize < 0) {
			return;
		}
		int endStoneX = x + stonesize;
		int endStoneY = y + stonesize;
		int squaSize = stonesize * stonesize;
		int posy = y - stonesize;
		int posx = x - stonesize;
		for (posy = y - stonesize; posy < endStoneY; ++posy) {
			for (posx = x - stonesize; posx < endStoneX; ++posx) {
				if ((posx - x) * (posx - x) + (posy - y) * (posy - y) < squaSize) {
					buf1[backWidth * posy + posx] = (short) -stoneweight;
				}
			}
		}

	}

	private void trickWater(int x, int y, int stonesize, int stoneweight) {
		if (x + stonesize > backWidth) {
			return;
		}
		if (y + stonesize > backHeight) {
			return;
		}
		if (x - stonesize < 0) {
			return;
		}
		if (y - stonesize < 0) {
			return;
		}

		int endStoneX = x + stonesize;
		int endStoneY = y + stonesize;
		int posy = y - stonesize;
		int posx = x - stonesize;
		for (posy = y - stonesize; posy < endStoneY; ++posy) {
			for (posx = x - stonesize; posx < endStoneX; ++posx) {
				if (posy >= 0 && posy < backHeight && posx >= 0 && posx < backWidth) {
					buf1[backWidth * posy + posx] = (short) -stoneweight;
				}
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			touchWater((int) event.getX(), (int) event.getY(), 4, 160);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			trickWater((int) event.getX(), (int) event.getY(), 2, 64);
		}
		return true;
	}

	protected void doDraw(Canvas canvas) {
		canvas.drawBitmap(bitmap2, 0, backWidth, 0, 0, backWidth, backHeight, false, null);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		running = true;
		wavingThread.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		running = false;
		try {
			wavingThread.join(); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
