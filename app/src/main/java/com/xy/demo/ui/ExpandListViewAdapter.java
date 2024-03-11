package com.xy.demo.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xy.demo.R;
import com.xy.demo.model.FirstModel;
import com.xy.demo.model.SecondModel;
import com.xy.demo.model.ThirdModel;

import java.util.List;


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
            holder.tv = ((TextView) convertView.findViewById(R.id.tv));
            holder.cb = ((CheckBox) convertView.findViewById(R.id.cb));
            convertView.setTag(holder);
        } else {
            holder = (FirstHolder) convertView.getTag();
        }
        holder.tv.setText(mListData.get(groupPosition).getTitle());
        final FirstHolder finalHolder = holder;
        finalHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = finalHolder.cb.isChecked();
                mListData.get(groupPosition).setCheck(isChecked);
                for (int i = 0; i < mListData.get(groupPosition).getListSecondModel().size(); i++) {
                    SecondModel secondModel = mListData.get(groupPosition).getListSecondModel().get(i);
                    secondModel.setCheck(isChecked);
//                    for (int j = 0; j < secondModel.getListThirdModel().size(); j++) {
//                        ThirdModel thirdModel = secondModel.getListThirdModel().get(j);
//                        thirdModel.setCheck(isChecked);
//                    }
                }
                notifyDataSetChanged();
            }
        });

        finalHolder.cb.setChecked(true);
        for (int i = 0; i < mListData.get(groupPosition).getListSecondModel().size(); i++) {
            SecondModel secondModel = mListData.get(groupPosition).getListSecondModel().get(i);
            if (!secondModel.isCheck()) {
                finalHolder.cb.setChecked(false);
                mListData.get(groupPosition).setCheck(false);
            }
        }
        notifyDataSetChanged();
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondHolder secondHolder = null;
        if (convertView == null) {
            secondHolder = new SecondHolder();
            convertView = mInflate.inflate(R.layout.item_expand_lv_second, parent, false);
            secondHolder.tv = ((TextView) convertView.findViewById(R.id.tv));
            secondHolder.cb = ((CheckBox) convertView.findViewById(R.id.cb));
            convertView.setTag(secondHolder);
        } else {
            secondHolder = (SecondHolder) convertView.getTag();
        }
        secondHolder.tv.setText(mListData.get(groupPosition).getListSecondModel().get(childPosition).getTitle());
        SecondHolder finalSecondHolder = secondHolder;
        SecondModel secondModel = mListData.get(groupPosition).getListSecondModel().get(childPosition);
        secondHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = finalSecondHolder.cb.isChecked();
                Log.d("bigname", "onCheckedChanged: second:" + groupPosition + "," + isChecked);
                secondModel.setCheck(isChecked);
//                    for (int i = 0; i < listSecondModel.get(groupPosition).getListThirdModel().size(); i++) {
//                        ThirdModel thirdModel = listSecondModel.get(groupPosition).getListThirdModel().get(i);
//                        thirdModel.setCheck(isChecked);
//                    }
                notifyDataSetChanged();
            }
        });

        secondHolder.cb.setChecked(secondModel.isCheck());
        finalSecondHolder.cb.setChecked(secondModel.isCheck());
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
                holder.tv = ((TextView) convertView.findViewById(R.id.tv));
                holder.cb = ((CheckBox) convertView.findViewById(R.id.cb));
                convertView.setTag(holder);
            } else {
                holder = (SecondHolder) convertView.getTag();
            }
            holder.tv.setText(listSecondModel.get(groupPosition).getTitle());
            final SecondHolder secondHolder = holder;
            secondHolder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = secondHolder.cb.isChecked();
                    Log.d("bigname", "onCheckedChanged: second:" + groupPosition + "," + isChecked);
                    listSecondModel.get(groupPosition).setCheck(isChecked);
//                    for (int i = 0; i < listSecondModel.get(groupPosition).getListThirdModel().size(); i++) {
//                        ThirdModel thirdModel = listSecondModel.get(groupPosition).getListThirdModel().get(i);
//                        thirdModel.setCheck(isChecked);
//                    }
                    notifyDataSetChanged();
                }
            });


            secondHolder.cb.setChecked(listSecondModel.get(groupPosition).isCheck());
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ThirdHolder holder = null;
            if (convertView == null) {
                holder = new ThirdHolder();
                convertView = mInflate.inflate(R.layout.item_expand_lv_third, parent, false);
                holder.tv = ((TextView) convertView.findViewById(R.id.tv));
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
        TextView tv;
        CheckBox cb;
    }

    class SecondHolder {
        TextView tv;
        CheckBox cb;
    }

    class ThirdHolder {
        TextView tv;
        CheckBox cb;
    }
}
