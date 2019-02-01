package com.bw.movie.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.bw.movie.R;

public class FragmentFeedCmt extends BottomSheetDialogFragment {
    /**
     * 顶部向下偏移量
     */
    private int topOffset = 0;
    private BottomSheetBehavior<FrameLayout> behavior;
    int view;

    private int mHeight;
    private int mTrueheight;
    private View mInflate;

    public static FragmentFeedCmt newInstance(long l) {
        Bundle args = new Bundle();
        args.putLong("FEED_ID", l);
        FragmentFeedCmt fragment = new FragmentFeedCmt();
        fragment.setArguments(args);
        return fragment;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        return new BottomSheetDialog(getContext(), R.style.TransparentBottomSheetStyle);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return mInflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置软键盘不自动弹出
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getHeight();
            behavior = BottomSheetBehavior.from(bottomSheet);
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
    /**
     * 获取屏幕高度
     *
     * @return height
     */
    private int getHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int height=wm.getDefaultDisplay().getHeight();
        //设置dialog高度
        if (getContext() != null) {
            Point point = new Point();
            if (wm != null) {
                // 使用Point已经减去了状态栏高度
                wm.getDefaultDisplay().getSize(point);
                height = point.y - getTopOffset();
            }
        }
        return mTrueheight;
    }
    public int getTopOffset() {
        return topOffset;
    }
    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }
    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }

    //获得diolog的高度
    public void setTrueHeight(int trueHeight) {
        mTrueheight=trueHeight;

    }

    public void setView(View view) {
        this.mInflate=view;
    }
}

