package org.hangox.photocropper;

import android.net.Uri;

/**
 * Created with Android Studio.
 * User: hangox
 * Date: 1/30/15
 * Time: 11:08 AM
 * Desc: CropListener
 * 裁剪监听类
 */
public interface CropListener {

    void onPhotoCropped(Uri uri);

    void onCropCancel();

    void onCropFailed(String message);

}
