package com.xiner.dcball.util;

import android.util.Log;

/**
 * Created by hy on 16-3-25.
 */
public class LogX
{
    private static boolean bIsOpenDebug = true;

    public static void v(String tag, String msg)
    {
        if (bIsOpenDebug)
        {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr)
    {
        if(bIsOpenDebug)
        {
            Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg)
    {
        if(bIsOpenDebug)
        {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr)
    {
        if(bIsOpenDebug)
        {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg)
    {
        if(bIsOpenDebug)
        {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr)
    {
        if(bIsOpenDebug)
        {
            Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg)
    {
        if(bIsOpenDebug)
        {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr)
    {
        if(bIsOpenDebug)
        {
            Log.w(tag, msg, tr);
        }
    }

    public static void w(String tag, Throwable tr)
    {
        if(bIsOpenDebug)
        {
            Log.w(tag, tr);
        }
    }

    public static void e(String tag, String msg)
    {
        if(bIsOpenDebug)
        {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr)
    {
        if(bIsOpenDebug)
        {
            Log.v(tag, msg, tr);
        }
    }
}
