package com.jonkoester.pepperleg;

import android.view.View;

public class PepperLegDataModel {

    private View highLightedView;
    private String popupTitle;
    private String popupDescription;
    private Direction popupDirection;
    private XAlignment popupXAlignment;
    private YAlignment popupYAlignment;

    public enum Direction {
        TOP, RIGHT, BOTTOM, LEFT
    }

    public enum XAlignment {
        LEFT, RIGHT, CENTER
    }

    public enum YAlignment {
        TOP, BOTTOM, CENTER
    }

    public PepperLegDataModel(View highLightedView, String popupTitle, String popupDescription, Direction popupDirection) {
        this(highLightedView, popupTitle, popupDescription, popupDirection, null, null);
    }

    public PepperLegDataModel(View highLightedView, String popupTitle, String popupDescription, Direction popupDirection, XAlignment xAlignment, YAlignment yAlignment) {
        super();

        this.highLightedView = highLightedView;
        this.popupTitle = popupTitle;
        this.popupDescription = popupDescription;
        this.popupDirection = popupDirection;
        this.popupXAlignment = xAlignment;
        this.popupYAlignment = yAlignment;
    }

    public View getHighLightedView() {
        return highLightedView;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public String getPopupDescription() {
        return popupDescription;
    }

    public Direction getPopupDirection() {
        return popupDirection;
    }

    public XAlignment getPopupXAlignment() {
        return popupXAlignment;
    }

    public YAlignment getPopupYAlignment() {
        return popupYAlignment;
    }
}
