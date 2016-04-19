package com.xiner.dcball.field;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiner.dcball.R;
import com.xiner.dcball.ball.Lottery;
import com.xiner.game.ball.DoubleBall;
import com.xiner.game.ball.LotteryStage;

import java.util.List;
import java.util.Map;

/**
 * Created by hy on 16-4-15.
 */
public class XHistoryListAdapter extends BaseAdapter
{
    private Context mContext;

    private List<Map.Entry<String, LotteryStage>> mListStage;

    public XHistoryListAdapter(Context context, List<Map.Entry<String, LotteryStage>> list)
    {
        mContext = context;

        mListStage = list;
    }

    @Override
    public int getCount()
    {
        return mListStage.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mListStage.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_list_item, null, false);
        }

        TextView tvDate = ViewHolder.get(convertView, R.id.history_list_item_date);
        TextView tvStage = ViewHolder.get(convertView, R.id.history_list_item_stage);

        RelativeLayout red1 = ViewHolder.get(convertView, R.id.history_list_item_red_1);
        TextView tvRed1 = (TextView) red1.findViewById(R.id.red_ball_text);
        RelativeLayout red2 = ViewHolder.get(convertView, R.id.history_list_item_red_2);
        TextView tvRed2 = (TextView) red2.findViewById(R.id.red_ball_text);
        RelativeLayout red3 = ViewHolder.get(convertView, R.id.history_list_item_red_3);
        TextView tvRed3 = (TextView) red3.findViewById(R.id.red_ball_text);
        RelativeLayout red4 = ViewHolder.get(convertView, R.id.history_list_item_red_4);
        TextView tvRed4 = (TextView) red4.findViewById(R.id.red_ball_text);
        RelativeLayout red5 = ViewHolder.get(convertView, R.id.history_list_item_red_5);
        TextView tvRed5 = (TextView) red5.findViewById(R.id.red_ball_text);
        RelativeLayout red6 = ViewHolder.get(convertView, R.id.history_list_item_red_6);
        TextView tvRed6 = (TextView) red6.findViewById(R.id.red_ball_text);
        RelativeLayout blue = ViewHolder.get(convertView, R.id.history_list_item_blue);
        TextView tvBlue = (TextView) blue.findViewById(R.id.blue_ball_text);

        Map.Entry<String, LotteryStage> entry = (Map.Entry<String, LotteryStage>) getItem(position);
        LotteryStage lottery = entry.getValue();

        String strDate = lottery.getCalendar();
        String strStage = lottery.getStage();
        tvDate.setText(strDate);
        tvStage.setText(strStage);

        DoubleBall ball = lottery.getDoubleBall();
        for (int i = 0; i < 6; i++)
        {
            int nRed = ball.getRedBall(i).m_nBallNum;
            switch (i)
            {
                case 0: tvRed1.setText(String.valueOf(nRed)); break;
                case 1: tvRed2.setText(String.valueOf(nRed)); break;
                case 2: tvRed3.setText(String.valueOf(nRed)); break;
                case 3: tvRed4.setText(String.valueOf(nRed)); break;
                case 4: tvRed5.setText(String.valueOf(nRed)); break;
                case 5: tvRed6.setText(String.valueOf(nRed)); break;
            }
        }

        int nBlue = ball.getBlueBall().m_nBallNum;
        tvBlue.setText(String.valueOf(nBlue));

        return convertView;
    }
}