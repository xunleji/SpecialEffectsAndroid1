package com.example.specialeffectsandroid.refreshlist;

import com.example.specialeffectsandroid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingLayout extends FrameLayout {

	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	private final ImageView headerImage;
	private final ProgressBar headerProgress;
	private final TextView headerText;

	private String pullLabel;
	private String refreshingLabel;
	private String releaseLabel;
	private int mode;

	private final Animation rotateAnimation, resetRotateAnimation;

	public LoadingLayout(Context context, final int mode, String releaseLabel, String pullLabel, String refreshingLabel) {
		super(context);
		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, this);
		headerImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
		headerProgress = (ProgressBar) header.findViewById(R.id.pull_to_refresh_progress);
		headerText = (TextView) header.findViewById(R.id.pull_to_refresh_text);

		this.mode = mode;

		final Interpolator interpolator = new LinearInterpolator();
		rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setInterpolator(interpolator);
		rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		rotateAnimation.setFillAfter(true);

		resetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		resetRotateAnimation.setInterpolator(interpolator);
		resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		resetRotateAnimation.setFillAfter(true);

		this.releaseLabel = releaseLabel;
		this.pullLabel = pullLabel;
		this.refreshingLabel = refreshingLabel;

		switch (mode) {
		case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
			headerImage.setImageResource(R.drawable.pull_refresh);
			headerText.setText("上拉可以加载更多");
			break;
		case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
			headerText.setText("下拉可以刷新");

		default:
			headerImage.setImageResource(R.drawable.pull_refresh);
			break;
		}
	}

	public void reset() {
		if (mode == PullToRefreshBase.MODE_PULL_UP_TO_REFRESH) {
			headerText.setText("上拉可以加载更多");
		} else {
			headerText.setText("下拉可以刷新");
		}

		headerImage.setVisibility(View.VISIBLE);
		headerProgress.setVisibility(View.GONE);
	}

	public void releaseToRefresh() {
		if (mode == PullToRefreshBase.MODE_PULL_UP_TO_REFRESH) {
			headerText.setText("松开可以加载更多");
			headerImage.clearAnimation();
			headerImage.startAnimation(rotateAnimation);
		} else {
			headerText.setText("松开可以刷新");
			headerImage.clearAnimation();
			headerImage.startAnimation(resetRotateAnimation);
		}

	}

	public void setPullLabel(String pullLabel) {
		this.pullLabel = pullLabel;
	}

	public void refreshing() {
		headerText.setText("加载中...");
		headerImage.clearAnimation();
		headerImage.setVisibility(View.INVISIBLE);
		headerProgress.setVisibility(View.VISIBLE);
	}

	public void setRefreshingLabel(String refreshingLabel) {
		this.refreshingLabel = refreshingLabel;
	}

	public void setReleaseLabel(String releaseLabel) {
		this.releaseLabel = releaseLabel;
	}

	public void pullToRefresh() {
		if (mode == PullToRefreshBase.MODE_PULL_UP_TO_REFRESH) {
			headerImage.clearAnimation();
			headerImage.startAnimation(resetRotateAnimation);
		} else {
			headerImage.clearAnimation();
			headerImage.startAnimation(rotateAnimation);
		}
	}

	public void setTextColor(int color) {
		headerText.setTextColor(color);
	}

}
