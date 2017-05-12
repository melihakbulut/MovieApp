package com.movieapp.melihakbulut.movieapp.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.movieapp.melihakbulut.movieapp.R;

/**
 * Created by melih.akbulut on 25.04.2017.
 */
public class CustomDialog extends ProgressDialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
    }

    public static ProgressDialog ctor(Context context) {
        CustomDialog dialog = new CustomDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }
}
