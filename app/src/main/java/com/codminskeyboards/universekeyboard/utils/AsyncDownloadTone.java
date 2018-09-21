package com.codminskeyboards.universekeyboard.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.model.Free;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncDownloadTone extends AsyncTask<String, String, String> {
    private Free soundEffect;
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
    private String imageName;

    public AsyncDownloadTone(Context context, Free soundEffect) {
        GlobalClass.printLog("AAA", "-----------" + "call the AsyncDownload()");

        GlobalClass.printLog("start service", "----AsyncDownload---AsyncDownload constructor-------");

        this.context = context;
        this.soundEffect = soundEffect;
        GlobalClass globalClass = new GlobalClass(context);
    }

//    public void setTaskCompletedCallback(TaskCompletedCallback taskCompletedCallback) {
//        this.taskCompletedCallback = taskCompletedCallback;
//    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... URL) {
        GlobalClass.printLog("start service", "----AsyncDownload---doInBackground-------");
        String filepath;
        try {
            URL url = new URL(soundEffect.getSound_url());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
            File newFile = new File(SDCardRoot.getAbsolutePath() + "/" + context.getString(R.string.app_name) + "/");
            String filename = soundEffect.getTitle() + ".mp3";
//            Log.e("Exception-------", "-------------" + filename);
            File file = new File(newFile, filename);
            try {
                if (file.createNewFile()) {
                    file.createNewFile();
                    GlobalClass.printLog("createNewFile Exception", "--------newFile-----" + newFile);
                    GlobalClass.printLog("createNewFile Exception", "-------filename------" + filename);
                }
            } catch (Exception e) {
                file = new File(SDCardRoot, filename);
                GlobalClass.printLog("SDCardRoot Exception", "------SDCardRoot-------" + SDCardRoot);
                GlobalClass.printLog("SDCardRoot Exception", "--------getAbsolutePath-----" + file.getAbsolutePath());
                GlobalClass.printLog("SDCardRoot Exception", "-------filename------" + filename);
            }

            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = conn.getInputStream();
            int totalSize = conn.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
            }
            fileOutput.close();

            GlobalClass.printLog("final path", "-------file.getPath------" + file.getPath());

            if (downloadedSize == totalSize) filepath = file.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            filepath = null;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        GlobalClass.setPreferencesString(context, GlobalClass.SOUND_STATUS, "on");

        GlobalClass.printLog("start service", "----AsyncDownload---onPostExecute-------");

        // Toast.makeText(context, "Image Saved: " + filepath, Toast.LENGTH_SHORT).show();

//        beep(10);

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
//        copyAssets();
    }

    private void beep(int volume) {

        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        MediaPlayer player;

        try {
//            File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
//            File newFile = new File(SDCardRoot.getAbsolutePath() + "/" + context.getString(R.string.app_name) + "/");
            //final MediaPlayer mp = MediaPlayer.create(CreateKeyboardActivity.this, GlobalClass.tempSoundName);
            //    String filename = GlobalClass.tempSoundName + ".mp3";

            //  File file = new File(filename);

            //if (file.exists()) {
//            if (GlobalClass.tempSoundName!=0) {
            player = MediaPlayer.create(context, GlobalClass.tempSoundName);
            assert manager != null;
            manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
            player.start();


            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        } catch (Exception ignored) {
        }

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
