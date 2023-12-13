package com.xy.demo.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xy.demo.R;
import com.xy.demo.base.Constants;
import com.xy.demo.event.DialogEvent;
import com.xy.demo.network.params.LanguageUtil;
import com.xy.demo.view.SizeColorClickTextview;
import com.xy.xframework.dialog.BaseDialogFragment;


public class AgreementDialogFragment extends BaseDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        setGravity(Gravity.CENTER);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_agreement;
    }



    @Override
    protected void initViews(View v) {
        String appName = LanguageUtil.getString(getActivity(), R.string.appName);
        SizeColorClickTextview contentTV = v.findViewById(R.id.contentTV);
        String content = String.format(
                LanguageUtil.getString(
                        getActivity(),
                        R.string.private_content
                ), appName, appName
        );
        // 设置文字
        contentTV.setMyText(
                getActivity(),
                content,
                0f,
                -1,
                2
        );

        TextView dialogOk = v.findViewById(R.id.dialog_ok);
        TextView dialogCancel = v.findViewById(R.id.dialog_cancel);

        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogEvent dialogEvent = new DialogEvent();
                dialogEvent.setPressStatus(true);
                LiveEventBus.get(Constants.Companion.getDialog_back()).post(dialogEvent);
                dismiss();
            }
        });

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogEvent dialogEvent = new DialogEvent();
                dialogEvent.setPressStatus(false);
                LiveEventBus.get(Constants.Companion.getDialog_back()).post(dialogEvent);
                dismiss();
            }
        });


    }
}
