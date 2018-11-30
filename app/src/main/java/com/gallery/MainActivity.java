package com.gallery;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gallery.base.BaseActivity;
import com.gallery.bean.FileCountBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private FileListAdapter mAdapter;
    private List<FileCountBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!applypermission()) {
            finish();
            return;
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.file_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FileListAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        getFileList();
    }

    private List<FileCountBean> getFileList() {
        List<File> fileList = new ArrayList<>();
        List<FileCountBean> beans = new ArrayList<>();
        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator;
        File fileAll = new File(filePath);
        if (fileAll.isDirectory()) {
            File[] files = fileAll.listFiles();
            for (File file : files) {
                Log.d(TAG, "file = " + file.getPath());
            }
            fileList = Arrays.asList(files);
        }
        for (final File file : fileList) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    int count = getCount(file.getPath());
                    if (count != 0) {
                        mData.add(new FileCountBean(file.getPath(), file.getName(), count));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }.start();
        }
        return beans;
    }

    private int getCount(String path) {
        return getGallery(new File(path)).size();
    }
}
