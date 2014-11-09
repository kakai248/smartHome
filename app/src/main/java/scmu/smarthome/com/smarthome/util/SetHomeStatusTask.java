package scmu.smarthome.com.smarthome.util;

import android.os.AsyncTask;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> origin/master

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import scmu.smarthome.com.smarthome.entities.PostResponse;

public class SetHomeStatusTask extends AsyncTask<Object, Void, Object> {

    public interface OnTaskFinishedListener {

        public void onHomeStatusTaskFinished(Object result);
    }

    private OnTaskFinishedListener mListener;

    public SetHomeStatusTask(OnTaskFinishedListener listener) {
        mListener = listener;
    }

    @Override
    protected Object doInBackground(Object... params) {
        String selectedItem = (String) params[0];
        String device = (String) params[1];
        String type = (String) params[2];
        String status = (String) params[3];

        final String URL = "http://195.154.70.147:3000/" + selectedItem +
                                                     "/" + device +
                                                     "/" + type +
                                                     "/" + status;

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

        return gson.fromJson(response, PostResponse.class);
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        if (mListener != null) {
            mListener.onHomeStatusTaskFinished(result);
        }
    }
}
