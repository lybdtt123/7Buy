package self.androidbase.activity;

import android.Manifest;
import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import self.androidbase.R;
import self.androidbase.extend.SelfAnimationListener;
import self.androidbase.utils.DensityUtils;
import self.androidbase.utils.MD5Encrypt;

/**
 * Created by Janesen on 16/2/19.
 */
public class SelectImageActivity extends TakePhotoActivity implements View.OnClickListener {

    /**
     * 图片选择的上限 int
     */
    public static final String EXTRA_MAX_COUNT = "count";

    /**
     * 选择栏的主题颜色
     */
    public static final String EXTRA_THEME_COLOR = "theme_color";

    /**
     * 返回的图片路径
     */
    public static final String RESULT_IMAGE_PATHS = "paths";

    private Context context;
    private Handler handler = new Handler();

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private AlbumAdapter adapter;


    private ScrollView scrollViewAlbum;
    private LinearLayout llAlbums, llSelectedImages;
    private TextView tvCurrAlbumName, tvConfirm;


    private HorizontalScrollView hScrollView;
    private String cameraSavePath;

    private AlbumEntity currAlbum = new AlbumEntity();
    private Set<String> selectedImages = new LinkedHashSet<>();


    private int maxSelectedCount = -1;
    private int position = -1;//记录是哪一项发出的选择图片的操作

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_base_select_image);
        if (getIntent().getExtras().containsKey(EXTRA_MAX_COUNT)) {
            maxSelectedCount = getIntent().getExtras().getInt(EXTRA_MAX_COUNT);

        }

        if (null != getIntent().getExtras()) {
            position = getIntent().getExtras().getInt("position");
        }

        if (getIntent().getExtras().containsKey(EXTRA_THEME_COLOR)) {
            int color = getIntent().getExtras().getInt(EXTRA_THEME_COLOR);
            findViewById(R.id.android_base_select_bar).setBackgroundColor(color);
            findViewById(R.id.android_base_select_bottom_bar).setBackgroundColor(ColorUtils.setAlphaComponent(color, (int) (255 * 0.7)));
        }
        context = this;
        initView();
        initAlbums();
    }

    public void initView() {
        tvCurrAlbumName = (TextView) findViewById(R.id.android_base_tvCurrAlbumName);
        tvConfirm = (TextView) findViewById(R.id.android_base_tvConfirm);
        recyclerView = (RecyclerView) findViewById(R.id.android_base_recyclerView);
        scrollViewAlbum = (ScrollView) findViewById(R.id.android_base_scrollViewAlbum);
        llAlbums = (LinearLayout) findViewById(R.id.android_base_llAlbums);
        llSelectedImages = (LinearLayout) findViewById(R.id.android_base_llSelected);
        hScrollView = (HorizontalScrollView) findViewById(R.id.android_base_hScrollView);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new AlbumItemDecoration());
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new AlbumAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewCacheSize(50);
        if (Build.VERSION.SDK_INT > 11) {
            llSelectedImages.setLayoutTransition(new LayoutTransition());
        }


        findViewById(R.id.android_base_sllAlbumBtn).setOnClickListener(this);
        findViewById(R.id.android_base_sllCloseBtn).setOnClickListener(this);
        findViewById(R.id.android_base_viewback).setOnClickListener(this);
        findViewById(R.id.android_base_llCamera).setOnClickListener(this);
        findViewById(R.id.android_base_tvConfirm).setOnClickListener(this);
    }


    public void refreshConfirmText() {
        if (selectedImages.size() > 0) {
            tvConfirm.setText("确定(" + selectedImages.size() + ")");
        } else {
            tvConfirm.setText("确定");
        }
    }


    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.android_base_sllAlbumBtn || v.getId() == R.id.android_base_viewback) {
            showOrHideAlbumPanel();
        } else if (v.getId() == R.id.android_base_llAlbumItem) {
            currAlbum = (AlbumEntity) v.getTag();
            showOrHideAlbumPanel();
            tvCurrAlbumName.setText(currAlbum.getAlbumName());
            adapter.notifyDataSetChanged();
        } else if (v.getId() == R.id.android_base_llCamera) {

            cameraSavePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + +System.currentTimeMillis()
                    + ".jpg";
//            File file = new File(cameraSavePath);
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
//
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//            intent.putExtra("outputFormat", "JPEG");
//            startActivityForResult(intent, 3);

            Uri imageUri = Uri.fromFile(new File(cameraSavePath));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "请打开相机权限", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(SelectImageActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                return;
            }
            getTakePhoto().onPickFromCapture(imageUri);

        } else if (v.getId() == R.id.android_base_sllClose) {
            try {
                JSONObject json = (JSONObject) v.getTag();
                int index = json.getInt("index");
                String imagePath = json.getString("imagePath");
                String albumId = json.getString("albumId");
                if (index >= 0 && currAlbum.getAlbumId().equals(albumId)) {
                    currAlbum.getImages().add(index, imagePath);
                    adapter.notifyItemInserted(index);
                }
                llSelectedImages.removeView((View) v.getParent());
                selectedImages.remove(imagePath);
                refreshConfirmText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.android_base_imgIcon) {
            String path = v.getTag().toString();
            Intent intent = new Intent(this, ImagePagerActivity.class);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, selectedImages.toArray(new String[]{}));
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_FIRST, path);
            startActivity(intent);
        } else if (v.getId() == R.id.android_base_tvConfirm) {
            Intent data = new Intent();
            data.putExtra(RESULT_IMAGE_PATHS, selectedImages.toArray(new String[]{}));
            setResult(RESULT_OK, data);
            if (position == -1) {
            } else {
                sendBroadcast(new Intent("getImgs").putExtra("position", position).putExtra(RESULT_IMAGE_PATHS, selectedImages.toArray(new String[]{})));
            }
            SelectImageActivity.this.finish();
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        ArrayList<TImage> images = result.getImages();
        if (images.size() > 0) {
            cameraSavePath = images.get(0).getOriginalPath();
            selectImage("file://" + cameraSavePath, -1);
        }
    }

    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media.DISPLAY_NAME, // 显示的名
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.LONGITUDE, // 经度
            MediaStore.Images.Media._ID, // id
            MediaStore.Images.Media.BUCKET_ID, // dir id 目录
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // dir name 目录名字
            MediaStore.Images.Media.DATE_MODIFIED,//最后一次修改时间
            MediaStore.Images.Media.DATE_ADDED//最后一次修改时间
    };

    DisplayImageOptions image_display_options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.android_base_img_default)
            .showImageForEmptyUri(R.drawable.android_base_img_default)
            .showImageOnFail(R.drawable.android_base_img_default)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .delayBeforeLoading(100)
            .displayer(new FadeInBitmapDisplayer(500))
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();

    /**
     * 获得相册列表
     */
    public void initAlbums() {
        Map<String, AlbumEntity> albums = new LinkedHashMap<String, AlbumEntity>();
        Cursor cursor = MediaStore.Images.Media.query(getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES, null, " date_modified desc");


        AlbumEntity albumTop100 = new AlbumEntity();
        albumTop100.setAlbumName("最近图片");
        albumTop100.setAlbumId(MD5Encrypt.MD5(System.currentTimeMillis() + ""));
        albums.put("top100", albumTop100);
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            String dir_id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));
            String dir = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
            if (!albums.containsKey(dir_id)) {
                AlbumEntity album = new AlbumEntity();
                album.setAlbumName(dir);
                album.setAlbumId(dir_id);
                albums.put(dir_id, album);
            }
            AlbumEntity album = albums.get(dir_id);

            if (Pattern.matches(".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$", path)) {
                album.getImages().add("file://" + path);
                if (albumTop100.getImages().size() < 100) {
                    albumTop100.getImages().add("file://" + path);
                }
            }
        }

        currAlbum = albumTop100;
        adapter.notifyDataSetChanged();
        tvCurrAlbumName.setText(currAlbum.getAlbumName());

        cursor.close();

        for (String key : albums.keySet()) {
            AlbumEntity album = albums.get(key);

            View view = LayoutInflater.from(context).inflate(R.layout.android_base_item_album, null);
            TextView tvAlbumName = (TextView) view.findViewById(R.id.android_base_tvAlbumName);
            tvAlbumName.setText(album.getAlbumName());

            TextView tvAlbumCount = (TextView) view.findViewById(R.id.android_base_tvAlbumCount);
            tvAlbumCount.setText(album.getImages().size() + "张");

            final ImageView imgAlbumIcon = (ImageView) view.findViewById(R.id.android_base_imgAlbum);
            String imgPath = album.getImages().get(0);
            // int degree=AppUtils.getImageDegree(imgPath);
            ImageSize targetSize = new ImageSize(DensityUtils.dip2px(context, 40), DensityUtils.dip2px(context, 40));
            ImageLoader.getInstance().displayImage(imgPath, imgAlbumIcon, targetSize);


            view.findViewById(R.id.android_base_llAlbumItem).setOnClickListener(this);
            view.findViewById(R.id.android_base_llAlbumItem).setTag(album);

            llAlbums.addView(view);
        }


    }


    /**
     * 显示或隐藏相册列表
     */
    public void showOrHideAlbumPanel() {
        TranslateAnimation animation = null;
        AlphaAnimation alphaAnimation = null;
        if (scrollViewAlbum.getVisibility() == View.VISIBLE) {
            alphaAnimation = new AlphaAnimation(1, 0);
            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, -1f);
            animation.setAnimationListener(new SelfAnimationListener() {
                @Override
                public void onAnimationEnd(Animation arg0) {
                    super.onAnimationEnd(arg0);
                    scrollViewAlbum.setVisibility(View.GONE);
                    findViewById(R.id.android_base_frameLayoutAlbum).setVisibility(View.GONE);
                }
            });

        } else {
            findViewById(R.id.android_base_frameLayoutAlbum).setVisibility(View.VISIBLE);
            alphaAnimation = new AlphaAnimation(0, 1);
            scrollViewAlbum.setVisibility(View.VISIBLE);
            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, -1f,
                    Animation.RELATIVE_TO_SELF, 0);
        }
        alphaAnimation.setDuration(300);
        alphaAnimation.setFillAfter(true);
        animation.setDuration(300);
        animation.setFillAfter(true);
        findViewById(R.id.android_base_viewback).startAnimation(alphaAnimation);
        scrollViewAlbum.startAnimation(animation);
    }


    @SuppressWarnings("serial")
    public class AlbumEntity implements Serializable {

        private String albumId;
        private String albumName;
        private List<String> images;


        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getAlbumName() {
            return albumName;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public List<String> getImages() {
            if (images == null) {
                images = new ArrayList<String>();
            }
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

    }


    public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

        @Override
        public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.android_base_view_item_album_photo, parent, false);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (DensityUtils.getWidthInPx(context) / 3)));
            return new AlbumHolder(view);
        }

        @Override
        public void onBindViewHolder(AlbumHolder holder, int position) {
            String imagePath = currAlbum.getImages().get(position);
            ImageSize targetSize = new ImageSize(DensityUtils.dip2px(context, 120), DensityUtils.dip2px(context, 120));
            ImageViewAware imageView = new ImageViewAware(holder.imageView);
            ImageLoader.getInstance().displayImage(imagePath, imageView, image_display_options, targetSize, null, null);
        }

        @Override
        public int getItemCount() {
            return currAlbum.getImages().size();
        }
    }


    public class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;

        public AlbumHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.android_base_imagePhoto);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (DensityUtils.getWidthInPx(context) / 3)));
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() >= 0) {
                String imagePath = currAlbum.getImages().get(getAdapterPosition());
                selectImage(imagePath, getAdapterPosition());
            }
        }
    }


    public void selectImage(String imagePath, int index) {
        if (maxSelectedCount > 0 && llSelectedImages.getChildCount() >= maxSelectedCount) {
            Toast.makeText(context, "已超出图片选择上限！", Toast.LENGTH_SHORT).show();
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.android_base_view_selected_image, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.android_base_imgIcon);
        ImageSize targetSize = new ImageSize(DensityUtils.dip2px(context, 50), DensityUtils.dip2px(context, 50));
        ImageViewAware imageViewAware = new ImageViewAware(imageView);
        ImageLoader.getInstance().displayImage(imagePath, imageViewAware, image_display_options, targetSize, null, null);
        imageView.setOnClickListener(this);
        imageView.setTag(imagePath);


        try {
            JSONObject json = new JSONObject();
            json.put("index", index);
            json.put("imagePath", imagePath);
            json.put("albumId", currAlbum.getAlbumId());
            view.findViewById(R.id.android_base_sllClose).setOnClickListener(this);
            view.findViewById(R.id.android_base_sllClose).setTag(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        selectedImages.add(imagePath);

        llSelectedImages.addView(view);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hScrollView.fullScroll(ScrollView.FOCUS_RIGHT);
            }
        }, 300);
        if (index >= 0) {
            currAlbum.getImages().remove(index);
            adapter.notifyItemRemoved(index);
        }

        refreshConfirmText();
    }

    public class AlbumItemDecoration extends RecyclerView.ItemDecoration {
        int space = DensityUtils.dip2px(SelectImageActivity.this, 1);

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.top = space;
            outRect.bottom = space;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 3) {
//                selectImage("file://" + cameraSavePath, -1);
//            }
//        }
    }
}
