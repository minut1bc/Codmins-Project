package com.codminskeyboards.universekeyboard.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.model.FontsPaid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncDownload extends AsyncTask<String, String, String> {
    private FontsPaid fontsPaid;
    private final WeakReference<Activity> activityWeakReference;

    public AsyncDownload(Activity activity, FontsPaid fontsPaid) {
        GlobalClass.printLog("AAA", "-----------" + "call the AsyncDownload()");

        GlobalClass.printLog("start service", "----AsyncDownload---AsyncDownload constructor-------");

        this.activityWeakReference = new WeakReference<>(activity);
        this.fontsPaid = fontsPaid;
    }

    @Override
    protected void onPreExecute() {

        GlobalClass.printLog("start service", "----AsyncDownload---onPreExecute-------");
    }

    @Override
    protected String doInBackground(String... URL) {
        GlobalClass.printLog("start service", "----AsyncDownload---doInBackground-------");
        try {
            Activity activity = activityWeakReference.get();

            URL url = new URL(fontsPaid.getFont_url());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
            File newFile = new File(SDCardRoot.getAbsolutePath() + "/" + activity.getString(R.string.app_name) + "/");
            String filename = fontsPaid.getTitle() + ".ttf";
            Log.e("Exception-------", "-------------" + filename);
            File file = new File(newFile, filename);
            try {
                if (file.createNewFile()) {
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        GlobalClass.printLog("start service", "----AsyncDownload---onPostExecute-------");
    }

}