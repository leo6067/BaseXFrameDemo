package com.xy.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xy.demo.R;
import com.xy.demo.model.FirstModel;
import com.xy.demo.model.SecondModel;
import com.xy.demo.utils.MyUtils;

import java.io.File;
import java.util.List;

//多级 展开列表   多级列表适配器
public class ExpandListViewAdapter extends BaseExpandableListAdapter {
    private List<FirstModel> mListData;
    private LayoutInflater mInflate;
    private Context context;

    public ExpandListViewAdapter(List<FirstModel> mListData, Context context) {
        this.mListData = mListData;
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return mListData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return 1;  如果有第三层级采用 返回1
        return mListData.get(groupPosition).getListSecondModel().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListData.get(groupPosition).getListSecondModel().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        FirstHolder holder = null;
        if (convertView == null) {
            holder = new FirstHolder();
            convertView = mInflate.inflate(R.layout.item_expand_lv_first, parent, false);
            holder.itemName = ((TextView) convertView.findViewById(R.id.itemName));
            holder.itemSize = ((TextView) convertView.findViewById(R.id.itemSize));
            holder.itemIcon = ((ImageView) convertView.findViewById(R.id.itemIcon));
            holder.itemCheck = ((CheckBox) convertView.findViewById(R.id.itemCheck));
            convertView.setTag(holder);
        } else {
            holder = (FirstHolder) convertView.getTag();
        }
        holder.itemName.setText(mListData.get(groupPosition).getTitle());

        Glide.with(holder.itemIcon).load(mListData.get(groupPosition).getIcon()).into(holder.itemIcon);

        final FirstHolder finalHolder = holder;
        finalHolder.itemCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = finalHolder.itemCheck.isChecked();
                mListData.get(groupPosition).setCheck(isChecked);
                for (int i = 0; i < mListData.get(groupPosition).getListSecondModel().size(); i++) {
                    SecondModel secondModel = mListData.get(groupPosition).getListSecondModel().get(i);
                    secondModel.setCheck(isChecked);
//                    for (int j = 0; j < secondModel.getListThirdModel().size(); j++) {
//                        ThirdModel thirdModel = secondModel.getListThirdModel().get(j);
//                        thirdModel.setCheck(isChecked);
//                    }
                }
                LiveEventBus.get("sizeRefresh").post("refresh");
                notifyDataSetChanged();
            }
        });

        finalHolder.itemCheck.setChecked(true);

        long itemCache = 0;

        for (int i = 0; i < mListData.get(groupPosition).getListSecondModel().size(); i++) {
            SecondModel secondModel = mListData.get(groupPosition).getListSecondModel().get(i);
            if (!secondModel.isCheck()) {
                finalHolder.itemCheck.setChecked(false);
                mListData.get(groupPosition).setCheck(false);
            } else {
                itemCache += secondModel.getFileSize();
            }
        }

        holder.itemSize.setText(MyUtils.byte2FitMemorySize(itemCache));

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondHolder holder = null;

        SecondModel secondModel = mListData.get(groupPosition).getListSecondModel().get(childPosition);

        if (convertView == null) {
            holder = new SecondHolder();
            convertView = mInflate.inflate(R.layout.item_expand_lv_second, parent, false);
            holder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            holder.itemSize = (TextView) convertView.findViewById(R.id.itemSize);
            holder.itemTime = (TextView) convertView.findViewById(R.id.itemTime);
            holder.itemCheck = (CheckBox) convertView.findViewById(R.id.itemCheck);
            holder.itemIcon = (ImageView) convertView.findViewById(R.id.itemIcon);
            convertView.setTag(holder);
        } else {
            holder = (SecondHolder) convertView.getTag();
        }


        holder.itemName.setText(secondModel.getTitle());
        holder.itemSize.setText(MyUtils.byte2FitMemorySize(secondModel.getFile().length()));
        holder.itemIcon.setTag(secondModel.getTitle());
        holder.itemTime.setText(secondModel.getFileTime());
        Glide.with(context).load(secondModel.getFile().getAbsoluteFile()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.icon_big_img).into(holder.itemIcon);
        SecondHolder finalHolder = holder;
        if (mListData.get(groupPosition).getTitle().equals("IMAGE")) {
            holder.itemIcon.setVisibility(View.VISIBLE);
        }else {
            holder.itemIcon.setVisibility(View.GONE);
        }

