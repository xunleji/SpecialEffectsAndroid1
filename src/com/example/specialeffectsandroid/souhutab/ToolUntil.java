package com.example.specialeffectsandroid.souhutab;

import android.content.Context;

public class ToolUntil {
	// 像素转换
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
