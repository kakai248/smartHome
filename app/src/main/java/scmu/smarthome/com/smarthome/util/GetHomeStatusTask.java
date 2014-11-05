package scmu.smarthome.com.smarthome.util;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class GetHomeStatusTask extends AsyncTask<String, Void, String> {

    public interface OnTaskFinishedListener {

        public void onHomeStatusTaskFinished(String result);
    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetHomeStatusTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        final String URL = "http://195.154.70.147:3000/";

        String mode = params[0];
        String position = params[1];

        Request request = new Request.Builder()
                .url(URL)
                .build();

        try {
            Response response = new OkHttpClient().newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (mListener != null) {
            mListener.onHomeStatusTaskFinished(result);
        }
    }
}
