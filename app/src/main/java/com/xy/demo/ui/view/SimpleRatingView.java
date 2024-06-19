package com.xy.demo.ui.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xingliuhua.xlhratingbar.IRatingView;
import com.xy.demo.R;


//星星等级 打分
public class SimpleRatingView implements IRatingView {
    ViewGroup mViewGroup;
    @Override
    public void setCurrentState(int state, int position, int starNums) {
        ImageView ivStar = mViewGroup.findViewById(R.id.iv_star);
        switch (state) {
            case IRatingView.STATE_NONE:
                ivStar.setImageResource( R.drawable.icon_star_b);
                break;
            case IRatingView.STATE_HALF:
            case IRatingView.STATE_FULL:
                ivStar.setImageResource( R.drawable.icon_star_a);
                break;
        }

    }

    @Override
    public ViewGroup getRatingView(Context context, int position, int starNums) {
        View inflate = View.inflate(context, R.layout.view_rate, null);
        mViewGroup = (ViewGroup) inflate;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        mViewGroup.setLayoutParams(layoutParams);
        return mViewGroup;
    }
}