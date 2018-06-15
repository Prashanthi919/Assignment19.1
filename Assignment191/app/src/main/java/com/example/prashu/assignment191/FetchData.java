package com.example.prashu.assignment191;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Prashu on 10-06-2018.
 */

public class FetchData extends AsyncTask<Void,Void,String> {

    private String mUrl;
    private Context mContext;
    private DataTransferInterface mDataTransferInterface;

    // constructor of the FetchData class.
    public FetchData(Context context, String url, DataTransferInterface dataTransferInterface ){
        // setting internal variables of the class as the passed variables.
        mContext = context;
        mUrl = url;
        mDataTransferInterface = dataTransferInterface;
    }

    @Override
    protected String doInBackground(Void... voids) {
        // creating an object of the OkHttpClient.
        OkHttpClient okHttpClient = new OkHttpClient();
        // if the connection is unable to establish then wait for 2 minutes for it to connect and then timeout and end process.
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        // if reading of the URL is unable to establish then wait for 2 minutes for it to happen and then timeout and end process.
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        // getting the request for the JSON URL.
        Request request = new Request.Builder().url(mUrl).build();
        String responseData = null;

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                // response contains the protocol of the URL
                responseData = response.body().string();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        mDataTransferInterface.interfaceData(response);
    }
}
