package com.example.specialeffectsandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LvAdapter extends BaseAdapter {

	private Context context;
	private String[] str = { "点击产生波纹效果", "圆形旋转菜单", "绘制时钟", "点击产生气泡帧动画",
			"3D页面旋转动画", "密码锁屏", "圆角listview", "Gallery实现页面点击切换", "旋转木马",
			"Gallery以屏幕为中心左右滑动", "自定义loading", "翻页效果", "添加快捷方式",
			"listview上拉刷新下拉加载更多", "产生图片点击后的发光效果", "播放gif图片", "通过反射获取应用程序大小",
			"部分动画", "获取手机中所有图片的信息", "图片操作", "音频录制", "视频处理", "抽屉式布局",
			"含有scrollview的屏幕左右滑动", "显示sd卡目录", "GestureOverlayView实现手势匹配",
			"定时更换壁纸", "带有标签的左右滑动", "ViewGroup实现屏幕左右滑动", "类似于大转盘",
			"listview中item点击后从右边画出界面", "图片放大缩小", "listview显示不同的item",
			"封面图片带倒影效果的滑动", "屏幕左中右三边滑动", "抽屉式滑动", "ListView 实现点击侧边A-Z快速查找demoOne",
			"ClipDrawable徐徐展开的风景", "FrameLayout霓虹灯效果", "异步加载图片的二级缓存技术",
			"跑马灯(ImageSwitcher animation gesture组合)", "双进度条", "从电脑端远程管理手机",
			"3d图片轮播器", "ViewPager超酷旋转", "Tab效果", "圆角图片", "画爱心", "类似于搜狐新闻的页面滑动",
			"Listview不规则项不同item的运行", "歌词滚动播放", "带有标签的左右滑动集合","属性动画测试 ",
			"ListView 实现点击侧边A-Z快速查找demotwo"};

	public LvAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		return 54;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv = new TextView(context);
		tv.setTextSize(20);
		tv.setTextColor(Color.BLACK);
		tv.setPadding(dip2px(context, 10), dip2px(context, 10),
				dip2px(context, 10), dip2px(context, 10));
		tv.setText(str[position]);
		return tv;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

}
