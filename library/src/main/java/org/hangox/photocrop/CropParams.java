package org.hangox.photocrop;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

/**
 * Created with Android Studio.
 * User: hangox
 * Date: 1/30/15
 * Time: 11:08 AM
 * Desc: CropParams
 */
public class CropParams {

    public static final String CROP_TYPE = "image/*";
    public static final String OUTPUT_FORMAT = Bitmap.CompressFormat.JPEG.toString();

    public static final int DEFAULT_ASPECT = 1;
    public static final int DEFAULT_OUTPUT = 300;

    /** 裁剪后图片地址**/
    public Uri cropUri;
    /** 原图地址**/
    public Uri oImageUri;

    public String type;
    public String outputFormat;
    public String crop;

    public boolean scale;
    public boolean returnData;
    public boolean noFaceDetection;
    public boolean scaleUpIfNeeded;

    public int aspectX;
    public int aspectY;

    public int outputX;
    public int outputY;

    public CropParams() {
        Uri startUri = Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_PICTURES));
        cropUri = startUri.buildUpon().appendPath("crop.jpg").build();
        oImageUri = startUri.buildUpon().appendPath("oImageUri.jpg").build();
        type = CROP_TYPE;
        outputFormat = OUTPUT_FORMAT;
        crop = "true";
        scale = true;
        returnData = false;
        noFaceDetection = true;
        scaleUpIfNeeded = true;
        aspectX = DEFAULT_ASPECT;
        aspectY = DEFAULT_ASPECT;
        outputX = DEFAULT_OUTPUT;
        outputY = DEFAULT_OUTPUT;
    }


}
