package com.xiner.dcball.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by hy on 16-3-31.
 */
public class XProgressDialog
{
    public ProgressDialog mProgress;

    public void createProgressDialog(Context context, String title, String message)
    {
        mProgress = new ProgressDialog(context);
        mProgress.setTitle(title);
        mProgress.setMessage(message);
        mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //设置ProgressDialog 的进度条是否不明确
        mProgress.setIndeterminate(true);
        //设置ProgressDialog 是否可以按退回按键取消
        mProgress.setCancelable(false);
    }

    public void setProgressMax(int max)
    {
        mProgress.setMax(max);
    }

    public void setProgress(int progress)
    {
        mProgress.setProgress(progress);
    }

    public void show()
    {
        mProgress.show();
    }

    public void dismiss()
    {
        mProgress.dismiss();
    }

    public void setProgressMsg(String message)
    {
        mProgress.setMessage(message);
    }


}
