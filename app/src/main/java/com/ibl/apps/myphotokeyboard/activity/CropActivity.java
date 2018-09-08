package com.ibl.apps.myphotokeyboard.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.edmodo.cropper.CropImageView;
import com.ibl.apps.myphotokeyboard.R;

import java.io.File;

public class CropActivity extends AppCompatActivity implements View.OnClickListener {

    Bitmap imageBitmap;
    private CropImageView ivCropImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        if (MainActivity.mFileTemp == null) {
            if (CreateKeyboardActivity.mFileTemp != null) {
                MainActivity.mFileTemp = CreateKeyboardActivity.mFileTemp;
            } else {
                String TEMP_PHOTO_FILE_NAME = MainActivity.TEMP_PHOTO_FILE_NAME;
                if ("mounted".equals(Environment.getExternalStorageState())) {
                    MainActivity.mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
                } else {
                    MainActivity.mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
                }
            }
        }
        this.imageBitmap = BitmapFactory.decodeFile(MainActivity.mFileTemp.getAbsolutePath());
        setContent();
    }

    private void setContent() {
        ImageView ivBack = findViewById(R.id.ivBack);
        ImageView ivDone = findViewById(R.id.ivDone);
        ivCropImage = findViewById(R.id.ivCropImage);
        this.ivCropImage.setFixedAspectRatio(true);
        this.ivCropImage.setAspectRatio(10, 6);

        if (this.imageBitmap != null) {
            this.ivCropImage.setImageBitmap(this.imageBitmap);
        } else {
            finish();
        }

        //set listener
        ivBack.setOnClickListener(this);
        ivDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;

            case R.id.ivDone:
                Bitmap cBitmap;
                try {
                    cBitmap = this.ivCropImage.getCroppedImage();
                    if (cBitmap != null) {
                        CropActivity.this.finish();
                        CreateKeyboardActivity.getInstance().setKeyboardBackground(cBitmap);
                        return;
                    }
                } catch (Exception ignored) {
                }
                break;
        }
    }

    protected void onDestroy() {
        if (this.imageBitmap != null) {
            this.imageBitmap.recycle();
            this.imageBitmap = null;
            System.gc();
        }
        super.onDestroy();
    }
}

