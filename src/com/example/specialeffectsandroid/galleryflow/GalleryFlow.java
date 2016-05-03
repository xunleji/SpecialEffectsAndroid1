package com.example.specialeffectsandroid.galleryflow;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryFlow extends Gallery {

	private Camera mCamera = new Camera();
	private int mMaxRotationAngle = -1;
	private int mMaxZoom = -120;
	private int mCoveflowCenter;

	public GalleryFlow(Context context) {
		super(context);
		this.setStaticTransformationsEnabled(true);
	}

	public GalleryFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setStaticTransformationsEnabled(true);
	}

	public GalleryFlow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setStaticTransformationsEnabled(true);
	}

	public int getMaxRotationAngle() {
		return mMaxRotationAngle;
	}

	public void setMaxRotationAngle(int maxRotationAngle) {
		mMaxRotationAngle = maxRotationAngle;
	}

	public int getMaxZoom() {
		return mMaxZoom;
	}

	public void setMaxZoom(int maxZoom) {
		mMaxZoom = maxZoom;
	}

	private int getCenterOfCoverflow() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
	}

	private static int getCenterOfView(View view) {
		return view.getLeft() + view.getWidth() / 2;
	}

	protected boolean getChildStaticTransformation(View child, Transformation t) {
		final int childCenter = getCenterOfView(child);
		final int childWidth = child.getWidth();
		int rotationAngle = 0;
		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);

		rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
		if (Math.abs(rotationAngle) > mMaxRotationAngle) {
			rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;
		}
		int scale = Math.abs(mCoveflowCenter - childCenter);
		transformImageBitmap((ImageView) child, t, rotationAngle, scale);
		return true;
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCoveflowCenter = getCenterOfCoverflow();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * Transform the Image Bitmap by the Angle passed
	 * 
	 * @param imageView
	 *            ImageView the ImageView whose bitmap we want to rotate
	 * @param t
	 *            transformation
	 * @param rotationAngle
	 *            the Angle by which to rotate the Bitmap
	 */
	private void transformImageBitmap(ImageView child, Transformation t, int rotationAngle, int scale) {
		mCamera.save();

		final Matrix imageMatrix = t.getMatrix();
		final int imageHeight = child.getLayoutParams().height;
		final int imageWidth = child.getLayoutParams().width;
		final int rotation = Math.abs(rotationAngle);
		// 在Z轴上正向移动camera的视角，实际效果为放大图片。
		// 如果在Y轴上移动，则图片上下移动；X轴上对应图片左右移动。

		float f = (float) (scale * 0.3);
		mCamera.translate(0.0f, 0.0f, f);

		// As the angle of the view gets less, zoom in
		if (rotation < mMaxRotationAngle) {
			float zoomAmount = (float) (mMaxZoom + (rotation * 0.3));
			mCamera.translate(0.0f, 0.0f, zoomAmount);
		}

		// 在Y轴上旋转，对应图片竖向向里翻转。
		// 如果在X轴上旋转，则对应图片横向向里翻转。
		// if (setRotateY) {
		// mCamera.rotateY(rotationAngle);
		// }
		mCamera.getMatrix(imageMatrix);

		imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight));
		imageMatrix.postTranslate((imageWidth / 2), (imageHeight));

		mCamera.restore();

	}

	protected int getChildDrawingOrder(int childCount, int i) {
		long t = getSelectedItemId();
		int h = getSelectedItemPosition();
		if (i < childCount / 2) {
			return i;
		}
		return childCount - i - 1 + childCount / 2;
	}
}
