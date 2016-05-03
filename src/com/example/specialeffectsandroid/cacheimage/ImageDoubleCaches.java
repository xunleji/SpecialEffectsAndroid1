
package com.example.specialeffectsandroid.cacheimage;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;
import android.os.Handler;

public class ImageDoubleCaches {

    /** 一级缓存的最大空间 */
    private static final int MAX_CAPACITY = 10;

    /** 定时清理缓存 */
    private static final long DELAY_BEFORE_PURGE = 10 * 1000;

    /** 0.75是加载因子为经验值，true则表示按照最近访问量的高低排序，false则表示按照插入顺序排序 */
    private HashMap<String, Bitmap> mFirstLevelCache =
            new LinkedHashMap<String, Bitmap>(MAX_CAPACITY / 2, 0.75f, true) {

                private static final long serialVersionUID = 1L;

                @Override
                protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
                    // 当超过一级缓存阈值的时候，将老的值从一级缓存搬到二级缓存
                    if (size() > MAX_CAPACITY) {
                        mSecondLevelCache.put(eldest.getKey(),
                                new SoftReference<Bitmap>(eldest.getValue()));
                        return true;
                    }
                    return false;
                };
            };

    /** 二级缓存，采用的是软应用，只有在内存吃紧的时候软应用才会被回收，有效的避免了oom */
    private ConcurrentHashMap<String, SoftReference<Bitmap>> mSecondLevelCache =
            new ConcurrentHashMap<String, SoftReference<Bitmap>>();

    /** 定时清理缓存 */
    private Runnable mClearCache = new Runnable() {
        @Override
        public void run() {
            clear();
        }
    };

    private Handler mPurgeHandler = new Handler();

    /**
     * 重置缓存清理的timer
     */
    public void resetPurgeTimer() {
        mPurgeHandler.removeCallbacks(mClearCache);
        mPurgeHandler.postDelayed(mClearCache, DELAY_BEFORE_PURGE);
    }

    /**
     * 清理缓存
     */
    public void clear() {
        mFirstLevelCache.clear();
        mSecondLevelCache.clear();
    }

    /**
     * 返回缓存，如果没有则返回null
     * 
     * @param key
     * @return
     */
    public Bitmap getBitmapFromCache(String key) {
        Bitmap bitmap = null;
        bitmap = getFromFirstLevelCache(key);// 从一级缓存中拿
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = getFromSecondLevelCache(key);// 从二级缓存中拿
        return bitmap;
    }

    /**
     * 从二级缓存中拿
     * 
     * @param key
     * @return
     */
    private Bitmap getFromSecondLevelCache(String key) {
        Bitmap bitmap = null;
        SoftReference<Bitmap> softReference = mSecondLevelCache.get(key);
        if (softReference != null) {
            bitmap = softReference.get();
            // 由于内存吃紧，软引用已经被gc回收了
            if (bitmap == null) {
                mSecondLevelCache.remove(key);
            }
        }
        return bitmap;
    }

    /**
     * 从一级缓存中拿
     * 
     * @param url
     * @return
     */
    private Bitmap getFromFirstLevelCache(String key) {
        Bitmap bitmap = null;
        synchronized (mFirstLevelCache) {
            bitmap = mFirstLevelCache.get(key);
            // 将最近访问的元素放到链的头部，提高下一次访问该元素的检索速度（LRU算法）
            if (bitmap != null) {
                mFirstLevelCache.remove(key);
                mFirstLevelCache.put(key, bitmap);
            }
        }
        return bitmap;
    }

    /**
     * 放入缓存
     * 
     * @param url
     * @param value
     */
    public void addImage2Cache(String key, Bitmap value) {
        if (value == null || key == null) {
            return;
        }
        synchronized (mFirstLevelCache) {
            mFirstLevelCache.put(key, value);
        }
    }

}
