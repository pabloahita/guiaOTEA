package gui.customItems;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

public class CustomScrollView extends NestedScrollView {
    private ConstraintLayout contentView;
    public CustomScrollView(@NonNull Context context) {
        super(context);
    }

    public CustomScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        contentView = new ConstraintLayout(context);
        addView(contentView);
    }

    @Override
    public void addView(View child) {
        contentView.addView(child);
        super.addView(contentView);
    }

    @Override
    public void addView(View child, int index) {
        contentView.addView(child, index);
        super.addView(contentView, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        contentView.addView(child, width, height);
        super.addView(contentView, width, height);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        contentView.addView(child, params);
        super.addView(contentView, params);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        contentView.addView(child, index, params);
        super.addView(contentView, index, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        for (int i = 0; i < contentView.getChildCount(); i++) {
            View child = contentView.getChildAt(i);
            height += child.getMeasuredHeight();
        }
        setMeasuredDimension(getMeasuredWidth(), height);
    }
}
