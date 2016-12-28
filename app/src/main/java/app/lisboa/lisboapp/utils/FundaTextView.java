package app.lisboa.lisboapp.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by anjali on 03/11/16.
 */

public class FundaTextView extends TextView {

    public FundaTextView(Context context) {
        super(context);
    }

    public FundaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    public FundaTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context);
    }

    private void setCustomFont(Context ctx) {
        Typeface curTypeFace = getTypeface();
        setTypeface(FundaFonts.get(ctx, Utils.getFontFamily(curTypeFace)));
    }

}
