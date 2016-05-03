
package com.example.specialeffectsandroid.paomd;

import com.example.specialeffectsandroid.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ExceptionDrawable extends Drawable {

    private Context context;
    private final String text;
    private final Bitmap bitmap;
    private final int width;
    private final int height;

    public ExceptionDrawable(Context context, String text) {
        this.context = context;
        this.text = text;
        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(
                R.drawable.image_logo);
        bitmap = drawable.getBitmap();
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }

    private Paint paint = new Paint();

    {
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Align.CENTER);
        paint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        float x = bounds.right - bounds.width() / 2;
        float y = bounds.bottom - bounds.height() / 2;
        canvas.drawText(text, x, y, paint);

        int dp2x = (int) (10 * context.getResources().getDisplayMetrics().density + 0.5f);
        canvas.drawBitmap(bitmap, x - (width / 2), y - (height / 2) + dp2x, paint);
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

}
