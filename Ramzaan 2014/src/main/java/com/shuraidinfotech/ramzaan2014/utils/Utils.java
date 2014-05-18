package com.shuraidinfotech.ramzaan2014.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.shuraidinfotech.ramzaan2014.R;

/**
 * Created by AABID on 18/5/14.
 */
public class Utils {

    public static void showToast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    public static void showThisMsg(Activity activity,String title, String msg,final OnOkPressedListener listener) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
            builder.setTitle(title==null?activity.getString(R.string.app_name):title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setNeutralButton(activity.getString(R.string.msg_ok),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(listener!=null){
                    listener.onOKPressed();
                }
            }
        });
        builder.create();
        builder.show();
    }

    public interface OnOkPressedListener{
        public void onOKPressed();
    }

}
