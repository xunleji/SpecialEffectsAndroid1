package com.example.specialeffectsandroid.cacheimage;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.example.specialeffectsandroid.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAsynLoader {

    private ImageDoubleCaches imageDoubleCaches = new ImageDoubleCaches();

    /**
     * 加载图片，如果缓存中有就直接从缓存中拿，缓存中没有就下载
     * 
     * @param url
     * @param adapter
     * @param mViewBusy 是否页面忙碌中
     * @param mImageView
     */
    public void loadImage(String url, BaseAdapter adapter, boolean mViewBusy, ImageView mImageView) {

        imageDoubleCaches.resetPurgeTimer();
        // 从缓存中读取
        Bitmap bitmap = imageDoubleCaches.getBitmapFromCache(url);

        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            // 在没有图片的情况下设置默认图片（可以是正在加载样式的图片）
            mImageView.setImageResource(R.drawable.ic_launcher);
            if (!mViewBusy) {
                // 异步任务去下载
                ImageLoadTask imageLoadTask = new ImageLoadTask();
                imageLoadTask.execute(url, adapter, mImageView);
            }
        }

    }

    class ImageLoadTask extends AsyncTask<Object, Void, Bitmap> {

        String url;

        BaseAdapter adapter;

        @Override
        protected Bitmap doInBackground(Object... params) {
            url = (String) params[0];
            adapter = (BaseAdapter) params[1];
            Bitmap drawable = loadImageFromInternet(url);// 获取网络图片
            return drawable;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                return;
            }
            imageDoubleCaches.addImage2Cache(url, result);// 放入缓存
            adapter.notifyDataSetChanged();// 触发getView方法执行，这个时候getView实际上会拿到刚刚缓存好的图片
        }
    }

    public Bitmap loadImageFromInternet(String url) {
    	Log.e("ImageAsynLoader", "loadImageFromInternet");
        Bitmap bitmap = null;
        HttpClient client = AndroidHttpClient.newInstance("Android");
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 3000);
        HttpConnectionParams.setSocketBufferSize(params, 3000);
        HttpResponse response = null;
        InputStream inputStream = null;
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(url);
            response = client.execute(httpGet);
            int stateCode = response.getStatusLine().getStatusCode();
            if (stateCode != HttpStatus.SC_OK) {
                return bitmap;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    inputStream = entity.getContent();
                    return bitmap = BitmapFactory.decodeStream(inputStream);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (ClientProtocolException e) {
            httpGet.abort();
            e.printStackTrace();
        } catch (IOException e) {
            httpGet.abort();
            e.printStackTrace();
        } finally {
            ((AndroidHttpClient) client).close();
        }
        return bitmap;
    }

    public ImageDoubleCaches getImageDoubleCaches() {
        return imageDoubleCaches;
    }

}
