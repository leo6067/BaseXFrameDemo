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

    int selectNumber   = 0;

    public PdfFileSelectAdapter() {
        super(R.layout.item_pdf_file_select);
    }




    public int getSelectNumber(){
        return selectNumber;
    }
    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, PdfFileModel pdfFileModel) {
        TextView itemNameTV = baseViewHolder.getView(R.id.itemNameTV);
        TextView itemSrcTV = baseViewHolder.getView(R.id.itemSrcTV);
        RCheckBox itemSelectIV = baseViewHolder.getView(R.id.itemSelectIV);
        itemNameTV.setText(pdfFileModel.name);
        itemSrcTV.setText(pdfFileModel.lastTime + " · " + pdfFileModel.size + " · " + pdfFileModel.totalPages+itemSrcTV.getContext().getString(R.string.page));
        itemSelectIV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                pdfFileModel.selectStatus = b;
                if (b){
                    selectNumber++;
                }else {
                    selectNumber--;
                }
                LiveEventBus.get(Constants.EVENT_FILE_SELECT).post(b);
            }
        });
        Globals.log("xxxxxxxxxPdfFileAdapter"+pdfFileModel.path);
    }
}
