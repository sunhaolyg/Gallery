package com.gallery.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    protected static final String TAG = "MainActivityTAG";
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected boolean applypermission() {
        //检查是否已经给了权限
        int checkpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (checkpermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
            //参数分别是当前活动，权限字符串数组，requestcode
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //grantResults数组与权限字符串数组对应，里面存放权限申请结果
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionsResult(true);
        } else {
            permissionsResult(false);
        }
    }

    protected void permissionsResult(boolean result) {
    }

    protected List<String> getGallery(File fileAll) {
        List<String> paths = new ArrayList<>();
        if (fileAll == null) {
            String filePath = Environment.getExternalStorageDirectory().toString() + File.separator;
            fileAll = new File(filePath);
        }
        if (!fileAll.isDirectory()) {
            if (isImageFile(fileAll.getPath())) {
                paths.add(fileAll.getPath());
            }
            return paths;
        }
        Log.d(TAG, "fileAll = " + fileAll.getPath());
        File[] files = fileAll.listFiles();
        for (File file : files) {
            if (file != null) {
                paths.addAll(getGallery(file));
            }
        }
        return paths;
    }

    protected boolean isImageFile(String fName) {
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        return FileEnd.equals("jpg") || FileEnd.equals("png");
    }

}
