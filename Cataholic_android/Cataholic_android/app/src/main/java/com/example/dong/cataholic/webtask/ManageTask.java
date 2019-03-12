package com.example.dong.cataholic.webtask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.model.CatBean;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ManageTask extends AsyncTask<MyTaskParams, Void, CatBean[]> {
    @Override
    protected CatBean[] doInBackground(MyTaskParams... myTaskParams) {
        try {
            String url = myTaskParams[0].getUrl();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            MemberBean memberBean = (MemberBean) myTaskParams[0].getObject();


            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/json");


            HttpEntity<MemberBean> request = new HttpEntity<>(memberBean, headers);

            CatBean[] catBeans = restTemplate.postForObject(url, request, CatBean[].class);
            return catBeans;
        } catch (Exception e) {
            Log.e("test", e.getMessage());
        }

        return null;
    }

}
