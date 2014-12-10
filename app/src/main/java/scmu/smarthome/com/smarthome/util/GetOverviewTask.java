package scmu.smarthome.com.smarthome.util;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import scmu.smarthome.com.smarthome.entities.Division;

public class GetOverviewTask extends AsyncTask<Object, Void, Division[]> {

    public interface OnTaskFinishedListener {

        public void onOverviewTaskFinished(Division[] result);
    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetOverviewTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Division[] doInBackground(Object... params) {
        String ip = Settings.getIp(mContext);

        final String URL = "http://" + ip;

        // make the HTTP request
        Request request = new Request.Builder()
                .url(URL)
                .build();

        String response;
        try {
            response = new OkHttpClient().newCall(request).execute().body().string();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.fromJson(response, Division[].class);
    }

    @Override
    protected void onPostExecute(Division[] result) {
        super.onPostExecute(result);

        if (mListener != null) {
            mListener.onOverviewTaskFinished(result);
        }
    }
}
