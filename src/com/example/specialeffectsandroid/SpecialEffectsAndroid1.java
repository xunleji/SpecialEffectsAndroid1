package com.example.specialeffectsandroid;

import com.example.specialeffectsandroid.animation.AnimationDemo;
import com.example.specialeffectsandroid.animation.PropertyAnimationActivity;
import com.example.specialeffectsandroid.biaoqian.ViewPagerTest;
import com.example.specialeffectsandroid.bubble.BubbleActivity;
import com.example.specialeffectsandroid.cacheimage.ImgDoubleCachesActivity;
import com.example.specialeffectsandroid.canvasclock.SmartClockActivity;
import com.example.specialeffectsandroid.carousel.CarouselActivity;
import com.example.specialeffectsandroid.changewall.ChangeWallActivity;
import com.example.specialeffectsandroid.circlemenu.CircleMenuActivity;
import com.example.specialeffectsandroid.clipdrawables.ClipDrawableViewActivity;
import com.example.specialeffectsandroid.cornerimage.CornImageActivity;
import com.example.specialeffectsandroid.cornlist.CornListActivity;
import com.example.specialeffectsandroid.coverflow.CoverflowDemoActivity;
import com.example.specialeffectsandroid.customloading.CustomLoadingActivity;
import com.example.specialeffectsandroid.differentlv.DifferlvActivity;
import com.example.specialeffectsandroid.diffitem.TestAndroidActivity;
import com.example.specialeffectsandroid.drawerlayout.Drawlayoumain;
import com.example.specialeffectsandroid.drawlove.CanvasDemoActivity;
import com.example.specialeffectsandroid.fanye.FanyeActivity;
import com.example.specialeffectsandroid.fltext.FrameLayoutActivity;
import com.example.specialeffectsandroid.galleryflow.GalleryFlowActivity;
import com.example.specialeffectsandroid.galleryview.FlingGalleryActivity;
import com.example.specialeffectsandroid.gesture.GestureActivity;
import com.example.specialeffectsandroid.getimage.ImageActivity;
import com.example.specialeffectsandroid.getimage.PhotoProcess;
import com.example.specialeffectsandroid.homelcenterr.QQmini2Activity;
import com.example.specialeffectsandroid.imageplay.Image3dActivity;
import com.example.specialeffectsandroid.lightbtn.BitmapAlphaActivity;
import com.example.specialeffectsandroid.lockpass.LockPassActivity;
import com.example.specialeffectsandroid.lvitemadd.Main;
import com.example.specialeffectsandroid.lvsearch.TestActivity;
import com.example.specialeffectsandroid.lvsearch.demoone.ListIndexActivity;
import com.example.specialeffectsandroid.packageinfo.PackageInfo;
import com.example.specialeffectsandroid.panel.TestPanels;
import com.example.specialeffectsandroid.paomd.PaomadengActivity;
import com.example.specialeffectsandroid.playgif.gifActivity;
import com.example.specialeffectsandroid.refreshlist.RefreshListActivity;
import com.example.specialeffectsandroid.remotephone.RemotePhoneActivity;
import com.example.specialeffectsandroid.rotate3d.RotateActivity;
import com.example.specialeffectsandroid.scaleimage.MainActivity;
import com.example.specialeffectsandroid.scrollscreen.ScrollScreenActivity;
import com.example.specialeffectsandroid.sdexploer.SDFileExplorer;
import com.example.specialeffectsandroid.shortcut.ShortCutActivity;
import com.example.specialeffectsandroid.souhutab.SouHuTabActivity;
import com.example.specialeffectsandroid.soundrecord.AudioRecordDemo;
import com.example.specialeffectsandroid.tabscroll.SimpleHomeActivity;
import com.example.specialeffectsandroid.videorecord.VideoDemo;
import com.example.specialeffectsandroid.viewgrouphua.ScrollLayoutTest;
import com.example.specialeffectsandroid.viewpager.ViewpagerActivity;
import com.example.specialeffectsandroid.waterwave.WaterWaveActivity;
import com.example.specialeffectsandroid.zhuanpan.WheelMain;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SpecialEffectsAndroid1 extends Activity {

	private Intent intent;
	private LvAdapter lvAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView lv = (ListView) findViewById(R.id.listView1);
		lv.setOnItemClickListener(onitem);
		lvAdapter = new LvAdapter(this);
		lv.setAdapter(lvAdapter);

	}

	private OnItemClickListener onitem = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg2 == 0) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						WaterWaveActivity.class);
				startActivity(intent);
			} else if (arg2 == 1) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						CircleMenuActivity.class);
				startActivity(intent);
			} else if (arg2 == 2) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						SmartClockActivity.class);
				startActivity(intent);
			} else if (arg2 == 3) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						BubbleActivity.class);
				startActivity(intent);
			} else if (arg2 == 4) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						RotateActivity.class);
				startActivity(intent);
			} else if (arg2 == 5) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						LockPassActivity.class);
				startActivity(intent);
			} else if (arg2 == 6) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						CornListActivity.class);
				startActivity(intent);
			} else if (arg2 == 7) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						FlingGalleryActivity.class);
				startActivity(intent);
			} else if (arg2 == 8) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						CarouselActivity.class);
				startActivity(intent);
			} else if (arg2 == 9) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						GalleryFlowActivity.class);
				startActivity(intent);
			} else if (arg2 == 10) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						CustomLoadingActivity.class);
				startActivity(intent);
			} else if (arg2 == 11) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						FanyeActivity.class);
				startActivity(intent);
			} else if (arg2 == 12) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						ShortCutActivity.class);
				startActivity(intent);
			} else if (arg2 == 13) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						RefreshListActivity.class);
				startActivity(intent);
			} else if (arg2 == 14) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						BitmapAlphaActivity.class);
				startActivity(intent);
			} else if (arg2 == 15) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						gifActivity.class);
				startActivity(intent);
			} else if (arg2 == 16) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						PackageInfo.class);
				startActivity(intent);
			} else if (arg2 == 17) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						AnimationDemo.class);
				startActivity(intent);
			} else if (arg2 == 18) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						ImageActivity.class);
				startActivity(intent);
			} else if (arg2 == 19) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						PhotoProcess.class);
				startActivity(intent);
			} else if (arg2 == 20) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						AudioRecordDemo.class);
				startActivity(intent);
			} else if (arg2 == 21) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						VideoDemo.class);
				startActivity(intent);
			} else if (arg2 == 22) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						Drawlayoumain.class);
				startActivity(intent);
			} else if (arg2 == 23) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						ScrollScreenActivity.class);
				startActivity(intent);
			} else if (arg2 == 24) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						SDFileExplorer.class);
				startActivity(intent);
			} else if (arg2 == 25) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						GestureActivity.class);
				startActivity(intent);
			} else if (arg2 == 26) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						ChangeWallActivity.class);
				startActivity(intent);
			} else if (arg2 == 27) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						ViewPagerTest.class);
				startActivity(intent);
			} else if (arg2 == 28) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						ScrollLayoutTest.class);
				startActivity(intent);
			} else if (arg2 == 29) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						WheelMain.class);
				startActivity(intent);
			} else if (arg2 == 30) {
				intent = new Intent(SpecialEffectsAndroid1.this, Main.class);
				startActivity(intent);
			} else if (arg2 == 31) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						MainActivity.class);
				startActivity(intent);
			} else if (arg2 == 32) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						TestAndroidActivity.class);
				startActivity(intent);
			} else if (arg2 == 33) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						CoverflowDemoActivity.class);
				startActivity(intent);
			} else if (arg2 == 34) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						QQmini2Activity.class);
				startActivity(intent);
			} else if (arg2 == 35) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						TestPanels.class);
				startActivity(intent);
			} else if (arg2 == 36) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						TestActivity.class);
				startActivity(intent);
			} else if (arg2 == 37) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						ClipDrawableViewActivity.class);
				startActivity(intent);
			} else if (arg2 == 38) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						FrameLayoutActivity.class);
				startActivity(intent);
			} else if (arg2 == 39) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						ImgDoubleCachesActivity.class);
				startActivity(intent);
			} else if (arg2 == 40) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						PaomadengActivity.class);
				startActivity(intent);
			} else if (arg2 == 41) {
				intent = new Intent(
						SpecialEffectsAndroid1.this,
						com.example.specialeffectsandroid.doubleseekbar.MainActivity.class);
				startActivity(intent);
			} else if (arg2 == 42) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						RemotePhoneActivity.class);
				startActivity(intent);
			} else if (arg2 == 43) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						Image3dActivity.class);
				startActivity(intent);
			} else if (arg2 == 44) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						ViewpagerActivity.class);
				startActivity(intent);
			} else if (arg2 == 45) {
				intent = new Intent(
						SpecialEffectsAndroid1.this,
						com.example.specialeffectsandroid.tab.MainActivity.class);
				startActivity(intent);
			} else if (arg2 == 46) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						CornImageActivity.class);
				startActivity(intent);
			} else if (arg2 == 47) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						CanvasDemoActivity.class);
				startActivity(intent);
			} else if (arg2 == 48) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						SouHuTabActivity.class);
				startActivity(intent);
			} else if (arg2 == 49) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						DifferlvActivity.class);
				startActivity(intent);
			} else if (arg2 == 50) {
				intent = new Intent(
						SpecialEffectsAndroid1.this,
						com.example.specialeffectsandroid.geci.MainActivity.class);
				startActivity(intent);
			} else if (arg2 == 51) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						SimpleHomeActivity.class);
				startActivity(intent);
			}else if (arg2 == 52) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						PropertyAnimationActivity.class);
				startActivity(intent);
			}else if (arg2 == 53) {
				intent = new Intent(SpecialEffectsAndroid1.this,
						ListIndexActivity.class);
				startActivity(intent);
			}
		}
	};

}
