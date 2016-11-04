package app.lisboa.lisboapp.utils;

import android.graphics.Typeface;

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
}
