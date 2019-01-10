package iitropar.advitiya;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String time = intent.getStringExtra("time");


        Intent intent1 = new Intent(context, NotificationService.class);
        intent1.putExtra("title",title);
        intent1.putExtra("description",description);
        intent1.putExtra("time",time);

        context.startService(intent1);
    }


}
