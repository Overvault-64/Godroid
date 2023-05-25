package org.overvault.godroid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import org.godotengine.godot.Dictionary;
import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.UsedByGodot;

import java.util.Calendar;
import java.util.Objects;

public class Notifications extends GodotPlugin {

    private final String TAG = Notifications.class.getName();
    private Dictionary notificationData = new Dictionary();
    private String action = null;
    private String uri = null;
    private Boolean intentWasChecked;

    public Notifications(Godot godot)
    {
        super(godot);
        intentWasChecked = false;
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "GodroidNotifications";
    }

    @Override
    public View onMainCreate(Activity activity) {
        return null;
    }


    @UsedByGodot
    public void showNotification(String message, String title, int interval, int tag) {
        if(interval <= 0) return;
        Log.d(TAG, "showLocalNotification: "+message+", "+ interval +", "+ tag);
        PendingIntent sender = getPendingIntent(message, title, tag);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, interval);

        getActivity();
        AlarmManager am = (AlarmManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ALARM_SERVICE);
        am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }

    @UsedByGodot
    public void showRepeatingNotification(String message, String title, int interval, int tag, int repeat_duration) {
        if(interval <= 0) return;
        Log.d(TAG, "showRepeatingNotification: "+message+", "+ interval +", "+ tag +" Repeat after: "+ repeat_duration);
        PendingIntent sender = getPendingIntent(message, title, tag);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, interval);

        getActivity();
        AlarmManager am = (AlarmManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeat_duration* 1000L, sender);
    }

    @UsedByGodot
    public void cancelNotification(int tag) {
        getActivity();
        AlarmManager am = (AlarmManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = getPendingIntent("", "", tag);
        am.cancel(sender);
    }


    // Internal methods
    private PendingIntent getPendingIntent(String message, String title, int tag) {
        Intent i = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), NotificationsReceiver.class);
        i.putExtra("notification_id", tag);
        i.putExtra("message", message);
        i.putExtra("title", title);
        return PendingIntent.getBroadcast(getActivity(), tag, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    private void checkIntent() {
        Log.w(TAG, "I'm going to check application intent");
        Intent intent = Godot.getCurrentIntent();
        if(intent == null) {
            Log.d(TAG, "No intent in app activity");
            return;
        }
        Log.w(TAG, "The intent isn't null, so check it closely.");
        if(intent.getExtras() != null) {
            Bundle extras = Godot.getCurrentIntent().getExtras();
            Log.d(TAG, "Extras:" + extras.toString());
            notificationData = new Dictionary();
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                try {
                    notificationData.put(key, value);
                    Log.w(TAG, "Get new value " + value.toString() + " for key " + key);
                } catch(Exception e) {
                    Log.d(TAG, "Conversion error: " + e);
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "Extras content: " + notificationData.toString());
        } else {
            Log.d(TAG, "No extra bundle in app activity!");
        }
        if(intent.getAction() != null) {
            Log.w(TAG, "Get deeplink action from intent");
            action = intent.getAction();
        }
        if(intent.getData() != null) {
            Log.w(TAG, "Get uri from intent");
            uri = intent.getData().toString();
        }
        intentWasChecked = true;
    }

    @UsedByGodot
    public Dictionary getNotificationData() {
        if(!intentWasChecked) checkIntent();
        return notificationData;
    }

    @UsedByGodot
    public String getDeeplinkAction() {
        if(!intentWasChecked) checkIntent();
        return action;
    }

    @UsedByGodot
    public String getDeeplinkUri() {
        if(!intentWasChecked) checkIntent();
        return uri;
    }
}
