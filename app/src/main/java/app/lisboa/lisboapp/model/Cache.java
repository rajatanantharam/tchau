package app.lisboa.lisboapp.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by Rajat Anantharam on 03/11/16.
 */

public class Cache {

    private static final String EVENT_PREF = "_event_pref_";
    private static final String USER_ID_PREF = "_userid_pref_";

    public static void storeEvent(Context context, Event event) {

        Gson gson = new Gson();
        try {
            String json = gson.toJson(event);
            if (json != null) {

                SharedPreferences sharedPreferences = context.getSharedPreferences(EVENT_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(EVENT_PREF, json);
                editor.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Event getPreviousEvent(Context context){
        Event event = null;
        try {

            //convert the json string back to object

            SharedPreferences sharedPreferences = context.getSharedPreferences(EVENT_PREF, Context.MODE_PRIVATE);
            String json = sharedPreferences.getString(EVENT_PREF, null);
            if (json != null) {
                event = new Gson().fromJson(json, Event.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_ID_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID_PREF, null);
    }

    public static void storeUserId(Context context, String string) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_ID_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID_PREF, string);
        editor.apply();
    }
}
