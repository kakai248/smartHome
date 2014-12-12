package scmu.smarthome.com.smarthome;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import scmu.smarthome.com.smarthome.ui.NavDrawerActivity;
import scmu.smarthome.com.smarthome.util.Settings;

public class GcmIntentService extends IntentService {

    private static int mNotificationId = 1;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        String revertMessage = extras.getString("revertMessage");
        String notificationMessage = extras.getString("message");

        if(Settings.isNotificationsEnabled(this))
            issueNotification(revertMessage, notificationMessage);

        // Release the wake lock provided by the GcmBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void issueNotification(String revertMessage, String notificationMessage) {
        // Sets an ID for the current notification
        mNotificationId++;

        //Define sound URI
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Define the Notification's Main Action
        PendingIntent mainPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        new Intent(this, NavDrawerActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // Define the Notification's Revert Action
        Intent revertIntent = new Intent(this, RevertNotificationHandler.class);
        revertIntent.putExtra("revertMessage", revertMessage);
        revertIntent.putExtra("notificationId", mNotificationId);

        PendingIntent revertPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        revertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("It's " + revertMessage + "!")
                        .setContentText(notificationMessage)
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setContentIntent(mainPendingIntent)
                        .addAction(android.R.drawable.ic_menu_revert, "Revert", revertPendingIntent);

        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
