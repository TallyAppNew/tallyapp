package com.ngedev.core;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

class DialogLoading {
    Dialog mDialog;
    public DialogLoading(Context context){
        mDialog = new Dialog(context);
    }

    void showDialog() {
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_loading);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    void cancelDialog(){
        mDialog.cancel();
    }
}
