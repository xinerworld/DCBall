package com.xiner.dcball.field;

import android.content.Context;
import android.view.View;

public class FieldContent
{
    private final String TAG = getClass().getName();

    private Context mContext;
    // content view
    private View mViewContent;

    public FieldContent(Context context, View view)
    {
        mContext = context;
        mViewContent = view;

        init();
    }

    private void init()
    {

    }
}
