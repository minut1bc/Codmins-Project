package com.codminskeyboards.universekeyboard.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener listener;

    private GestureDetector gestureDetector;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        this.listener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
        View childView = view.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (childView != null && listener != null && gestureDetector.onTouchEvent(motionEvent))
            listener.onItemClick(childView, view.getChildAdapterPosition(childView));
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
