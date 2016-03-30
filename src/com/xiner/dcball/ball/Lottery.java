package com.xiner.dcball.ball;

import android.content.Context;
import android.util.Log;

import com.xiner.dcball.data.Common;
import com.xiner.game.util.AnalysisXML;
import com.xiner.game.util.LotteryManager;

public class Lottery
{
	private final String TAG = getClass().getName();
	
	private Context mContext;
	
	private String mPath_game;
	
	private String mPath_lottery;
	
	private String mPath_statistic;

	public Lottery(Context context)
	{
		mContext = context;
		
		initPath();
		
		LotteryManager.create();

		load();
	}

	private void initPath()
	{
		String path = Common.sExtPath;

		mPath_game = path + "/xGame";
		mPath_lottery = mPath_game + "/lottery";
		mPath_statistic = mPath_game + "/statistic";
	}

	public void load()
	{
		// AnalysisXML analysisXML = new AnalysisXML();

		// analysisXML.importXml("");
	}
}
