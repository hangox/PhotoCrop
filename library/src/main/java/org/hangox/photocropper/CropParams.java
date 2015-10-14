package org.hangox.photocropper;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
    private Uri mStartUri = Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment
            .DIRECTORY_PICTURES));
    private boolean isAutoGenerateName;

    public boolean isAutoGenerateName(){
        return isAutoGenerateName;
    }

    public CropParams() {
        this(true);
    }

    /**
     * 是否自动生成图片名字
     * @param isAutoGenerateName
     */
    public CropParams(boolean isAutoGenerateName){
        this.isAutoGenerateName = isAutoGenerateName;
        Uri startUri = mStartUri;
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

    public void buildCropUri(){
        cropUri = mStartUri.buildUpon().appendPath(generateName()).build();
    }

    public String generateName(){
        return "crop_" + mSimpleDateFormat.format(new Date(System.currentTimeMillis())) + ".jpg";
    }


}
