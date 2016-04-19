package com.xiner.dcball.field;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;

import android.widget.*;
import com.xiner.dcball.R;
import com.xiner.game.ball.LotteryBox;
import com.xiner.game.ball.LotteryContain;
import com.xiner.game.ball.LotteryStage;
import com.xiner.game.util.LotteryManager;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class FieldContent
{
	private final String TAG = getClass().getName();

	private Context mContext;

	// content view
	private View mViewContent;
    // pager view
	private ViewPager mViewPager;
    //history
	private View mLotteryHistory;

	private Spinner mSpinner;

	private List<String> mSpinnerDatalist;

	private ArrayAdapter<String> mSpinnerArrAdapter = null;

	private ListView mHistoryListView;

    private XHistoryListAdapter mHistoryListAdapter = null;
    //statistic
	private View mStatistic;

	private ArrayList<View> mViewPagerList;

	private XPagerAdapter mXpagerAdapter;


    public final int fUpdataHistory = 1;

    public Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            int w = msg.what;

            switch (w)
            {
                case fUpdataHistory:
                    updataHistoryList();
                    break;
            }
        }
    };

	public FieldContent(Context context, View view)
	{
		mContext = context;
		mViewContent = view;

		init();
	}

	private void init()
	{
		mViewPager = (ViewPager) mViewContent.findViewById(R.id.viewpager);

		mLotteryHistory = LayoutInflater.from(mContext).inflate(R.layout.lottery_history, null);
		mSpinner = (Spinner) mLotteryHistory.findViewById(R.id.list_spinner);
		mHistoryListView = (ListView) mLotteryHistory.findViewById(R.id.history_list);

		mSpinnerDatalist = new ArrayList<String>();

		mStatistic = LayoutInflater.from(mContext).inflate(R.layout.lottery_statistic, null);

		mViewPagerList = new ArrayList<View>();// 将要分页显示的View装入数组中
		mViewPagerList.add(mLotteryHistory);
		mViewPagerList.add(mStatistic);

		mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String y = (String) mSpinner.getItemAtPosition(position);
                Toast.makeText(mContext, y, Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(fUpdataHistory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
	}

	public void updataLottery()
	{
		updataSpinner();
        updataHistoryList();
	}
	
	private void updataSpinner()
	{
		LotteryContain lotteryContain = LotteryManager.getInstance().getLotteryContain(LotteryManager.getInstance().getKEY_Lottery());

		List<Entry<Integer, LotteryBox>> containlist = lotteryContain.getLotteryContainByOrder();

		mSpinnerDatalist.clear();

		int size = containlist.size();
		for (int i = size - 1; i >= 0; i--)
		{
			int key = containlist.get(i).getKey().intValue();
			mSpinnerDatalist.add(String.valueOf(key));
		}

		// 适配器
		if (mSpinnerArrAdapter != null)
		{
			mSpinnerArrAdapter = null;
		}
		mSpinnerArrAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mSpinnerDatalist);
		// 设置样式
		mSpinnerArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 加载适配器
		mSpinner.setAdapter(mSpinnerArrAdapter);
		mSpinner.setSelection(0, true);

        mSpinner.invalidate();
	}

	private void updataHistoryList()
	{
		String spinnerSelected = (String) mSpinner.getSelectedItem();

        LotteryContain lotteryContain = LotteryManager.getInstance().getLotteryContain(LotteryManager.getInstance().getKEY_Lottery());
        LotteryBox lotteryBox = lotteryContain.getLotteryBoxAdd(spinnerSelected);

        List<Entry<String, LotteryStage>> listStage = lotteryBox.getLotteryBoxByOrder();

        if (mHistoryListAdapter != null)
        {
            mHistoryListAdapter = null;
        }
        mHistoryListAdapter  = new XHistoryListAdapter(mContext, listStage);

        mHistoryListView.setAdapter(mHistoryListAdapter);

        mHistoryListView.invalidate();
	}

    public void updataStatistic()
    {

    }
}
