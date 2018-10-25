package com.haoniu.zzx.uidemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.haoniu.zzx.uidemo.R;
import com.haoniu.zzx.uidemo.utils.PhotoUtils;

import java.io.File;
import java.io.FileInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.PATCH;

public class PhotoActivity extends BaseActivity {

    protected String title = "";
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_photo);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }


    @Override
    protected void initView() {
        setTitle(title);
    }

    private File mCropFile;

    @OnClick({R.id.btCamera, R.id.btAlbum, R.id.btDel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btCamera:
                File mFile = PhotoUtils.getFile(mContext);
                if (mFile == null) {
                    return;
                }
                mCropFile = mFile;
                PhotoUtils.takePicture(PhotoActivity.this, Uri.fromFile(mFile), 310);
                break;
            case R.id.btAlbum:
                PhotoUtils.openPic(PhotoActivity.this, 311);
                break;
            case R.id.btDel:
                if (mCropFile != null && mCropFile.exists()) {
                    mCropFile.delete();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 310) {
                PhotoUtils.cropImage(PhotoActivity.this, Uri.fromFile(mCropFile), mCropFile, 1, 1, 300, 300, 312);
            } else if (requestCode == 311) {
                Uri imageUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                mCropFile = PhotoUtils.getFile(mContext);
                if (mCropFile == null) {
                    return;
                }
                PhotoUtils.cropImage(PhotoActivity.this, imageUri, mCropFile, 1, 1, 300, 300, 312);
            } else if (requestCode == 312) {
                ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mCropFile.getAbsolutePath()));
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param inputUri
     */
//    public void startPhotoZoom(Uri inputUri) {
//
//        if (inputUri == null) {
//            Log.d("TAG", "----The uri is not exist.");
//            return;
//        }
//
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        //sdk>=24
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Uri outPutUri = Uri.fromFile(mFile);
//            intent.setDataAndType(inputUri, "image/*");
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
//            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        } else {
//            Uri outPutUri = Uri.fromFile(mFile);
//            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                String url = PhotoUtils.getPath(this, inputUri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
//                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
//            } else {
//                intent.setDataAndType(inputUri, "image/*");
//            }
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
//        }
//
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 300);
//        intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", false);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
//        startActivityForResult(intent, 311);//这里就将裁剪后的图片的Uri返回了
//    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }
}
