package com.xiner.dcball.field;

import android.content.Context;
import android.view.View;

public class FieldFoot
{
    private final String TAG = getClass().getName();

    private Context mContext;
    // foot view
    private View mViewFoot;

    public FieldFoot(Context context, View view)
    {
        mContext = context;
        mViewFoot = view;

        init();
    }

    private void init()
    {

    }
}
