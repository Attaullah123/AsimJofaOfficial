package com.cresset.asimjofaofficial.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by attaullahkhizar on 9/14/17.
 */

public class CustomFont extends android.support.v7.widget.AppCompatTextView {

    public CustomFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomFont(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Myriad Pro Regular.ttf");
            setTypeface(tf);
        }
    }
}
