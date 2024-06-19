package com.xy.demo.ui.adapter;





import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.huxq17.handygridview.scrollrunner.OnItemMovedListener;
import com.xy.demo.R;
import com.xy.demo.model.ImgModel;
import com.zhy.view.flowlayout.TagView;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter implements OnItemMovedListener {
    private Context context;
    private List<ImgModel> mDatas = new ArrayList<>();

    public GridViewAdapter(Context context, List<ImgModel> dataList) {
        this.context = context;
        this.mDatas.addAll(dataList);
    }

    private GridView mGridView;
    private boolean inEditMode = false;

    public void setData(List<ImgModel> dataList) {
        this.mDatas.clear();
        this.mDatas.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setInEditMode(boolean inEditMode) {
        this.inEditMode = inEditMode;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public ImgModel getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View  layView = LayoutInflater.from(context).inflate( R.layout.item_drag_image,null);
            convertView = layView;

            TextView itemOrder =layView.findViewById(R.id.itemOrder);
            ImageView imageView = layView.findViewById(R.id.imageView);

            // 设置边角半径为10dp
            int cornerRadius = 10; // 可以根据需要调整
            BaseRequestOptions options = new RequestOptions().transform(new RoundedCorners(cornerRadius));

            Glide.with(imageView)
                    .asBitmap()
                    .load( mDatas.get(position).imgPath)
                    .apply(options)
                    .into(imageView);






//        if (!isFixed(position)) {
//            textView.showDeleteIcon(inEditMode);
//        } else {
//            textView.showDeleteIcon(false);
//        }
//        textView.setText(getItem(position));

        return convertView;
    }

    @Override
    public void onItemMoved(int from, int to) {
        ImgModel s = mDatas.remove(from);
        mDatas.add(to, s);
    }

    @Override
    public boolean isFixed(int position) {
        //When postion==0,the item can not be dragged.
        if (position == 0) {
            return true;
        }
        return false;
    }


}