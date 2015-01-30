# PhotoCrop
A helper to use system gallery or camera take Photo
used [code][2] 
fix some problem

 - can not save crop pitcure
 - can not picker some photo from gallery
 - some api review

#How to use
 1. **new helper and set CropParam**
    ```java
    CropHelper cropHelper = new CropHelper(this);
    cropHelper.setCropParams(new CropParams());
    ```
    
 2. **insert handle**
    ```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropHelper.handleResult(requestCode,resultCode,data);
    }
    ```
 3. **handle result**
    ```java
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
    ```

#Thanks for 
1. [Android大图片裁剪终极解决方案（上：原理分析）][1]
2. [Code][2]


  [1]: http://ryanhoo.github.io/blog/2014/05/26/the-ultimate-approach-to-crop-photos-on-android-1/
  [2]: https://github.com/ryanhoo/PhotoCropper
