package com.dzg.takeaway.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.util.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewBigImageActivity extends FragmentActivity implements OnPageChangeListener, PhotoViewAttacher.OnPhotoTapListener {

    private TextView nameTv;
    // 接收传过来的uri地址
    ArrayList<RestaurantDetail.AlbumsBean> albumsBeans;
    // 接收穿过来当前选择的图片的数量
    int code;
    // 用于判断是头像还是文章图片 1:头像 2：文章大图
    int selet;

    // 用于管理图片的滑动
    ViewPager very_image_viewpager;
    // 当前页数
    private int page;

    /**
     * 显示当前图片的页数
     */
    TextView very_image_viewpager_text;
    /**
     * 用于判断是否是加载本地图片
     */
    private boolean isLocal;

    ViewPagerAdapter adapter;

    /**
     * 本应用图片的id
     */
    private int imageId;
    /**
     * 是否是本应用中的图片
     */
    private boolean isApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_big_image);
        getView();
    }

    /**
     * 保存图片至相册
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "云阅相册");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsoluteFile())));
    }


    /**
     * Glide 获得图片缓存路径
     */
    private String getImagePath(String imgUrl) {
        String path = null;
        FutureTarget<File> future = Glide.with(ViewBigImageActivity.this)
                .load(imgUrl)
                .downloadOnly(500, 500);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return path;
    }


    /*
     * 接收控件
     */
    private void getView() {
        very_image_viewpager_text = findViewById(R.id.image_title_bottom_centertv);
        nameTv = findViewById(R.id.image_title_left_bottomtv);
        very_image_viewpager = findViewById(R.id.very_image_viewpager);
        Bundle bundle = getIntent().getExtras();
        code = bundle.getInt("code");
        selet = bundle.getInt("selet");
        isLocal = bundle.getBoolean("isLocal", false);
        albumsBeans = (ArrayList<RestaurantDetail.AlbumsBean>) bundle.getSerializable("albumsBeans");
        isApp = bundle.getBoolean("isApp", false);
        imageId = bundle.getInt("id", 0);
        if (isApp) {
            MyPageAdapter myPageAdapter = new MyPageAdapter();
            very_image_viewpager.setAdapter(myPageAdapter);
            very_image_viewpager.setEnabled(false);
        } else {
            adapter = new ViewPagerAdapter();
            very_image_viewpager.setAdapter(adapter);
            very_image_viewpager.setCurrentItem(code);
            page = code;
            very_image_viewpager.setOnPageChangeListener(this);
            very_image_viewpager.setEnabled(false);
            // 设定当前的页数和总页数
            if (selet == 2) {
                very_image_viewpager_text.setText((code + 1) + " / " + albumsBeans.size());
            }
        }
    }
    class MyPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.viewpager_very_image, container, false);
            PhotoView zoom_image_view =view.findViewById(R.id.zoom_image_view);
            ProgressBar spinner = view.findViewById(R.id.loading);
            spinner.setVisibility(View.GONE);
            if (imageId != 0) {
                zoom_image_view.setImageResource(imageId);
            }
            zoom_image_view.setOnPhotoTapListener(ViewBigImageActivity.this);
            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
    class ViewPagerAdapter extends PagerAdapter {

        LayoutInflater inflater;

        ViewPagerAdapter() {
            inflater = getLayoutInflater();
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.viewpager_very_image, container, false);
            final PhotoView zoom_image_view =  view.findViewById(R.id.zoom_image_view);
            final ProgressBar spinner =  view.findViewById(R.id.loading);
            String adapter_image_Entity =TextUtils.formatLagerImageUrl(((RestaurantDetail.AlbumsBean) getItem(position)).getCover_image_hash());
            String imageUrl;
            if (isLocal) {
                imageUrl = "file://" + adapter_image_Entity;
                nameTv.setVisibility(View.GONE);
            } else {
                imageUrl = adapter_image_Entity;
            }
            spinner.setVisibility(View.VISIBLE);
            spinner.setClickable(false);
            Glide.with(ViewBigImageActivity.this).load(imageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Toast.makeText(getApplicationContext(), "资源加载异常", Toast.LENGTH_SHORT).show();
                            spinner.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            spinner.setVisibility(View.GONE);
                            int height = zoom_image_view.getHeight();

                            int wHeight = getWindowManager().getDefaultDisplay().getHeight();
                            if (height > wHeight) {
                                zoom_image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                zoom_image_view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            return false;
                        }
                    }).into(zoom_image_view);
            zoom_image_view.setOnPhotoTapListener(ViewBigImageActivity.this);
            container.addView(view, 0);
            return view;
        }

        @Override
        public int getCount() {
            if (albumsBeans == null || albumsBeans.size() == 0) {
                return 0;
            }
            return albumsBeans.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        Object getItem(int position) {
            return albumsBeans.get(position);
        }
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }
    @Override
    public void onPageSelected(int arg0) {
        very_image_viewpager_text.setText((arg0 + 1) + " / " + albumsBeans.size());
        page = arg0;
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        finish();
    }
}