//            Observable<Bitmap> longRunningOperation = Observable.create(new ObservableOnSubscribe<Bitmap>() {
//                @Override
//                public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
//                    // 执行耗时操作
//                    Bitmap bitmap = getThumbnail(secondModel.getFile(), 25, 25);
//                    emitter.onNext(bitmap);
//                    emitter.onComplete();
//                }
//            });
//            longRunningOperation
//                    .subscribeOn(Schedulers.io()) // 在 IO 线程上执行耗时操作
//                    .observeOn(AndroidSchedulers.mainThread()) // 在主线程上观察结果
//                    .subscribe(new Observer<Bitmap>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            // 可以在这里保存 Disposable 对象以便稍后取消订阅
//                        }
//
//                        @Override
//                        public void onNext(Bitmap result) {
//                            // 更新 UI 控件
//                            if (result != null && secondModel.getTitle().equals(finalHolder.itemIcon.getTag())) {
//                                // 设置图片
////                                finalHolder.itemIcon.setImageBitmap(result);
////                            Glide.with(context).load(secondModel.getFile().getAbsoluteFile()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.icon_big_img).into(finalHolder.itemIcon);
//                            }
//                        }
//                        @Override
//                        public void onError(Throwable e) {
//                            // 处理错误
//                            e.printStackTrace();
//                        }
//                        @Override
//                        public void onComplete() {
//                            // 操作完成
//                        }
//                    });
//        } else {
//            holder.itemIcon.setVisibility(View.GONE);
//        }



        holder.itemCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = finalHolder.itemCheck.isChecked();
                Log.d("bigname", "onCheckedChanged: second:" + groupPosition + "," + isChecked);
                secondModel.setCheck(isChecked);
//                    for (int i = 0; i < listSecondModel.get(groupPosition).getListThirdModel().size(); i++) {
//                        ThirdModel thirdModel = listSecondModel.get(groupPosition).getListThirdModel().get(i);
//                        thirdModel.setCheck(isChecked);
//                    }
                LiveEventBus.get("sizeRefresh").post("refresh");
                notifyDataSetChanged();
            }
        });

        holder.itemCheck.setChecked(secondModel.isCheck());
        return convertView;


        //如果有第三层   采用以下用法
