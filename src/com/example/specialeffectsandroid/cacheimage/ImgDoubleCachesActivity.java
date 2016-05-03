
package com.example.specialeffectsandroid.cacheimage;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * 功能描述： 如何将下载回来的图片进行缓存，为了节约流量，并且提高下一次显示图片的速度，提高用户体验，
 * 所以不能够每次调用getView的时候都去从网络下载图片，就必须用到缓存。
 * 缓存的重点问题：如何控制缓存的大小，如果我们一直向缓存中筛数据，而没有对缓存的大小进行控制，那么最终会导致OOM
 * 解决方案：设置两级缓存，第一级用LinkedHashMap<String,Bitmap>保留Bitmap的强引用，但是控制缓存的大小
 * MAX_CAPACITY=10，当继续向该缓存中存数据的时候，将会把一级缓存中的最近最少使用的元素放入二级缓存
 * ConcurrentHashMap<String, SoftReference<Bitmap>>，二级缓存中保留的Bitmap的软引用。
 * SoftReference：它保存的对象实例，除非JVM即将OutOfMemory
 * ，否则不会被GC回收。这个特性使得它特别适合设计对象Cache。对于Cache
 * ，我们希望被缓存的对象最好始终常驻内存，但是如果JVM内存吃紧，为了不发生OutOfMemoryError导致系统崩溃
 * ，必要的时候也允许JVM回收Cache的内存，待后续合适的时机再把数据重新Load到Cache中。这样可以系统设计得更具弹性。
 * 
 * @author njzhufeifei
 * @date 2013-2-5 下午2:13:41
 */
public class ImgDoubleCachesActivity extends Activity {

    private ListView mListview;

    ImgDoubleCacheAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.double_cache_view);
        setupViews();
    }

    private void setupViews() {
        mListview = (ListView) findViewById(R.id.main_lv_list);
        adapter = new ImgDoubleCacheAdapter(500, this);
        mListview.setAdapter(adapter);
        mListview.setOnScrollListener(mScrollListener);
    }

    OnScrollListener mScrollListener = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case OnScrollListener.SCROLL_STATE_FLING: // 滚屏过程中，不建议加载数据否则会卡
                    adapter.setFlagBusy(true);
                    break;
                case OnScrollListener.SCROLL_STATE_IDLE: // 静止状态
                    adapter.setFlagBusy(false);
                    break;
                case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动还没开始（手指还没离开屏幕）
                    adapter.setFlagBusy(false);
                    break;
                default:
                    break;
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                int visibleItemCount, int totalItemCount) {
        }
    };
}
