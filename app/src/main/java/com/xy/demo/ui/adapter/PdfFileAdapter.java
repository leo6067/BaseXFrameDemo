package com.xy.demo.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;
import com.xy.demo.db.PdfFileModel;
import com.xy.demo.network.Globals;

public class PdfFileAdapter extends BaseQuickAdapter<PdfFileModel, BaseViewHolder> {
    public PdfFileAdapter() {
        super(R.layout.item_pdf_file);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, PdfFileModel pdfFileModel) {

        TextView itemNameTV = baseViewHolder.getView(R.id.itemNameTV);
        TextView itemSrcTV = baseViewHolder.getView(R.id.itemSrcTV);
        ImageView itemIconIv = baseViewHolder.getView(R.id.itemIconIv);
        itemNameTV.setText(pdfFileModel.name);
        itemSrcTV.setText(pdfFileModel.lastTime + "    " + pdfFileModel.size);
    }
}