//        CustomExpandableListView lv = ((CustomExpandableListView) convertView);
//        if (convertView == null) {
//            lv = new CustomExpandableListView(context);
//        }
//        SecondAdapter secondAdapter = new SecondAdapter(context, mListData.get(groupPosition).getListSecondModel());
//        lv.setAdapter(secondAdapter);
//        return lv;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /*
     *   第二层的适配器 （有第三层级的情况 在第二层使用此适配器）
     * */
    class SecondAdapter extends BaseExpandableListAdapter {
        Context context;
        LayoutInflater inflater;
        List<SecondModel> listSecondModel;

        public SecondAdapter(Context context, List<SecondModel> listSecondModel) {
            this.context = context;
            this.listSecondModel = listSecondModel;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getGroupCount() {
            int size = listSecondModel.size();
            Log.d("bigname", "getGroupCount: " + size);
            return size;
        }


        // 第三层  层级数量 三层是否出来
        @Override
        public int getChildrenCount(int groupPosition) {
//            return listSecondModel.get(groupPosition).getListThirdModel().size();
            return 0;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return listSecondModel.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return listSecondModel.get(groupPosition).getListThirdModel().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            SecondHolder holder = null;
            if (convertView == null) {
                holder = new SecondHolder();
                convertView = mInflate.inflate(R.layout.item_expand_lv_second, parent, false);
                holder.itemName = (TextView) convertView.findViewById(R.id.itemName);
                holder.itemSize = (TextView) convertView.findViewById(R.id.itemSize);
                holder.itemTime = (TextView) convertView.findViewById(R.id.itemTime);
                holder.itemCheck = (CheckBox) convertView.findViewById(R.id.itemCheck);
                holder.itemIcon = (ImageView) convertView.findViewById(R.id.itemIcon);
                convertView.setTag(holder);
            } else {
                holder = (SecondHolder) convertView.getTag();
            }


            holder.itemName.setText(listSecondModel.get(groupPosition).getTitle());
            final SecondHolder secondHolder = holder;
            secondHolder.itemCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = secondHolder.itemCheck.isChecked();
                    Log.d("bigname", "onCheckedChanged: second:" + groupPosition + "," + isChecked);
                    listSecondModel.get(groupPosition).setCheck(isChecked);
//                    for (int i = 0; i < listSecondModel.get(groupPosition).getListThirdModel().size(); i++) {
//                        ThirdModel thirdModel = listSecondModel.get(groupPosition).getListThirdModel().get(i);
//                        thirdModel.setCheck(isChecked);
//                    }
                    notifyDataSetChanged();
                }
            });


            secondHolder.itemCheck.setChecked(listSecondModel.get(groupPosition).isCheck());
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ThirdHolder holder = null;
            if (convertView == null) {
                holder = new ThirdHolder();
                convertView = mInflate.inflate(R.layout.item_expand_lv_third, parent, false);
                holder.tv = ((TextView) convertView.findViewById(R.id.itemName));
                holder.cb = ((CheckBox) convertView.findViewById(R.id.cb));
                convertView.setTag(holder);
            } else {
                holder = (ThirdHolder) convertView.getTag();
            }


            holder.tv.setText(listSecondModel.get(groupPosition).getListThirdModel().get(childPosition).getTitle());
            final ThirdHolder thirdHolder = holder;
            thirdHolder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = thirdHolder.cb.isChecked();
                    Log.d("bigname", "onCheckedChanged: third:" + groupPosition + "," + isChecked);
                    listSecondModel.get(groupPosition).getListThirdModel().get(childPosition).setCheck(isChecked);
                }
            });
            thirdHolder.cb.setChecked(listSecondModel.get(groupPosition).getListThirdModel().get(childPosition).isCheck());
            return convertView;
        }


        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


    class FirstHolder {
        TextView itemName;
        TextView itemSize;
        CheckBox itemCheck;
        ImageView itemIcon;

    }

    class SecondHolder {
        TextView itemName;
        TextView itemSize;
        TextView itemTime;
        CheckBox itemCheck;
        ImageView itemIcon;
    }

    class ThirdHolder {
        TextView tv;
        CheckBox cb;
    }


    public Bitmap getThumbnail(File imageFile, int reqWidth, int reqHeight) {
        // 第一步：初始化BitmapFactory.Options
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置为true，以便先获取图片尺寸，而不是直接解码整个图片

        // 第二步：获取图片的原始尺寸
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        // 第三步：计算inSampleSize
        options.inSampleSize = calculateInSampleSize(imageWidth, imageHeight, reqWidth, reqHeight);

        // 第四步：设置inJustDecodeBounds为false并解码图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
    }

    public int calculateInSampleSize(int srcWidth, int srcHeight, int reqWidth, int reqHeight) {
        // 源图片和目标图片的宽高比
        final float srcAspect = (float) srcWidth / (float) srcHeight;
        final float reqAspect = (float) reqWidth / (float) reqHeight;

        // 计算宽度和高度的缩放比例
        int inSampleSize = 1;

        if (srcHeight > reqHeight || srcWidth > reqWidth) {
            final int halfHeight = srcHeight / 2;
            final int halfWidth = srcWidth / 2;

            // 计算最大的inSampleSize值，它是一个2的幂，并且小于或等于halfHeight和halfWidth
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
