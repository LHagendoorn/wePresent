package wepresent.wepresent;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMMessageHandler extends IntentService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;
    private int sessionId;
    private String button1, button2, button3, type, question;

    public GCMMessageHandler() {
        super("GCMMessageHandler");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve data extras from push notification
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        // Keys in the data are shown as extras
        String title = extras.getString("title");
        String body = extras.getString("body");
        sessionId = Integer.parseInt(extras.getString("sessionId"));
        type = extras.getString("type");
        question = extras.getString("question");
        // Check if type is multiple choice
        if ( type.equals( "multiplechoice" ) ) {
            button1 = extras.getString("button1");
            button2 = extras.getString("button2");
            button3 = extras.getString("button3");
        }
        // Create notification or otherwise manage incoming push
        createNotification(56, R.drawable.notification_icon, title, body);
        // Notify receiver the intent is completed
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Creates notification based on title and body received
    private void createNotification(int nId, int iconRes, String title, String body) {
        //TODO: Go to quiz question stuff and get question enzo
        Intent intent = new Intent(this, LauncherHubThing.class);
        intent.putExtra("Tab", "quiz");
        intent.putExtra("Question", question);
        intent.putExtra("Type", type);
        intent.putExtra("SessionID", sessionId);
        // Check if multiple choice
        if ( type.equals("multiplechoice") ) {
            intent.putExtra("Button1", button1);
            intent.putExtra("Button2", button2);
            intent.putExtra("Button3", button3);
        }
        int requestID = (int) System.currentTimeMillis();
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.notification_icon);
        Notification mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(iconRes)
                .setContentTitle(title)
                .setContentText(body)
                .setLargeIcon(bm)
                .setContentIntent(pIntent).build();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(nId, mBuilder);
    }

}