package com.xiner.dcball.field;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.xiner.dcball.R;

import android.view.View;
import android.widget.TextView;
import com.xiner.dcball.util.LogX;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FieldHeader
{
	private final String TAG = getClass().getName();

	// header view
	private View mViewHeader;

    private Context mContext;

	private TextView mTextView_lastDate;

	private TextView mTextView_lastLottery;

    public Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {

        };
    };

	public FieldHeader(Context context, View view)
	{
		LogX.e(TAG, "new FieldHeader");

        mContext = context;
        mViewHeader = view;
        initView();

		init();
	}

    private void initView()
    {
        mTextView_lastDate = (TextView) mViewHeader.findViewById(R.id.last_data_text);
        mTextView_lastLottery = (TextView) mViewHeader.findViewById(R.id.last_lottery_text);
    }

	private void init()
	{

	}

    private void updataLottery()
    {
        String date = getLastData();
    }

    private String getLastData()
    {
        String date = getDate(0);
        int weekday = getWeekday(date);

        switch (weekday)
        {
            case 2://一
            case 4://三
            case 6://五
                date = getDate(-1);
                break;
            case 7://六
                date = getDate(-2);
                break;
        }

        return date;
    }

    private String getDate(int value)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,   value);
        String date = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
        return date;
    }

    public static int getWeekday(String date)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try
        {
            d = sd.parse(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        cal.setTime(d);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek;
    }
}
