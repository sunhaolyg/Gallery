package com.gallery;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gallery.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ThreadShowActivity extends BaseActivity {

    private static final String TAG = "MainActivityTAG";
    public static final int GALLERY_COLUM = 2;
    private RecyclerView mRecyclerView;
    private ThreadShowAdapter mAdapter;
    private List<PicBean> mData = new ArrayList<>();
    private TextView gallery_count, gallery_empty;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                gallery_empty.setText("no data");
            } else {
                gallery_empty.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        if (!applypermission()) {
            finish();
            return;
        }
        getData();
        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_grid);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, GALLERY_COLUM));
        mAdapter = new ThreadShowAdapter(this, mData, new ThreadShowAdapter.OnPositionCallback() {

            @Override
            public void onPosition(int position) {
                gallery_count.setText(position + "");
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        gallery_count = (TextView) findViewById(R.id.gallery_count);
        gallery_empty = (TextView) findViewById(R.id.gallery_empty);
    }

    private void getData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String path = getIntent().getStringExtra("path");
                mData.addAll(getPicBeans(getGallery(new File(path))));
                if (mData.size() == 0) {
                    mHandler.sendEmptyMessage(0);
                } else {
                    mHandler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    private List<PicBean> getPicBeans(List<String> paths) {
        List<PicBean> beans = new ArrayList<>();
        for (String path : paths) {
            if (isCorrect(path)) {
                PicBean bean = new PicBean(path);
                beans.add(bean);
            }
        }
        return beans;
    }

    private boolean isCorrect(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int h = options.outHeight;
        int w = options.outWidth;
//        return h == 1440 && w == 720;
        return true;
    }

}
