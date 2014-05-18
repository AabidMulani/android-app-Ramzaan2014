package com.shuraidinfotech.ramzaan2014.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.shuraidinfotech.ramzaan2014.R;

/**
 * Created by AABID on 18/5/14.
 */
public class InitializeDatabase extends AsyncTask<Void,Void,Boolean> {
    private ProgressDialog progressDialog;
    private Activity activity;

    public InitializeDatabase(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage(activity.getString(R.string.msg_initialize_database));
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if(result){

        }else{
            Utils.showThisMsg(activity,activity.getString(R.string.title_error),activity.getString(R.string.msg_error_init_database),new Utils.OnOkPressedListener() {
                @Override
                public void onOKPressed() {
                    activity.finish();
                }
            });
        }
    }
}
