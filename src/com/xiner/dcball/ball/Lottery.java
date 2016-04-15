package com.xiner.dcball.ball;

import android.content.Context;

import android.os.Handler;
import android.os.Message;
import com.xiner.dcball.MainActivity;
import com.xiner.dcball.data.Common;
import com.xiner.dcball.view.XProgressDialog;
import com.xiner.game.ball.LotteryBox;
import com.xiner.game.ball.LotteryContain;
import com.xiner.game.ball.LotteryStage;
import com.xiner.game.ball.LotteryStatistic;
import com.xiner.game.util.AnalysisHtml;
import com.xiner.game.util.AnalysisXML;
import com.xiner.game.util.LotteryManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Lottery
{
    private final String TAG = getClass().getName();

    private Context mContext;

    private String mPath_game;

    private String mPath_lottery;

    private String mPath_statistic;

    //存放目录及其子目录下的所有文件对象
    private List<File> mLstFile = new ArrayList<File>();

    private XProgressDialog mProgressDialog;

    private final int fLoadName = 1;
    private final int fLoadProgress = 2;
    private final int fLoadError = 3;
    private final int fLoadOver = 4;

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            int what = msg.what;
            switch (what)
            {
                case fLoadName:
                    String name = (String) msg.obj;
                    mProgressDialog.setProgressMsg(name);
                    break;
                case fLoadProgress:
                    int progress = msg.arg1;
                    mProgressDialog.setProgress(progress);
                    break;
                case fLoadError:
                    LotteryManager.getInstance().clearContain(LotteryManager.getInstance().getKEY_Lottery());
                    mProgressDialog.dismiss();
                    break;
                case fLoadOver:
                    mProgressDialog.dismiss();
                    ((MainActivity)mContext).mHandler.sendEmptyMessage(((MainActivity)mContext).fLoadDateEnd);
                    break;
            }
        }
    };

    public Lottery(Context context)
    {
        mContext = context;

        initPath();

        mLstFile.clear();
        File file = new File(mPath_game);
        listDirectory(file, mLstFile);

        LotteryManager.create();

        loadLocalXML();
    }

    private void initPath()
    {
        String path = Common.sExtPath;

        mPath_game = path + "/xGame";
        mPath_lottery = mPath_game + "/lottery";
        mPath_statistic = mPath_game + "/statistic";

        File gameFile = new File(mPath_game);
        if  (!gameFile.exists())
        {
            gameFile.mkdir();
        }

        File lotteryFile = new File(mPath_lottery);
        if (!lotteryFile.exists())
        {
            lotteryFile.mkdir();
        }

        File statisticFile = new File(mPath_statistic);
        if (!statisticFile.exists())
        {
            statisticFile.mkdir();
        }
    }

    /**
     * 遍历目录及其子目录下的所有文件并保存
     *
     * @param path   目录全路径
     * @param fileList 列表：保存文件对象
     */
    private void listDirectory(File path, List<File> fileList)
    {
        if (!path.exists())
        {
            System.out.println("文件名称不存在!");
        }
        else
        {
            if (path.isFile())
            {
                fileList.add(path);
            }
            else
            {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    listDirectory(files[i], fileList);
                }
            }
        }
    }

    private void loadLocalXML()
    {
        mProgressDialog = new XProgressDialog();
        mProgressDialog.createProgressDialog(mContext, "Load local data", "");
        mProgressDialog.setProgress(0);
        mProgressDialog.setProgressMax(mLstFile.size());

        LotteryManager.getInstance().setKEY_Lottery(Common.LOTTERY_XML_RECORD);
        LotteryManager.getInstance().setKEY_Statistic(Common.STATISTIC_XML_RECORD);

        AnalysisXML analysisXML = new AnalysisXML();

        for (int i=0; i < mLstFile.size(); i++)
        {
            Message msg = new Message();

            File file = mLstFile.get(i);
            try
            {
                msg.what = fLoadName;
                msg.obj = file.getName();
                mHandler.sendMessage(msg);

                String path = file.getCanonicalPath();
                analysisXML.importXml(path);
            } catch (IOException e)
            {
//                e.printStackTrace();
                msg.what = fLoadError;
                mHandler.sendMessage(msg);
            }

            msg.what = fLoadProgress;
            msg.arg1 = i;
            mHandler.sendMessage(msg);
        }

        mHandler.sendEmptyMessage(fLoadOver);
    }

    public void loadDown()
    {
        AnalysisHtml analysisHtml = new AnalysisHtml();

        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);

        String endStage = "1993001";

        LotteryStatistic lotteryStatistic = LotteryManager.getInstance().getLotteryStatistic(LotteryManager.getInstance().getKEY_Statistic(), true);

        if (lotteryStatistic.getStartStage() == null)
        {
            lotteryStatistic.setStartStage(endStage);
            lotteryStatistic.setEndStage(endStage);
        }
        else
        {
            endStage = lotteryStatistic.getEndStage();
        }

        int endYear = Integer.parseInt(endStage) / 1000;

        for (;endYear <= y; endYear++)
        {
            String strHtml = "http://baidu.lecai.com/lottery/draw/list/50?type=range&start="+endYear+"001&end="+endYear+"160";
            analysisHtml.analysis(strHtml);
        }

        ((MainActivity)mContext).mHandler.sendEmptyMessage(((MainActivity)mContext).fLoadDownEnd);
    }

    public void statistic()
    {
        LotteryContain lotteryContain = LotteryManager.getInstance().getLotteryContain(LotteryManager.getInstance().getKEY_Lottery());
        LotteryStatistic lotteryStatistic = LotteryManager.getInstance().getLotteryStatistic(LotteryManager.getInstance().getKEY_Statistic(), true);

        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int endYear = Integer.parseInt(lotteryStatistic.getEndStage()) / 1000;

        for (;endYear <= y; endYear++)
        {
            LotteryBox box = lotteryContain.getLotteryContain().get(new Integer(endYear));

            LotteryManager.getInstance().lotteryBox2Statistic(box, lotteryStatistic);
        }

        LotteryBox box = lotteryContain.getLotteryContain().get(new Integer(endYear));
        List<Map.Entry<String, LotteryStage>> liststage = box.getLotteryBoxByOrder();
        Map.Entry<String, LotteryStage> entryStage = liststage.get(liststage.size() - 1);
        String stage = entryStage.getKey();

        lotteryStatistic.setEndStage(stage);
    }

    public void exportXml()
    {
        AnalysisXML analysisXML = new AnalysisXML();

        LotteryContain lotteryContain = LotteryManager.getInstance().getLotteryContain(LotteryManager.getInstance().getKEY_Lottery());

        analysisXML.exportXml(mPath_lottery+"/", lotteryContain);

        LotteryStatistic lotteryStatistic = LotteryManager.getInstance().getLotteryStatistic(LotteryManager.getInstance().getKEY_Statistic(), true);

        analysisXML.exportXML(mPath_statistic+"/statistic.xml", lotteryStatistic);
    }
}
