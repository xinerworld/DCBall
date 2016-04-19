package com.xiner.dcball.field;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hy on 16-4-15.
 */
public class XPagerAdapter extends PagerAdapter
{
    private ArrayList<View> mListView;

    public XPagerAdapter(ArrayList<View> listView)
    {
        mListView = listView;
    }

    @Override
    public int getCount()
    {
        return mListView.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {  //这个方法用来实例化页卡
        container.addView(mListView.get(position), 0);//添加页卡
        return mListView.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object o)
    {
        return view == o;
//        return false;
    }


}
