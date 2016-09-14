package com.jonkoester.pepperleg;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PepperLegPopup extends LinearLayout {

    public PepperLegPopup(Context context) {
        super(context);
    }

    public PepperLegPopup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PepperLegPopup(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, null, null);
    }

    public PepperLegPopup(Context context, String titleText, String descriptionText) {
        super(context);

        inflate(context, R.layout.layout_pepperleg_popup, this);
        setWillNotDraw(false);

        TextView popupTitleTV = (TextView) findViewById(R.id.popup_titleTV);
        TextView popupDescriptionTV = (TextView) findViewById(R.id.popup_descriptionTV);

        popupTitleTV.setText(titleText);
        popupDescriptionTV.setText(descriptionText);
    }
}
