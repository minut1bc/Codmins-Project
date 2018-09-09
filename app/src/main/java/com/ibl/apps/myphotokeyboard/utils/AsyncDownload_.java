package com.ibl.apps.myphotokeyboard.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.ibl.apps.myphotokeyboard.R;
import com.ibl.apps.myphotokeyboard.model.FontsPaid;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

public class AsyncDownload_ extends AsyncTask<String, String, String> {
    private FontsPaid fontsPaid;
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


    //    TaskCompletedCallback taskCompletedCallback;
    private String saveFile;
    private Bitmap imageBitmap;

    public AsyncDownload_(Context context, FontsPaid fontsPaid) {
        GlobalClass.printLog("AAA", "-----------" + "call the AsyncDownload()");
        this.context = context;
        this.fontsPaid = fontsPaid;
    }

//    public void setTaskCompletedCallback(TaskCompletedCallback taskCompletedCallback) {
//        this.taskCompletedCallback = taskCompletedCallback;
//    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... URL) {
        try {
            java.net.URL url = new URL(fontsPaid.getFont_url());
            URLConnection conection = url.openConnection();
            conection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = conection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

//            // Output stream
//            GlobalClass.printLog("get aseett1", "======" + String.valueOf(context.getAssets()));
//
//            File App_Folder = new File(GlobalClass.AppFolder);
//            if (!App_Folder.exists())
//                App_Folder.mkdir();
//
//            OutputStream output = new FileOutputStream(new File(context.getExternalFilesDir(null), "keyBoard"));
//            GlobalClass.printLog("App_Folder", "===path===" + App_Folder.getAbsolutePath());


            // Find the SD Card path
            File filepath = Environment.getExternalStorageDirectory();
            // Create a new folder in SD Card
            File photoDir = new File(filepath.getAbsolutePath() + "/" + context.getString(R.string.app_name) + "/");
            if (!photoDir.exists())
                photoDir.mkdirs();


            Random randomNumber = new Random(System.currentTimeMillis());
            int randomNoOfImage = (1 + randomNumber.nextInt(2)) * 99999 + randomNumber.nextInt(99999);

            String imageName = fontsPaid.getTitle() + ".ttf";


            GlobalClass.printLog("Finalpath", "------------" + filepath.getAbsolutePath() + "/" + context.getString(R.string.app_name) + "/" + imageName);

            // Create a name for the saved image
            File imageFile = new File(photoDir, imageName);
            if (imageFile.exists()) imageFile.delete();
            // Show a toast message on successful save
            try {
                FileOutputStream output = new FileOutputStream(imageFile);
                // Compress into png format image from 0% - 100%
//                SelectedImageActivity.bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);

                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    output.write(bytes, 0, read);
                }

                output.flush();
                output.close();
            } catch (Exception e) {
                Log.e("Saving image ", "error -- " + e.getMessage());
            }
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

//        GlobalClass.printLog("get aseett1","======"+String.valueOf(context.getAssets()));
//
//        AssetManager assetManager = context.getAssets();
//        String[] files = null;
//        try {
//            files = assetManager.list("Files");
//        } catch (IOException e) {
//            Log.e("tag====", e.getMessage());
//        }
//        GlobalClass.printLog("files","==size===="+files.length);
//
//        for(String filename : files) {
//            GlobalClass.printLog("File name =======> ","------"+filename);
////            InputStream in = null;
////            OutputStream out = null;
////            try {
////                in = assetManager.open("Files/"+filename);   // if files resides inside the "Files" directory itself
////                out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() +"/" + filename);
////                copyFile(in, out);
////                in.close();
////                in = null;
////                out.flush();
////                out.close();
////                out = null;
////            } catch(Exception e) {
////                Log.e("tag", e.getMessage());
////            }
//        }
        copyAssets();
    }

    private void copyAssets() {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {

            Log.e("tag", "filename" + filename);

//            InputStream in = null;
//            OutputStream out = null;
//            try {
//                in = assetManager.open(filename);
//                File outFile = new File(context.getExternalFilesDir(null), filename);
//                out = new FileOutputStream(outFile);
//                copyFile(in, out);
//            } catch (IOException e) {
//                Log.e("tag", "Failed to copy asset file: " + filename, e);
//            } finally {
//                if (in != null) {
//                    try {
//                        in.close();
//                    } catch (IOException e) {
//                        // NOOP
//                    }
//                }
//                if (out != null) {
//                    try {
//                        out.close();
//                    } catch (IOException e) {
//                        // NOOP
//                    }
//                }
//            }
        }

        assert files != null;
        GlobalClass.printLog("files", "==size====" + files.length);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
