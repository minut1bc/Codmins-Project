package com.codminskeyboards.universekeyboard.customkeyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import java.util.ArrayList;
import java.util.List;

public class CandidateView extends View {

    private static final int OUT_OF_BOUNDS = -1;

    private SoftKeyboard softKeyboard;
    private List<String> suggestions;
    private int selectedIndex;
    private int touchX = OUT_OF_BOUNDS;
    private Drawable selectionHighlight;
    private boolean typedWordValid;

    private Rect backgroundPadding;

    private static final int MAX_SUGGESTIONS = 32;
    private static final int SCROLL_PIXELS = 20;

    private int[] wordWidth = new int[MAX_SUGGESTIONS];
    private int[] wordX = new int[MAX_SUGGESTIONS];

    private static final int X_GAP = 10;

    private static final List<String> EMPTY_LIST = new ArrayList<>();

    private int colorNormal;
    private int colorRecommended;
    private int colorOther;
    private int verticalPadding;
    private Paint paint;
    private Rect padding;
    private boolean scrolled;
    private int targetScrollX;
    private int totalWidth;

    private GestureDetector gestureDetector;

    public CandidateView(Context context) {
        super(context);

        selectionHighlight = context.getResources().getDrawable(android.R.drawable.list_selector_background);

        selectionHighlight.setState(new int[]{
                android.R.attr.state_enabled,
                android.R.attr.state_focused,
                android.R.attr.state_window_focused,
                android.R.attr.state_pressed
        });

        setBackgroundColor(getResources().getColor(R.color.candidate_background));

        colorNormal = getResources().getColor(R.color.candidate_normal);
        colorRecommended = getResources().getColor(R.color.candidate_recommended);
        colorOther = getResources().getColor(R.color.candidate_other);
        verticalPadding = getResources().getDimensionPixelSize(R.dimen.candidate_vertical_padding);

        paint = new Paint();
        paint.setColor(colorNormal);
        paint.setAntiAlias(true);
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.candidate_font_height));
        paint.setStrokeWidth(0);

        padding = new Rect();

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                    float distanceX, float distanceY) {
                scrolled = true;
                int sx = getScrollX();
                sx += distanceX;
                if (sx < 0)
                    sx = 0;

                if (sx + getWidth() > totalWidth)
                    sx -= distanceX;

                targetScrollX = sx;
                scrollTo(sx, getScrollY());
                invalidate();
                return true;
            }
        });
        setHorizontalFadingEdgeEnabled(true);
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
    }

    /**
     * A connection back to the service to communicate with the text field
     *
     */
    public void setService(SoftKeyboard softKeyboard) {
        this.softKeyboard = softKeyboard;
    }

    @Override
    public int computeHorizontalScrollRange() {
        return totalWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = resolveSize(50, widthMeasureSpec);

        // Get the desired height of the icon menu view (last row of items does
        // not have a divider below)
        selectionHighlight.getPadding(padding);
        final int desiredHeight = ((int) paint.getTextSize()) + verticalPadding + padding.top + padding.bottom;

        // Maximum possible width and desired height
        setMeasuredDimension(measuredWidth, resolveSize(desiredHeight, heightMeasureSpec));
    }

    /**
     * If the canvas is null, then only touch calculations are performed to pick the target
     * candidate.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null)
            super.onDraw(canvas);

        totalWidth = 0;
        if (suggestions == null)
            return;

        if (backgroundPadding == null) {
            backgroundPadding = new Rect(0, 0, 0, 0);
            if (getBackground() != null)
                getBackground().getPadding(backgroundPadding);
        }

        int x = 0;
        final int count = suggestions.size();
        final int height = getHeight();
        GlobalClass.printLog("Canditate View", "==getHeight====" + getHeight());
        final Rect bgPadding = backgroundPadding;
        final Paint paint = this.paint;
        final int touchX = this.touchX;
        final int scrollX = getScrollX();
        final boolean scrolled = this.scrolled;
        final boolean typedWordValid = this.typedWordValid;
        final int y = (int) (((height - this.paint.getTextSize()) / 2) - this.paint.ascent());

        for (int i = 0; i < count; i++) {
            String suggestion = suggestions.get(i);
            float textWidth = paint.measureText(suggestion);
            final int wordWidth = (int) textWidth + X_GAP * 2;

            wordX[i] = x;
            this.wordWidth[i] = wordWidth;
            paint.setColor(colorNormal);
            if (touchX + scrollX >= x && touchX + scrollX < x + wordWidth && !scrolled) {
                if (canvas != null) {
                    canvas.translate(x, 0);
                    selectionHighlight.setBounds(0, bgPadding.top, wordWidth, height);
                    selectionHighlight.draw(canvas);
                    canvas.translate(-x, 0);
                }
                selectedIndex = i;
            }

            if (canvas != null) {
                if ((i == 1 && !typedWordValid) || (i == 0 && typedWordValid)) {
                    paint.setFakeBoldText(true);
                    paint.setColor(colorRecommended);
                } else if (i != 0)
                    paint.setColor(colorOther);

                canvas.drawText(suggestion, x + X_GAP, y, paint);
                paint.setColor(colorOther);
                canvas.drawLine(x + wordWidth + 0.5f, bgPadding.top, x + wordWidth + 0.5f, height + 1, paint);
                paint.setFakeBoldText(false);
            }
            x += wordWidth;
        }
        totalWidth = x;
        if (targetScrollX != getScrollX())
            scrollToTarget();
    }

    private void scrollToTarget() {
        int sx = getScrollX();
        if (targetScrollX > sx) {
            sx += SCROLL_PIXELS;
            if (sx >= targetScrollX) {
                sx = targetScrollX;
                requestLayout();
            }
        } else {
            sx -= SCROLL_PIXELS;
            if (sx <= targetScrollX) {
                sx = targetScrollX;
                requestLayout();
            }
        }
        scrollTo(sx, getScrollY());
        invalidate();
    }

    @SuppressLint("WrongCall")
    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {
        clear();
        if (suggestions != null)
            this.suggestions = new ArrayList<>(suggestions);

        this.typedWordValid = typedWordValid;
        scrollTo(0, 0);
        targetScrollX = 0;
        // Compute the total width
        onDraw(null);
        invalidate();
        requestLayout();
    }

    public void clear() {
        suggestions = EMPTY_LIST;
        touchX = OUT_OF_BOUNDS;
        selectedIndex = -1;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (gestureDetector.onTouchEvent(motionEvent))
            return true;

        int action = motionEvent.getAction();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        touchX = x;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                scrolled = false;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (y <= 0) {
                    // Fling up!?
                    if (selectedIndex >= 0) {
                        softKeyboard.pickSuggestionManually(selectedIndex);
                        selectedIndex = -1;
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (!scrolled)
                    if (selectedIndex >= 0)
                        softKeyboard.pickSuggestionManually(selectedIndex);
                selectedIndex = -1;
                removeHighlight();
                requestLayout();
                break;
        }
        return true;
    }

    /**
     * For flick through from keyboard, call this method with the x coordinate of the flick
     * gesture.
     *
     */
    @SuppressLint("WrongCall")
    public void takeSuggestionAt(float x) {
        touchX = (int) x;
        // To detect candidate
        onDraw(null);
        if (selectedIndex >= 0)
            softKeyboard.pickSuggestionManually(selectedIndex);
        invalidate();
    }

    private void removeHighlight() {
        touchX = OUT_OF_BOUNDS;
        invalidate();
    }
}
