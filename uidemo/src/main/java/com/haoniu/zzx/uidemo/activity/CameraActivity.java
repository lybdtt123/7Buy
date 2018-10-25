package com.haoniu.zzx.uidemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.haoniu.zzx.uidemo.R;
import com.haoniu.zzx.uidemo.utils.BitmapUtil;
import com.haoniu.zzx.uidemo.utils.ScannerUtils;
import com.haoniu.zzx.uidemo.view.StickerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraActivity extends BaseActivity {
    protected String title = "";
    @BindView(R.id.mSurfaceView)
    SurfaceView mSurfaceView;
    @BindView(R.id.ivShow)
    ImageView ivShow;
    @BindView(R.id.mStickerView)
    StickerView mStickerView;
    @BindView(R.id.rlBg)
    RelativeLayout rlBg;
    @BindView(R.id.llBottom1)
    RelativeLayout llBottom1;
    @BindView(R.id.llBottom2)
    RelativeLayout llBottom2;

    private SurfaceHolder mSurfaceHolder;
    private SurfaceViewCallback surfaceViewCallback;
    private String imagUrl = "";
    private int mCurrentCamIndex = 0;
    private boolean previewing;
    private Camera mCamera;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_camera);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    @Override
    protected void initView() {
        setTitle(title);

        rlBg.setDrawingCacheEnabled(true);
        rlBg.buildDrawingCache();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.img_pic);
        mStickerView.setWaterMark(bitmap);
        surfaceViewCallback = new SurfaceViewCallback();
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(surfaceViewCallback);
        // surfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @OnClick({R.id.tvCancel, R.id.ivTakePhoto, R.id.tvReTake, R.id.tvSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                finish();
                break;
            case R.id.ivTakePhoto:
                if (previewing) {
//                    mCamera.takePicture(shutterCallback, rawPictureCallback,
//                            jpegPictureCallback);
                    mCamera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera arg1) {
                            takePhoto();
                            mCamera.cancelAutoFocus();
                        }
                    });
                }
                break;
            case R.id.tvReTake:
                ivShow.setVisibility(View.GONE);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.img_pic);
                mStickerView.setWaterMark(bitmap);
                llBottom1.setVisibility(View.VISIBLE);
                llBottom2.setVisibility(View.GONE);
                mCamera.startPreview();
                break;
            case R.id.tvSave:
                mStickerView.setAction(false);
                int viewWidth = rlBg.getMeasuredWidth();
                int viewHeight = rlBg.getMeasuredHeight();
                if (viewWidth > 0 && viewHeight > 0) {
                    Bitmap sBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
                    Canvas cvs = new Canvas(sBitmap);
                    if (sBitmap != null) {
                        synchronized (cvs) {
                            Canvas canvas = cvs;
                            canvas.setBitmap(sBitmap);
                            canvas.save();
                            canvas.drawColor(Color.WHITE); // 防止 View 上面有些区域空白导致最终 Bitmap 上有些区域变黑
                            rlBg.draw(canvas);
                            canvas.restore();
                            canvas.setBitmap(null);
                        }
                    }
//                    Bitmap sBitmap = QMUIDrawableHelper.createBitmapFromView(rlBg);
                    if (sBitmap != null && !sBitmap.isRecycled()) {
//                        saveImageToGallery(mContext, sBitmap);
                        ScannerUtils.saveImageToGallery(mContext, sBitmap, ScannerUtils.ScannerType.RECEIVER);
                    } else {
                        Log.d("TAG", "----------------==================------------------");
                    }
                }
