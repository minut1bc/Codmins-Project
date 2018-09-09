package com.ibl.apps.myphotokeyboard.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class MyDownloadService_old extends Service {
    private Context context;
    private AsyncDownload asyncDownload;

    public MyDownloadService_old() {
        GlobalClass.printLog("start service","----MyDownloadService---construcor-------");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        getKeyboardData();
    }

//    private void getKeyboardData() {
//        GlobalClass.printLog("start service","----MyDownloadService---getKeyboardData-------");
//        ApiInterface apiService = APIClient.getClient();
//        Call<ResponseObject<ResponseData>> call = apiService.getKeyboardData();
//        call.enqueue(new Callback<ResponseObject<ResponseData>>() {
//            @Override
//            public void onResponse(Call<ResponseObject<ResponseData>> call, final Response<ResponseObject<ResponseData>> response) {
//                try {
//                    if (response.body() != null) {
//                        GlobalClass.printLog("----onResponse-----", "Size" + response.body().getResponse_data().getWallpaper().getPaid().getTextual().size());
//
//                        for (int i = 0; i < response.body().getResponse_data().getFonts().getPaid().size(); i++) {
//                            asyncDownload = new AsyncDownload(context, response.body().getResponse_data().getFonts().getPaid().get(i));
//                            asyncDownload.execute();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    GlobalClass.printLog("-------- e-------", "" + e.toString());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseObject<ResponseData>> call, Throwable t) {
//                GlobalClass.printLog("-------- t-------", "" + t.toString());
//                call.cancel();
//            }
//        });
//    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
