package com.xy.demo.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.d.lib.pulllayout.rv.adapter.CommonAdapter;
import com.d.lib.pulllayout.rv.adapter.CommonHolder;
import com.d.lib.pulllayout.rv.itemtouchhelper.ItemTouchHelperViewHolder;
import com.xy.demo.R;
import com.xy.demo.model.ImgModel;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;

import java.util.Collections;
import java.util.List;

/**
 * ItemTouchGridAdapter
 * Created by D on 2017/6/3.
 */
public class ItemTouchGridAdapter extends BaseQuickAdapter<ImgModel, BaseViewHolder> {

    public ItemTouchGridAdapter() {
        super(R.layout.item_drag_image);
    }



    @Override
    protected void convert(@NonNull BaseViewHolder holder, ImgModel bean) {
        TextView itemOrder = holder.getView(R.id.itemOrder);
        ImageView imageView = holder.getView(R.id.imageView);

        itemOrder.setText(getItemPosition(bean)+1 +"/"+getData().size());


        // 设置边角半径为10dp
        int cornerRadius = 10; // 可以根据需要调整
        BaseRequestOptions options = new RequestOptions().transform(new RoundedCorners(cornerRadius));

        Glide.with(imageView)
                .asBitmap()
                .load(bean.imgPath)
                .apply(options)
                .into(imageView);
    }




    public void exchange(int fromPosition,int toPosition){
        // 更新数据集中的位置
        Collections.swap(getData(), fromPosition, toPosition);
        // 更新RecyclerView的显示
        notifyItemMoved(fromPosition, toPosition);
    }


//        imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN
//                        && getItemCount() > 1 && mStartDragListener != null) {
//                    // Step 3-3: Only calling onStartDrag will trigger the drag,
//                    // here the drag will start when touched,
//                    // of course, you can also click or long press to start
//                    mStartDragListener.onStartDrag(holder);
//                    return true;
//                }
//                return false;
//            }
//        });
        //
//        holder.setOnItemTouchListener(new ItemTouchHelperViewHolder() {
//            @Override
//            public void onItemSelected() {
//                // Callback when dragging is triggered
//                tvHandler.setBackgroundDrawable(ContextCompat.getDrawable(mContext,
//                        R.drawable.corner_bg_touch_select));
//            }
//
//            @Override
//            public void onItemClear() {
//                // Callback when finger is released
//                tvHandler.setBackgroundDrawable(ContextCompat.getDrawable(mContext,
//                        R.drawable.corner_bg_touch_normal));
//            }
//        });
    }


//    @Override
//    public void onItemDismiss(int position) {
//        mDatas.remove(position);
//        notifyItemRemoved(position);
//
//    }
//
//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        if (Math.abs(fromPosition - toPosition) > 1) {
//            ImgModel from = mDatas.get(fromPosition);
//            mDatas.remove(fromPosition);
//            mDatas.add(toPosition, from);
//        } else {
//            Collections.swap(mDatas, fromPosition, toPosition);
//        }
//        notifyItemMoved(fromPosition, toPosition);
//        notifyDataSetChanged();
//        return true;
//    }

