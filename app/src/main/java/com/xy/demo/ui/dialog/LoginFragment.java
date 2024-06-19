package com.xy.demo.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.xy.demo.R;


//修改 RateDialog
public class LoginFragment extends DialogFragment {
    private static volatile LoginFragment dialog = null;


//    private GoogleSignInClient mGoogleSignInClient;

    public static LoginFragment getInstance() {
        if (dialog == null) {
            synchronized (LoginFragment.class) {
                if (dialog == null) {
                    dialog = new LoginFragment();
                }
            }
        }
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login, container, false);
        initView(view);
        return view;


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog createDialog = super.onCreateDialog(savedInstanceState);
        createDialog.setCancelable(false);
        return createDialog;
    }


    public void initView(View view) {
        ImageView closeIV = view.findViewById(R.id.closeIV);
        EditText emailET = view.findViewById(R.id.emailET);
        TextView yzmTV = view.findViewById(R.id.yzmTV);
        EditText yzmET = view.findViewById(R.id.yzmET);
        TextView loginTV = view.findViewById(R.id.loginTV);
        TextView googleTV = view.findViewById(R.id.googleTV);


    }
}
