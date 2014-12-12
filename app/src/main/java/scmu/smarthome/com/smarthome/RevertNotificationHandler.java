package scmu.smarthome.com.smarthome;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

import scmu.smarthome.com.smarthome.util.GetHomeStatusTask;
import scmu.smarthome.com.smarthome.util.RevertNotificationActionTask;

public class RevertNotificationHandler extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        String revertMessage = extras.getString("revertMessage");
        int notificationId = extras.getInt("notificationId");

        System.out.println("revert message: " + revertMessage + " ! " + notificationId);

        // Dismiss notification
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(notificationId);

        // Run AsyncTask
        RevertNotificationActionTask mHomeStatusTask = new RevertNotificationActionTask(this);
        mHomeStatusTask.execute(revertMessage);

        finish();
    }
}
