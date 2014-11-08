package scmu.smarthome.com.smarthome.util;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import scmu.smarthome.com.smarthome.entities.Division;

public class GetHomeStatusTask extends AsyncTask<Object, Void, Object> {

    public interface OnTaskFinishedListener {

        public void onHomeStatusTaskFinished(Object result);
    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetHomeStatusTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Object doInBackground(Object... params) {
        String selectedItem = (String) params[0];
        Boolean showDivisions = (Boolean) params[1];

        final String URL = "http://195.154.70.147:3000/" + selectedItem;

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

        if(showDivisions)
            return gson.fromJson(response, Division.class);
        else
            return gson.fromJson(response, Division[].class);
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        if (mListener != null) {
            mListener.onHomeStatusTaskFinished(result);
        }
    }
}
