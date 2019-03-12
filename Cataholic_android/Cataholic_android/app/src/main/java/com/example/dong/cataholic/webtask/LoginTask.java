package com.example.dong.cataholic.webtask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dong.cataholic.DataBase;
import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.dao.MemberDao;
import com.example.dong.cataholic.model.MemberBean;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class LoginTask extends AsyncTask<MyTaskParams, Void, MemberBean> {
    @Override
    protected MemberBean doInBackground(MyTaskParams... myTaskParams) {
        String url = myTaskParams[0].getUrl();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        MemberBean memberBean = (MemberBean) myTaskParams[0].getObject();
        MainActivity mainActivity = myTaskParams[0].getMainActivity();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<MemberBean> request = new HttpEntity<>(memberBean, headers);

        MemberBean memberBean2 = null;

        try {
            memberBean2 = restTemplate.postForObject(url, request, MemberBean.class);
        } catch (Exception e) {
            Log.e("test", e.getMessage());
        }
        if (memberBean2 == null) {
            MemberDao memberDao = DataBase
                    .getInstance(mainActivity)
                    .getMemberDao();

            memberBean2 = memberDao
                    .getMember(memberBean.getMemberId());

            if (memberBean2 == null) {
                return null;
            } else {
                return memberBean2;
            }
        } else {
            MemberDao memberDao = DataBase
                    .getInstance(mainActivity)
                    .getMemberDao();
            MemberBean memberBean3 = memberDao
                    .getMember(memberBean2.getMemberId());
            if (memberBean3 == null) {
                memberDao.insert(memberBean2);
                memberBean3 = memberDao
                        .getMember(memberBean.getMemberId());
                return memberBean3;
            } else {
                return memberBean3;
            }
        }
    }

}
