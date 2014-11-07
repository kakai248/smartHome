package scmu.smarthome.com.smarthome.util;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import scmu.smarthome.com.smarthome.entities.Device;
import scmu.smarthome.com.smarthome.entities.Division;

public class GetHomeStatusTask extends AsyncTask<String, Void, Device> {

    public interface OnTaskFinishedListener {

        public void onHomeStatusTaskFinished(Device result);
    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetHomeStatusTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Device doInBackground(String... params) {
        String roomSelected = params[0];
        final String URL = "http://195.154.70.147:3000/" + roomSelected;

        System.out.println("room: " + roomSelected);

        // make the HTTP request
        Request request = new Request.Builder()
                .url(URL)
                .build();

        String response;
        try {
            response = new OkHttpClient().newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(response, Device.class);
    }

    @Override
    protected void onPostExecute(Device result) {
        super.onPostExecute(result);

        if (mListener != null) {
            mListener.onHomeStatusTaskFinished(result);
        }
    }
}
