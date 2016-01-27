package com.xiner.dcball.util;

import com.xiner.dcball.MainActivity;

import android.content.Context;
import android.util.Log;

public class Screen
{
	private final String TAG = "screen";
	
	private Context mContext;
	
	private static Screen sScreen = null;
	
	private int mWidth;
	private int mHeight;
	private float mDensity;
	
	public Screen(Context context)
	{
		mContext = context;
		
		init();
		
		sScreen = this;
	}
	
	private void init()
	{
		mWidth = ((MainActivity)mContext).getWindowManager().getDefaultDisplay().getWidth();
		mHeight = ((MainActivity)mContext).getWindowManager().getDefaultDisplay().getHeight();
		mDensity = ((MainActivity)mContext).getResources().getDisplayMetrics().density;

		Log.e(TAG, "screenWidth=" + mWidth + " screenHeight=" + mHeight + " dip=" + mDensity);
	}
	
	public static Screen getScreen()
	{
		return sScreen;
	}

	public int getWidth()
	{
		return mWidth;
	}

	public void setWidth(int Width)
	{
		this.mWidth = Width;
	}

	public int getHeight()
	{
		return mHeight;
	}

	public void setHeight(int Height)
	{
		this.mHeight = Height;
	}

	public float getDensity()
	{
		return mDensity;
	}

	public void setDensity(float Density)
	{
		this.mDensity = Density;
	}
	
	
}
