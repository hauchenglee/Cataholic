package com.example.dong.cataholic.webtask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dong.cataholic.DataBase;
import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.dao.StatusDao;
import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.model.StatusBean;

public class LoadTask extends AsyncTask<MyTaskParams, Void, StatusBean> {
    @Override
    protected StatusBean doInBackground(MyTaskParams... myTaskParams) {
//        System.out.println("LoadTask");
        StatusBean statusBean = null;
        try {
            String url = myTaskParams[0].getUrl();
            MemberBean memberBean = (MemberBean) myTaskParams[0].getObject();
            MainActivity mainActivity = myTaskParams[0].getMainActivity();

            StatusDao statusDao = DataBase
                    .getInstance(mainActivity)
                    .getStatusDao();

            statusBean = statusDao.getStatusByMemberKey(memberBean.getMemberKey());

            System.out.println("Old status: " + statusBean.getEndTime());


            return statusBean;
        } catch (Exception e) {
            Log.e("load", e.getMessage());
        }

        return statusBean;
    }

}
