package com.iwalnexus.tsn.socialapp.Firebase;

import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.iwalnexus.tsn.socialapp.R;

import static com.iwalnexus.tsn.socialapp.Activities.MainActivity.CHANNEL;
import static com.iwalnexus.tsn.socialapp.Activities.MainActivity.CHANNELID;

public class NotificationHelper {

    private Context context;
    private NotificationManagerCompat notificationManagerCompat;


    public void displayNotification(Context context, String titulo, String body, String id){

        this.context = context;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            createNotificationWithChannel(titulo, body, id);

        }else {
            createNotificationWithoutChannel(titulo, body, id);
        }
    }

    private void createNotificationWithoutChannel(String titulo, String body, String id) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                .setContentTitle(titulo)
                .setContentText(body)
                        .setSmallIcon(R.drawable.ic_account_circle);

        notificationManagerCompat = getManager();
        notificationManagerCompat.notify(Integer.parseInt(id), builder.build());
    }

    private NotificationManagerCompat getManager() {

        if(notificationManagerCompat == null){
            notificationManagerCompat = NotificationManagerCompat.from(context);
        }
        return notificationManagerCompat;
    }

    private void createNotificationWithChannel(String titulo, String body, String id) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, CHANNELID)
                            .setContentTitle(titulo)
                            .setContentText(body)
                    .setSmallIcon(R.drawable.ic_account_circle);

            notificationManagerCompat = getManager();
            notificationManagerCompat.notify(Integer.parseInt(id), builder.build());
        }
    }
}
