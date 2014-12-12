package scmu.smarthome.com.smarthome.util;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import scmu.smarthome.com.smarthome.entities.Division;

public class RevertNotificationActionTask extends AsyncTask<String, Void, Void> {

    private Context mContext;

    public RevertNotificationActionTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        String revertMessage = params[0];

        String ip = Settings.getIp(mContext);

        final String URL = "http://" + ip + "/revert/" + revertMessage;

        // make the HTTP request
        Request request = new Request.Builder()
                .url(URL)
                .build();

        try {
            new OkHttpClient().newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
