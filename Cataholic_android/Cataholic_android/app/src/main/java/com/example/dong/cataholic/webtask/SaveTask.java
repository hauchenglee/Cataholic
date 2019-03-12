package com.example.dong.cataholic.webtask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dong.cataholic.DataBase;
import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.dao.CatDao;
import com.example.dong.cataholic.dao.StatusDao;
import com.example.dong.cataholic.model.StatusBean;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SaveTask extends AsyncTask<MyTaskParams, Void, Integer> {
    @Override
    protected Integer doInBackground(MyTaskParams... myTaskParams) {
//        System.out.println("SaveTask");
        Integer i = null;
        try {
            String url = myTaskParams[0].getUrl();
            StatusBean statusBean = (StatusBean) myTaskParams[0].getObject();
            MainActivity mainActivity = myTaskParams[0].getMainActivity();

            StatusDao statusDao = DataBase
                    .getInstance(mainActivity)
                    .getStatusDao();
            StatusBean statusBean1 = statusDao.getStatusByMemberKey(statusBean.getMemberKey());

//            System.out.println("Old status: " + statusBean1.getPressureStatus());
            if (statusBean1 != null) {
                statusDao.delete(statusBean1);
            }
            statusDao.insert(statusBean);
            i = 1;


            return i;
        } catch (Exception e) {
            Log.e("Save", e.getMessage());
        }

        return i;
    }

}
