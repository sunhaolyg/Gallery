package com.gallery.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class HeightImageView extends ImageView {

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setImageBitmap((Bitmap) msg.obj);
        }
    };

    public HeightImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    public void setBitmap(Bitmap bitmap) {
        if (Thread.currentThread().getName().equals("main")) {
            setImageBitmap(bitmap);
            return;
        }
        Message msg = mHandler.obtainMessage();
        msg.obj = bitmap;
        mHandler.sendMessage(msg);
    }
}
