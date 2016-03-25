package com.xiner.dcball.field;

import com.xiner.dcball.R;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class FieldHeader
{
	private final String TAG = getClass().getName();

	// header view
	private View mViewHeader;

	private TextView mTextView_lastDate;

	private TextView mTextView_lastLottery;

	public FieldHeader(View view)
	{
		Log.e(TAG, "new FieldHeader");

		mViewHeader = view;

		init();
	}

	private void init()
	{
		mTextView_lastDate = (TextView) mViewHeader.findViewById(R.id.last_data_text);
		mTextView_lastLottery = (TextView) mViewHeader.findViewById(R.id.last_lottery_text);
	}
}
