package com.xiner.dcball.field;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.xiner.dcball.MainActivity;
import com.xiner.dcball.R;

import android.view.View;
import android.widget.TextView;
import com.xiner.dcball.data.Common;
import com.xiner.dcball.util.LogX;
import com.xiner.game.ball.LotteryStage;
import com.xiner.game.util.LotteryManager;

import java.text.DateFormat;
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

    private LotteryStage mCurLottery = null;

    private final int fCheckTime = 1000 * 60 * 5;

    public final int fUpdataView = 1;

    public final int fCheckDate = 2;

    public Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            int what = msg.what;

            switch (what)
            {
                case fUpdataView:
                    mViewHeader.invalidate();
                    break;
                case fCheckDate:
                    checkDate();
                    break;
            }
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
        checkDate();
	}

    public void updataLottery()
    {
        LotteryStage stage = LotteryManager.getInstance().getLastLotteryStage(Common.LOTTERY_XML_RECORD);

        String calendar = stage.getCalendar();

        String lottery = stage.getDoubleBall().getBall();

        mTextView_lastDate.setText(calendar);

        mTextView_lastLottery.setText(lottery);

        mCurLottery = stage;

        mHandler.sendEmptyMessage(fUpdataView);
    }

    private void checkDate()
    {
        if (mCurLottery != null)
        {
            String lastData = getLastDate();
            String stageData = mCurLottery.getCalendar();

            int flag = compareDate(lastData, stageData);
            boolean isLoadDown = false;
            switch (flag)
            {
                case -1:
                    isLoadDown = true;
                    break;
                case 0:
                    Calendar calendar = Calendar.getInstance();

                    int h = calendar.get(Calendar.HOUR_OF_DAY);
                    int m = calendar.get(Calendar.MINUTE);

                    if (h >= 21 && m >= 30)
                    {
                        isLoadDown = true;
                    }
                break;
                case 1:
                    break;
            }

            if (isLoadDown)
            {
                ((MainActivity)mContext).mHandler.sendEmptyMessage(((MainActivity)mContext).fLoadDown);
            }
            else
            {
                mHandler.sendEmptyMessageDelayed(fCheckDate, fCheckTime);
            }
        }
    }

    private int compareDate(String DATE1, String DATE2)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime())
            {//System.out.println("dt1 在dt2前");
                return 1;
            }
            else if (dt1.getTime() < dt2.getTime())
            {//System.out.println("dt1在dt2后");
                return -1;
            }
            else
            {
                return 0;
            }
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return 0;
    }

    private String getLastDate()
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

    private int getWeekday(String date)
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
