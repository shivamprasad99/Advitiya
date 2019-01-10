package iitropar.advitiya;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

import java.util.Random;


public class NotificationService extends IntentService {


    public NotificationService() {
        super("NotificationService");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(Intent intent) {
        Random random = new Random();
        int NOTIFICATION_ID = random.nextInt(99999 - 1000) + 1000;

        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String time = intent.getStringExtra("time");
        System.out.print("time is" + time);
        Notification.Builder builder = new Notification.Builder(this);
        final RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.real_time_notification);
        contentView.setTextViewText(R.id.notificationTitle, title);
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
        notificationCompat.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        //notification.defaults |= Notification.DEFAULT_VIBRATE;
        //notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notificationCompat.ledARGB = 0xFFFFA500;
        notificationCompat.ledOnMS = 800;
        notificationCompat.ledOffMS = 1000;

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }


}