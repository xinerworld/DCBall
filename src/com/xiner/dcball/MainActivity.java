package com.xiner.dcball;

import com.xiner.dcball.ball.Lottery;
import com.xiner.dcball.field.FieldContent;
import com.xiner.dcball.field.FieldFoot;
import com.xiner.dcball.field.FieldHeader;
import com.xiner.dcball.util.LogX;
import com.xiner.dcball.util.Screen;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class MainActivity extends Activity
{
	private final String TAG = "MainActivity";
	
	private View mViewHeader;
	
	private View mViewContent;
	
	private View mViewFoot;
	
	private FieldHeader mFieldHeader;
	
	private FieldContent mFieldContent;
	
	private FieldFoot mFieldFoot;
	
	private Lottery mLottery;

    public final int fLoadDate = 1;

    public final int fLoadDateEnd = 2;

    public final int fLoadDown = 3;

    public final int fLoadDownEnd = 4;

    public Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			int w = msg.what;

            switch (w)
            {
                case fLoadDate:
                    initLayout();
                    break;
                case fLoadDateEnd:
                    mFieldHeader.updataLottery();
                    break;
                case fLoadDown:
                    mLottery.loadDown();
                    break;
                case fLoadDownEnd:
                    mLottery.statistic();
                    mLottery.exportXml();
                    updataAll();
                    break;
            }
		};
	};

    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_layout);
		
		new Screen(this);
		
		init();

        mHandler.sendEmptyMessage(fLoadDate);
		
		LogX.e(TAG, "onCreate end");
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
	
	/**
	 * init activity
	 */
	private void init()
	{
		LogX.e(TAG, "create init");

		initLayout();

        //mLottery = new Lottery(this);

		mFieldHeader = new FieldHeader(this, mViewHeader);
        mFieldContent = new FieldContent(this, mViewContent);
        mFieldFoot = new FieldFoot(this, mViewFoot);
	}
	
	/**
	 * loadLocalXML layout
	 */
	private void initLayout()
	{
		mViewHeader = findViewById(R.id.main_header_relative);
		
		mViewContent = findViewById(R.id.main_show_content);
		
		mViewFoot = findViewById(R.id.main_foot_relative);
	}

    private void updataAll()
    {
        mFieldHeader.updataLottery();
    }
}
