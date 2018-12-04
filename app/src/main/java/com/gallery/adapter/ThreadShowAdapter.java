package com.gallery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gallery.R;
import com.gallery.activity.GalleryDetailsActivity;
import com.gallery.activity.ThreadShowActivity;
import com.gallery.bean.PicBean;
import com.gallery.cache.ImageCache;
import com.gallery.widget.HeightImageView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadShowAdapter extends RecyclerView.Adapter<ThreadShowAdapter.ViewHolder> {

    private static final String TAG = "MainActivityTAG";
    private final int mScreenWidth;
    private final ExecutorService mExecutorService;
    private Thread mWorkThread;
    private Context mContext;
    private List<PicBean> mData;
    private ImageCache mMemoryCache;

    public ThreadShowAdapter(Context context, List<PicBean> data, OnPositionCallback callback) {
        mOnPositionCallback = callback;
        mContext = context;
        mData = data;
        mMemoryCache = new ImageCache();
        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
        mScreenWidth = dm2.widthPixels;
        mExecutorService = Executors.newFixedThreadPool(5);

    }

    public List<PicBean> getData() {
        return mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gallery_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mOnPositionCallback != null) {
            mOnPositionCallback.onPosition(position);
        }
        PicBean bean = mData.get(position);
        getBitmap(holder.iv, bean.getPath());
        holder.position.setText(position + "");
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GalleryDetailsActivity.class);
                intent.putExtra("path", mData.get(position).getPath());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void getBitmap(final HeightImageView iv, final String path) {
        Bitmap b = mMemoryCache.getBitmap(path);
        if (b != null) {
            iv.setBitmap(b);
            return;
        }
        mWorkThread = new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapReal(path);
                iv.setBitmap(bitmap);
                mMemoryCache.put(path, bitmap);
            }
        };
        mExecutorService.submit(mWorkThread);
    }

    private Bitmap getBitmapReal(String path) {
        Bitmap bitmap = null;
        if (bitmap != null) {
            return bitmap;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(path, options);

        options.inJustDecodeBounds = false;
        options.inSampleSize = getScale(options.outWidth, options.outHeight);
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    private int getScale(int w, int h) {
        int base = mScreenWidth / ThreadShowActivity.GALLERY_COLUM;
        int wSize = w / base;
        int hSize = h / base;

        return Math.min(wSize, hSize);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        HeightImageView iv;
        TextView position;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.gallery_item_iv);
            position = itemView.findViewById(R.id.gallery_item_position);
        }

    }

    public interface OnPositionCallback {

        void onPosition(int position);

    }

    private OnPositionCallback mOnPositionCallback;

    public void onDestroy() {
        mExecutorService.shutdown();
    }
}
