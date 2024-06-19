package com.xy.demo.ui.adapter;

import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.ruffian.library.widget.RCheckBox;
import com.xy.demo.R;
import com.xy.demo.base.Constants;
import com.xy.demo.db.PdfFileModel;
import com.xy.demo.network.Globals;

public class PdfFileSelectAdapter extends BaseQuickAdapter<PdfFileModel, BaseViewHolder> {

    int selectNumber = 0;
    int selectAll = 0; //0:多选 1：全选 2：全不选

    public PdfFileSelectAdapter() {
        super(R.layout.item_pdf_file_select);
    }


    public int getSelectNumber() {
        return selectNumber;
    }

    public void selectAll(int selectAllA) {
        this.selectAll = selectAllA;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, PdfFileModel pdfFileModel) {
        TextView itemNameTV = baseViewHolder.getView(R.id.itemNameTV);
        TextView itemSrcTV = baseViewHolder.getView(R.id.itemSrcTV);
        ImageView itemSelectIV = baseViewHolder.getView(R.id.itemSelectIV);
        itemNameTV.setText(pdfFileModel.name);
        itemSrcTV.setText(pdfFileModel.lastTime + " · " + pdfFileModel.size);
        if (selectAll == 1) {
            itemSelectIV.setSelected(true);
        }
        if (selectAll == 2) {
            itemSelectIV.setSelected(false);
        }

        itemSelectIV.setBackgroundResource(pdfFileModel.selectStatus ? R.drawable.icon_file_select : R.drawable.icon_file_select_un);


//        itemSelectIV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                pdfFileModel.selectStatus = b;
//                if (b) {
//                    selectNumber++;
//                } else if (selectNumber > 0) {
//                    selectNumber--;
//                }
//                LiveEventBus.get(Constants.EVENT_FILE_SELECT).post(b);
//            }
//        });

    }
}
