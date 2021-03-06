package com.fanyafeng.materialdesign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by fanyafeng on 2015/12/16,0016.
 */
public class MyScrollView extends ScrollView {

    private static final String TAG = "MyScrollView";

    private OnScrollListener onScrollListener;

    private MyScrollView myScrollView;

    public MyScrollView getMyScrollView() {
        return myScrollView;
    }

    public void setMyScrollView(MyScrollView myScrollView) {
        this.myScrollView = myScrollView;
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollListener(OnScrollListener scrollListener) {
        this.onScrollListener = scrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.OnScroll(l, t, oldl, oldt);
        }
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
    }

    public interface OnScrollListener {
        public void OnScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (myScrollView != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    Log.d(TAG, "手势拦截：MotionEvent.ACTION_MOVE");
                    myScrollView.requestDisallowInterceptTouchEvent(true);
                    break;
            }
        }


        return super.onInterceptTouchEvent(ev);
    }


}
