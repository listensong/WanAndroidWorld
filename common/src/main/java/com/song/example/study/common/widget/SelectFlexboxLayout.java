package com.song.example.study.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.flexbox.FlexboxLayout;

/**
 * @author: Listensong
 * @time 19-12-2 下午4:12
 * @desc com.song.example.study.common.widget.SelectFlexboxLayout
 * 单选的FlexboxLayout效果
 */
public class SelectFlexboxLayout extends FlexboxLayout
        implements FlexboxViewAdapter.OnDataCacheChangeListener {

    public SelectFlexboxLayout(Context context) {
        super(context);
    }

    public SelectFlexboxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectFlexboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDataChanged() {
        changeAdapter();
    }

    private void changeAdapter() {
        if (this.flexboxViewAdapter == null) {
            return;
        }
        removeAllViews();
        int size = this.flexboxViewAdapter.getItemCount();
        for (int i = 0; i < size; i++) {
            View tagView = this.flexboxViewAdapter.viewDelegate(this, i);
            final int position = i;
            tagView.setOnClickListener(v -> flexboxViewAdapter.onItemClick(position));
            this.addView(tagView);
        }
    }

    private int saveSelectedPosition = -1;
    private void updateSelectedState(int prevPosition, int newPosition) {
        this.saveSelectedPosition = resetPosition(newPosition);
        if (saveSelectedPosition >= this.getChildCount()) {
            return;
        }
        if (prevPosition >= 0) {
            prevPosition = resetPosition(prevPosition);
            this.getChildAt(prevPosition).setSelected(false);
        }
        this.getChildAt(saveSelectedPosition).setSelected(true);
        if (flexboxViewAdapter != null) {
            flexboxViewAdapter.onItemSelected(saveSelectedPosition);
        }
    }

    private int resetPosition(int position) {
        if (position < 0 || position >= this.getChildCount()) {
            return 0;
        }
        return position;
    }

    public void setSelectedView(int position) {
        if (position < 0 || position > this.getChildCount()) {
            return;
        }
        updateSelectedState(saveSelectedPosition, position);
    }

    @Nullable
    private FlexboxViewAdapter flexboxViewAdapter;
    public void setAdapter(FlexboxViewAdapter viewAdapter) {
        this.flexboxViewAdapter = viewAdapter;
        this.flexboxViewAdapter.setOnDataCacheChangeListener(this);
    }
}
