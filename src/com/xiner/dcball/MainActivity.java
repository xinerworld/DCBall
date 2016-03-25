package com.xiner.dcball;

import com.xiner.dcball.ball.Lottery;
import com.xiner.dcball.field.FieldContent;
import com.xiner.dcball.field.FieldFoot;
import com.xiner.dcball.field.FieldHeader;
import com.xiner.dcball.util.LogX;
import com.xiner.dcball.util.Screen;
import com.xiner.game.util.LotteryManager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

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
	
	public Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_layout);
		
		new Screen(this);
		
		init();
		
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

        mLottery = new Lottery(this);

		mFieldHeader = new FieldHeader(this, mViewHeader);
        mFieldContent = new FieldContent(this, mViewContent);
        mFieldFoot = new FieldFoot(this, mViewFoot);
	}
	
	/**
	 * load layout
	 */
	private void initLayout()
	{
		mViewHeader = findViewById(R.id.main_header_relative);
		
		mViewContent = findViewById(R.id.main_show_content);
		
		mViewFoot = findViewById(R.id.main_foot_relative);
	}
}
