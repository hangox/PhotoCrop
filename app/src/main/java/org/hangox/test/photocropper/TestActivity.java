package org.hangox.test.photocropper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.hangox.photocrop.CropHelper;
import org.hangox.photocrop.CropListener;
import org.hangox.photocrop.CropParams;

/**
 * Created with Android Studio.
 * User: Hangox
 * Date: 1/30/15
 * Time: 11:44 AM
 * Desc: TestActivity
 */
public class TestActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "TestActivity";

    ImageView mImageView;

    CropParams mCropParams;// = new CropParams();
    CropHelper cropHelper = new CropHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        cropHelper.setCropParams(new CropParams());
        cropHelper.setCropListener(new CropListener() {
            @Override
            public void onPhotoCropped(Uri uri) {
                Log.d(TAG, "Crop Uri in path: " + uri.getPath());
                Toast.makeText(TestActivity.this, "Photo cropped!", Toast.LENGTH_LONG).show();
                mImageView.setImageBitmap(cropHelper.getCropBitmap());
            }

            @Override
            public void onCropCancel() {
                Toast.makeText(TestActivity.this,"cancel",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCropFailed(String message) {
                Toast.makeText(TestActivity.this,message+"",Toast.LENGTH_SHORT).show();
            }
        });
        mImageView = (ImageView) findViewById(R.id.image);
        findViewById(R.id.bt_capture).setOnClickListener(this);
        findViewById(R.id.bt_gallery).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_capture:
                cropHelper.startCamera();
                break;
            case R.id.bt_gallery:
                cropHelper.startGallery();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropHelper.handleResult(requestCode,resultCode,data);
    }

}