//                Bitmap sBitmap = rlBg.getDrawingCache();
//                rlBg.setDrawingCacheEnabled(false);

                break;
        }
    }

    private void takePhoto() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera arg1) {
                // TODO Auto-generated method stub
                //camera.autoFocus(null);
//                shootSound();
//                int sourceWidth = mCamera.getParameters().getPictureSize().width;
                mCamera.stopPreview();
                BitmapFactory.Options options = new BitmapFactory.Options();
//                if (sourceWidth > 1000) {
//                    options.inSampleSize = 3;
//                }
                if (data != null) {
                    if (data.length / 1024 > 350
                            && data.length / 1024 < 450) {
                        options.inSampleSize = 3;
                    } else if (data.length / 1024 > 450) {
                        options.inSampleSize = 4;
                    }
                }
//                WeakReference<Bitmap> softBitmap = new WeakReference<Bitmap>(BitmapFactory.decodeByteArray(data, 0, data.length, options));
                Bitmap softBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                Bitmap bitmap = BitmapUtil.rotaingMyImageView(90, softBitmap);
                ivShow.setImageBitmap(bitmap);
                ivShow.setVisibility(View.VISIBLE);
                llBottom1.setVisibility(View.GONE);
                llBottom2.setVisibility(View.VISIBLE);
            }
        });
    }


    private final class SurfaceViewCallback implements
            android.view.SurfaceHolder.Callback {
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
                                   int arg3) {
            if (previewing) {
                mCamera.stopPreview();
                previewing = false;
            }


            try {
                mCamera.setPreviewDisplay(arg0);
                Camera.Parameters parameters = mCamera.getParameters();
////            parameters.setPictureSize((QMUIDisplayHelper.getScreenHeight(mContext) - QMUIDisplayHelper.dp2px(mContext, 150))
////                    , QMUIDisplayHelper.getScreenWidth(mContext));
                parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
                List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

                // 如果sizeList只有一个我们也没有必要做什么了，因为就他一个别无选择
                if (sizeList.size() > 0) {
                    parameters.setPictureSize(sizeList.get(0).width, sizeList.get(0).height);
                }
                parameters.setJpegQuality(100); // 设置照片质量
                mCamera.setParameters(parameters);
                mCamera.startPreview();
                previewing = true;
                setCameraDisplayOrientation(CameraActivity.this,
                        mCurrentCamIndex, mCamera);
            } catch (Exception e) {
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            mCamera = openFrontFacingCameraGingerbread();

        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            previewing = false;
        }
    }

    // 0表示后置，1表示前置
    private int cameraPosition = 0;

    private Camera openFrontFacingCameraGingerbread() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();// 得到摄像头的个数
//        if (cameraCount > 1) {
//            cameraPosition = 1;
//        } else {
//            cameraPosition = 0;
//        }
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);// 得到每一个摄像头的信息
            if (cameraPosition == 1) {
                // 现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    cam = Camera.open(i);
                    break;
                }
            } else {
                // 现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    cam = Camera.open(i);
                    break;
                }
            }
        }

        return cam;
    }


    // 根据横竖屏自动调节preview方向
    private static void setCameraDisplayOrientation(Activity activity,
                                                    int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();

        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);

    }

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
        }
    };

    Camera.PictureCallback rawPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {

        }
    };


//    Camera.PictureCallback jpegPictureCallback = new Camera.PictureCallback() {
//        @Override
//        public void onPictureTaken(byte[] arg0, Camera arg1) {
//            save(arg0);
//        }
//
//        /**
//         * 保存图片
//         *
//         * @param data
//         */
//        private void save(byte[] data) {
//            String fileName = Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_DCIM).toString()
//                    + File.separator + System.currentTimeMillis() + ".jpg";
//            File file = new File(fileName);
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdir();
//            }
//            try {
//                BufferedOutputStream bos = new BufferedOutputStream(
//                        new FileOutputStream(file));
//                bos.write(data);
//                mCamera.stopPreview();
//                previewing = false;
//                bos.flush();
//                bos.close();
//                scanFileToPhotoAlbum(file.getAbsolutePath());
//                configPhoto(file);
//                saveNewPhoto();
//            } catch (Exception e) {
//            }
//            mCamera.startPreview();
//            previewing = true;
//        }
//
//        /**
//         * 保持合成后的图片
//         */
//        private void saveNewPhoto() {
//            Bitmap bitmap = ivShow.getDrawingCache();
//            ivShow.setImageBitmap(bitmap);
//            saveImageToGallery(CameraActivity.this, bitmap);
//            ivShow.setVisibility(View.INVISIBLE);
//        }
//
//        /**
//         * 配置照片
//         *
//         * @param file
//         */
//        private void configPhoto(File file) {
//            Bitmap imagbitmap = null;
//            setCameraDisplayOrientation(CameraActivity.this, mCurrentCamIndex,
//                    mCamera);
//            if (cameraPosition == 1) {
//                imagbitmap = BitmapUtil.convert(BitmapUtil.rotaingImageView(
//                        270, BitmapFactory.decodeFile(file.getAbsolutePath())),
//                        0);
//            } else {
//                imagbitmap = BitmapUtil.decodeSampledBitmapFromResource(
//                        file.getAbsolutePath(), 200, 300);
//                imagbitmap = BitmapUtil.rotaingImageView(90, imagbitmap);
//            }
//            ivShow.setImageBitmap(imagbitmap);
//            ivShow.setVisibility(View.VISIBLE);
//        }
//
//    };

    public void scanFileToPhotoAlbum(String path) {
        MediaScannerConnection.scanFile(CameraActivity.this,
                new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    /**
     * 保存合成后的图片
     *
     * @param context
     * @param bmp
     */
    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String fileName = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).toString()
                + File.separator + System.currentTimeMillis() + ".jpg";
        File file = new File(fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse(file.getAbsolutePath())));
        imagUrl = file.getAbsolutePath();
        finish();
    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, int width, int height) {
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        for (Camera.Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                bestSize = s;
                largestArea = area;
            }
        }
        return bestSize;
    }
}
