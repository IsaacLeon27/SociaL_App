package com.iwalnexus.tsn.socialapp.Firebase;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class MyFirebaseMsgService extends FirebaseMessagingService {


    private SharedPreferences pref;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map data = remoteMessage.getData();

        NotificationHelper nt = new NotificationHelper();

        try {
            nt.displayNotification(getApplicationContext(), URLDecoder.decode(data.get("title").toString(), "UTF-8"), URLDecoder.decode(data.get("body").toString(), "UTF-8"), data.get("notidicationId").toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        saveToken(token);
    }

    private void saveToken(String token) {

        pref = getSharedPreferences("SocialAppPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.apply();;
    }


}
