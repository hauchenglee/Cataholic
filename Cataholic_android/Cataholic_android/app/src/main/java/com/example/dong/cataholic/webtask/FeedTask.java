package com.example.dong.cataholic.webtask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dong.cataholic.model.CatMemberBean;
import com.example.dong.cataholic.model.MemberBean;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class FeedTask extends AsyncTask<MyTaskParams, Void, MemberBean> {
    @Override
    protected MemberBean doInBackground(MyTaskParams... myTaskParams) {
        try {
            String url = myTaskParams[0].getUrl();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            CatMemberBean catMemberBean = (CatMemberBean)myTaskParams[0].getObject();

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/json");

            HttpEntity<CatMemberBean> request = new HttpEntity<>(catMemberBean, headers);

            MemberBean memberBean2 = restTemplate.postForObject(url, request, MemberBean.class);

            return memberBean2;
        } catch (Exception e) {
            Log.e("test", e.getMessage());
        }

        return null;
    }

}
