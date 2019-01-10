package iitropar.advitiya;

import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * Created by zeitgeistapp on 11/1/18.
 */

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.MeasureUnit;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class FirebaseMessage extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains Results data payload.
/*        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (// Check if data needs to be processed by long running job // true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                // scheduleJob();
            } else {
                // Handle message within 10 seconds
                // handleNow();
            }

        }
*/
        // Check if message contains Results notification payload.
        if (remoteMessage.getNotification() != null) {
            Random random = new Random();
            int NOTIFICATION_ID = random.nextInt(99999 - 1000) + 1000;
            Log.d(TAG, "Message Notification Body: Not set");
            String time = getTime(remoteMessage.getSentTime());
            String description = remoteMessage.getNotification().getBody();

            String title="Advitiya'18";
            if(remoteMessage.getNotification().getTitle()!="")
                title = remoteMessage.getNotification().getTitle();
            Log.d(TAG, "Message Notification Body: " + description);

            Notification.Builder builder = new Notification.Builder(this);
            final RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.real_time_notification);
            contentView.setTextViewText(R.id.notificationTitle, title);
/*            contentView.setTextColor(R.id.notificationTitle,getResources().getColor(
                    R.color.black));*/
            contentView.setTextViewText(R.id.notification_description, description);
            contentView.setTextViewText(R.id.notificationCurrentTime,time);
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setContent(contentView);


            Intent notifyIntent = new Intent(this, SpaceActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //to be able to launch your activity from the notification
            builder.setContentIntent(pendingIntent);
            Notification notificationCompat = builder.build();
            builder.setAutoCancel(true);
            notificationCompat.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_INSISTENT | Notification.FLAG_SHOW_LIGHTS;

            //notification.defaults |= Notification.DEFAULT_VIBRATE;
            //notification.flags |= Notification.FLAG_SHOW_LIGHTS;
            notificationCompat.ledARGB = 0xFFFFA500;
            notificationCompat.ledOnMS = 800;
            notificationCompat.ledOffMS = 1000;

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

            managerCompat.notify(NOTIFICATION_ID, notificationCompat);



        }

        // Also if you intend on generating your own notifications as Results result of Results received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    public String getTime(long milliseconds){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat df2 = new SimpleDateFormat("hh:mm aaa");
        String formattedDate = df2.format(calendar.getTime());

        return formattedDate ;
    }
    // [END receive_message]

    /**
     * Schedule Results job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }
}

    /**
     * Create and show Results simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
