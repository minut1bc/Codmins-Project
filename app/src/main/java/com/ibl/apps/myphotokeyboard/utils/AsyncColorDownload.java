package com.ibl.apps.myphotokeyboard.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import com.ibl.apps.myphotokeyboard.database.DatabaseHelper;
import com.ibl.apps.myphotokeyboard.model.Color;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AsyncColorDownload extends AsyncTask<String, String, String> {
    private Color color;
    private Context context;
    private FileInputStream fileInputStream;
    private BufferedInputStream bif;
    private InputStream inputStream;
    private Base64CODEC base64CODEC = new Base64CODEC();
    private ArrayList<String> imageBase64List = new ArrayList<>();
    private String[] selectionArgs;
    private Cursor subCatDataCursor;
    private String saveFileThumb;
    private InputStream inputStreamThumb;
    private Bitmap imageBitmapThumb;


    private String saveFile;
    private Bitmap imageBitmap;

    public AsyncColorDownload(Context context, Color color) {
        GlobalClass.printLog("AAAA", "-----------" + "call the AsyncDownload()");
        this.context = context;
        this.color = color;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... URL) {
        try {
            saveFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".png";

            String fileURL = color.getImage().replaceAll(" ", "%20");
//                    GlobalClass.printLog("image File->" + i + ":", "" + fileURL);
            imageBitmap = null;
            try {
                // Download Image from URL
                inputStream = new java.net.URL(fileURL).openStream();
                // Decode Bitmap
                imageBitmap = BitmapFactory.decodeStream(inputStream);
//                        imageBase64List.add(base64CODEC.convertToBase64(imageBitmap));
                color.setImageBase64(base64CODEC.convertToBase64(imageBitmap));

//                GlobalClass.printLog("frame base64CODEC", "---------" + base64CODEC.convertToBase64(imageBitmap));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.getMessage();

        } finally {
            try {
                if (imageBitmap != null) {
                    imageBitmap.recycle();
                    imageBitmap = null;
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (bif != null) {
                    bif.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            saveFileThumb = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".png";

            String fileURLThumb = color.getThumb_image().replaceAll(" ", "%20");
//                    GlobalClass.printLog("image File->" + i + ":", "" + fileURLThumb);
            imageBitmapThumb = null;
            try {
                // Download Image from URL
                inputStreamThumb = new java.net.URL(fileURLThumb).openStream();
                // Decode Bitmap
                imageBitmapThumb = BitmapFactory.decodeStream(inputStreamThumb);
//                        imageBase64List.add(base64CODEC.convertToBase64(imageBitmap));
                color.setThumbImageBase64(base64CODEC.convertToBase64(imageBitmapThumb));

//                GlobalClass.printLog("frame base64CODEC", "---------" + base64CODEC.convertToBase64(imageBitmapThumb));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (imageBitmapThumb != null) {
                    imageBitmapThumb.recycle();
                    imageBitmapThumb = null;
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (bif != null) {
                    bif.close();
                }
                if (inputStreamThumb != null) {
                    inputStreamThumb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
//        GlobalClass.printLog("---onPostExecute", "--------onPostExecute=====" + color.getThumbImageBase64());

        final String selection = DatabaseHelper.KEY_COLOR_WALLPAPER_ID + " LIKE ?";
        selectionArgs = new String[]{String.valueOf(color.getId())};

        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.KEY_COLOR_WALLPAPER_IMAGE_BITMAP, color.getImageBase64());
        values.put(DatabaseHelper.KEY_COLOR_WALLPAPER_THUMB_BITMAP, color.getThumbImageBase64());

        subCatDataCursor = dbHelper.getTableDataById(DatabaseHelper.TABLE_COLOR_WALLPAPER, DatabaseHelper.KEY_COLOR_WALLPAPER_ID
                , color.getId() + "");

        if (subCatDataCursor != null && subCatDataCursor.getCount() > 0) {
            GlobalClass.printLog("********", "--------update data=====" + color.getTitle());
            dbHelper.updateRowData(DatabaseHelper.TABLE_COLOR_WALLPAPER, values, selection, selectionArgs);
        }
    }
}
