package app.lisboa.lisboapp.utils;

import android.graphics.Typeface;

import app.lisboa.lisboapp.BuildConfig;

/**
 * Created by anjali on 03/11/16.
 */

public class Utils {

    public static Typeface getTypeface(int style) {
        return Typeface.create((String) null, style);
    }

    public static String getFontFamily(Typeface typeface) {
        String fontFamily;
        if (typeface == null) {
            return "proximanova-regular.otf";
        }
        if (typeface.getStyle() == Typeface.BOLD) {
            fontFamily = "proximanova-semibold.otf";
        } else {
            fontFamily = "proximanova-regular.otf";
        }
        return fontFamily;
    }

    public static String getEventTopic() {
        if (BuildConfig.DEBUG) {
            return "/topics/events-dev";
        }
        else {
            return "/topics/events";
        }
    }
}
