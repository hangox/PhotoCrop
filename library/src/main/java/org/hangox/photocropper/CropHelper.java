package org.hangox.photocropper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created with Android Studio.
 * User: hangox
 * Date: 1/30/15
 * Time: 11:08 AM
 * Desc: CropHelper
 */
public class CropHelper {

    public static final String TAG = "CropHelper";
    private Activity mActivity;
    private CropParams mCropParams;
    private CropListener mCropListener;

    /**
     * request code of Activities or Fragments
     * You will have to change the values of the request codes below if they conflict with your own.
     */
    public static final int REQUEST_CROP = 127;
    public static final int REQUEST_CAMERA = 128;
    public static final int REQUEST_GALLERY = 129;


    public CropHelper(Activity ac) {
        this.mActivity = ac;
    }

    public void setCropListener(CropListener listener) {
        mCropListener = listener;
    }

    public void setCropParams(CropParams params) {
        mCropParams = params;
    }

    public CropParams getCropParams() {
        return mCropParams;
    }

    public CropListener getCropListener() {
        return mCropListener;
    }


    public void handleResult(int requestCode, int resultCode, Intent data) {
        if (mCropListener == null) return;

        if (resultCode == Activity.RESULT_CANCELED) {
            mCropListener.onCropCancel();
        } else if (resultCode == Activity.RESULT_OK) {
            if (mCropParams == null) {
                mCropListener.onCropFailed("CropHandler's params MUST NOT be null!");
                return;
            }
            switch (requestCode) {
                case REQUEST_CROP:
                    Log.d(TAG, "Photo cropped!");
                    mCropListener.onPhotoCropped(mCropParams.cropUri);
                    break;
                case REQUEST_GALLERY:
                    mCropParams.oImageUri = data.getData();
                case REQUEST_CAMERA:
                    Intent intent = buildCropFromUriIntent();
                    if (mActivity != null) {
                        mActivity.startActivityForResult(intent, REQUEST_CROP);
                    } else {
                        mCropListener.onCropFailed("CropHandler's context MUST NOT be null!");
                    }
                    break;
            }
        }
    }


    public boolean clearCache() {
        File[] files = {new File(mCropParams.cropUri.getPath()),
                new File(mCropParams.oImageUri.getPath())};
        for (File file : files) {
            if (file.exists()) {
                boolean result = file.delete();
                if (result)
                    Log.i(TAG, "Cached crop file cleared.");
                else {
                    Log.e(TAG, "Failed to clear cached crop file.");
                    return false;
                }
            } else {
                Log.w(TAG, "Trying to clear cached crop file but it does not exist.");
            }
        }
        return true;
    }

    public void startCamera() {
        mActivity.startActivityForResult(buildCaptureIntent(), CropHelper.REQUEST_CAMERA);
    }

    public void startGallery() {
        mActivity.startActivityForResult(buildGalleyIntent(), CropHelper.REQUEST_GALLERY);
    }

    public Intent buildCropFromUriIntent() {
        return buildCropIntent("com.android.camera.action.CROP", mCropParams);
    }


    public Intent buildCaptureIntent() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, mCropParams.oImageUri);
    }

    public Intent buildGalleyIntent() {
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        return intent;
    }

    public Intent buildCropIntent(String action, CropParams params) {
        if (mCropParams.isAutoGenerateName()) params.buildCropUri();
        return new Intent(action, null)
                .setDataAndType(params.oImageUri, params.type)
                        //.setType(params.type)
                .putExtra("crop", params.crop)
                .putExtra("scale", params.scale)
                .putExtra("aspectX", params.aspectX)
                .putExtra("aspectY", params.aspectY)
                .putExtra("outputX", params.outputX)
                .putExtra("outputY", params.outputY)
                .putExtra("return-data", params.returnData)
                .putExtra("outputFormat", params.outputFormat)
                .putExtra("noFaceDetection", params.noFaceDetection)
                .putExtra("scaleUpIfNeeded", params.scaleUpIfNeeded)
                .putExtra(MediaStore.EXTRA_OUTPUT, params.cropUri);
    }

    public Bitmap getCropBitmap() {
        return decodeUriAsBitmap(mActivity, mCropParams.cropUri);
    }

    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        if (context == null || uri == null) return null;

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


}
