package com.gallery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryDetailsActivity extends Activity {

    public static final int GALLERY_COLUM = 2;
    private MyZoomImageView gallery_details;
    private TextView details_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_details);
        gallery_details = findViewById(R.id.gallery_details);
        details_info = findViewById(R.id.details_info);
        String path = getIntent().getStringExtra("path");
        if (TextUtils.isEmpty(path)) {
            finish();
            return;
        }
        Bitmap bitmap = getBitmapReal(path);
        if (bitmap == null) {
            finish();
            return;
        }
        gallery_details.setImageBitmap(bitmap);
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
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        int base = dm2.widthPixels;
        int wSize = w / base;
        int hSize = h / base;
        details_info.setText(w + "," + h + "," + base);
        int scale = 1;
        if (h / w > 10 || w / h > 10) {
            scale = 2;
        }
        return Math.min(wSize, hSize) * scale;
    }

}
