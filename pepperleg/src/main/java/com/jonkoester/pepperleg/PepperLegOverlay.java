package com.jonkoester.pepperleg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Queue;

public class PepperLegOverlay extends RelativeLayout {

    private RectF clipRect = new RectF();
    private Path clipPath = new Path();
    private Path highlightPath = new Path();
    private Paint highlightPaint = new Paint();
    private boolean removeExistingPath = false;
    private int[] screenPos = new int[2];
    private int[] screenOffset;
    private Queue<PepperLegDataModel> pepperLegDataModelQueue;
    private PepperLegDataModel pepperLegDataModel;
    private PepperLegPopup pepperLegPopup;

    private final static int CLIP_PATH_PADDING = 12;
    private final static int HIGHLIGHT_STROKE_WIDTH = 8;
    private final static int HELPER_SPACING = 16;
    private final static int CORNER_RADIUS = 12;

    public PepperLegOverlay(Context context) {
        super(context);
    }

    public PepperLegOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PepperLegOverlay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //region programmatic constructor for drawing the stuff

    public PepperLegOverlay(Context context, Queue<PepperLegDataModel> pepperLegDataModelQueue) {
        super(context);

        init(context, pepperLegDataModelQueue);
    }

    private void init(Context context, Queue<PepperLegDataModel> pepperLegDataModelQueue) {
        inflate(context, R.layout.layout_pepper_leg_overlay, this);
        setWillNotDraw(false);

        if (pepperLegDataModelQueue != null &&
                !pepperLegDataModelQueue.isEmpty()) {

            this.pepperLegDataModelQueue = pepperLegDataModelQueue;
            this.pepperLegDataModel = pepperLegDataModelQueue.poll();
        }

        initHighlightPaint();

        Button okGotItButton = (Button) findViewById(R.id.overlay_button);
        okGotItButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAStep();
            }
        });
    }

    //endregion

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initScreenPositionAndOffsets();
        unMaskView(canvas);
        createAndPositionHelper();
    }

    private void initScreenPositionAndOffsets() {
        if (pepperLegDataModel.getHighLightedView() != null) {
            pepperLegDataModel.getHighLightedView().getLocationOnScreen(screenPos);
        }

        if (screenOffset == null) {
            screenOffset = new int[2];
            getLocationOnScreen(screenOffset);
        }
    }

    private void initHighlightPaint() {
        highlightPaint.setAntiAlias(true);
        highlightPaint.setColor(getContext().getResources().getColor(R.color.material_lime_A700));
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setStrokeWidth(HIGHLIGHT_STROKE_WIDTH);
    }

    private void unMaskView(Canvas canvas) {
        if (pepperLegDataModel.getHighLightedView() != null) {
            if (removeExistingPath) {
                canvas.clipPath(clipPath, Region.Op.UNION);
                clipPath.reset();
                highlightPath.reset();
                removeExistingPath = false;
            }

            clipRect.set(getXOffset() - CLIP_PATH_PADDING,
                    getYOffset() - CLIP_PATH_PADDING,
                    getXOffset() + pepperLegDataModel.getHighLightedView().getWidth() + CLIP_PATH_PADDING,
                    getYOffset() + pepperLegDataModel.getHighLightedView().getHeight() + CLIP_PATH_PADDING);
            clipPath.addRoundRect(clipRect, CORNER_RADIUS, CORNER_RADIUS, Path.Direction.CW);
            highlightPath.addRoundRect(clipRect, CORNER_RADIUS, CORNER_RADIUS, Path.Direction.CW);

            canvas.drawPath(highlightPath, highlightPaint);
            canvas.clipPath(clipPath, Region.Op.DIFFERENCE);
        }
    }

    private void createAndPositionHelper() {
        if (pepperLegPopup == null) {
            pepperLegPopup = new PepperLegPopup(getContext(), pepperLegDataModel.getPopupTitle(), pepperLegDataModel.getPopupDescription());
            pepperLegPopup.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            positionHelper();
            addView(pepperLegPopup);
        }
    }

    private void positionHelper() {
        float x = getXOffset();
        float y = getYOffset();

        switch (pepperLegDataModel.getPopupDirection()) {
            case TOP:
                y -= pepperLegPopup.getMeasuredHeight() + HELPER_SPACING;
                break;
            case BOTTOM:
                y += pepperLegDataModel.getHighLightedView().getHeight() + HELPER_SPACING;
                break;
            case LEFT:
                x -= pepperLegPopup.getMeasuredWidth() + HELPER_SPACING;
                break;
            case RIGHT:
                x += pepperLegDataModel.getHighLightedView().getWidth() + HELPER_SPACING;
                break;
            default:
                break;
        }

        if (pepperLegDataModel.getPopupXAlignment() != null) {
            switch (pepperLegDataModel.getPopupXAlignment()) {
                case LEFT:
                    x -= HELPER_SPACING;
                    break;
                case RIGHT:
                    x += (pepperLegDataModel.getHighLightedView().getWidth() - pepperLegPopup.getMeasuredWidth() + CLIP_PATH_PADDING) > 0 ? (pepperLegDataModel.getHighLightedView().getWidth() - pepperLegPopup.getMeasuredWidth() + CLIP_PATH_PADDING) : 0;
                    break;
                case CENTER:
                    x += (pepperLegDataModel.getHighLightedView().getWidth() / 2) - (pepperLegPopup.getMeasuredWidth() / 2);
                    break;
                default:
                    break;
            }
        }

        if (pepperLegDataModel.getPopupYAlignment() != null) {
            switch (pepperLegDataModel.getPopupYAlignment()) {
                case TOP:
                    y -= CLIP_PATH_PADDING;
                    break;
                case BOTTOM:
                    y += pepperLegDataModel.getHighLightedView().getHeight() - pepperLegPopup.getMeasuredHeight() + CLIP_PATH_PADDING;
                    break;
                case CENTER:
                    y += (pepperLegDataModel.getHighLightedView().getHeight() / 2) - (pepperLegPopup.getMeasuredHeight() / 2);
                    break;
                default:
                    break;
            }
        }

        pepperLegPopup.setX(x);
        pepperLegPopup.setY(y);
    }

    /**
     * @return the x-coordinate to account for any views on the screen not included in the window
     */
    private float getXOffset() {
        float xOffset = 0;
        if (screenOffset != null) {
            xOffset = screenPos[0] - screenOffset[0];
        }

        return xOffset;
    }

    /**
     * @return the y-coordinate to account for any views on the screen not included in the window
     */
    private float getYOffset() {
        float yOffset = 0;
        if (screenOffset != null) {
            yOffset = screenPos[1] - screenOffset[1];
        }

        return yOffset;
    }

    private void takeAStep() {
        if (pepperLegDataModelQueue != null
                && !pepperLegDataModelQueue.isEmpty()) {

            // We remove and null out the helper to make sure it does not hang around in memory
            removeView(pepperLegPopup);
            pepperLegPopup = null;

            removeExistingPath = true;
            pepperLegDataModel = pepperLegDataModelQueue.poll();
            requestLayout();
        } else {
            ((ViewGroup) getParent()).removeView(this);
        }
    }
}
