package com.gallery.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImageCache extends LruCache<String, Bitmap> {

    private static final String TAG = "MainActivityTAG";
    private Map<String, SoftReference<Bitmap>> mCacheMap;

    public ImageCache() {
        super((int) (Runtime.getRuntime().maxMemory() / 16));
        mCacheMap = new LinkedHashMap<>();
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount();
//        return value.getRowBytes() * value.getHeight();
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {

        if (oldValue == null) {
            super.entryRemoved(evicted, key, oldValue, newValue);
        } else {
            SoftReference<Bitmap> bitmapSoftReference = new SoftReference<Bitmap>(oldValue);
            mCacheMap.put(key, bitmapSoftReference);
        }
    }

    public Map<String, SoftReference<Bitmap>> getCacheMap() {
        return mCacheMap;
    }

    public Bitmap getBitmap(String key) {
        Bitmap b = get(key);
        if (b == null && mCacheMap != null) {
            SoftReference<Bitmap> soft = mCacheMap.get(key);
            if (soft != null) {
                b = soft.get();
            }
        }
        return b;
    }


}
