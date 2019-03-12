package com.example.dong.cataholic.webtask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dong.cataholic.DataBase;
import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.dao.CatDao;
import com.example.dong.cataholic.model.CatBean;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ShopCatTask extends AsyncTask<MyTaskParams, Void, CatBean[]> {
    @Override
    protected CatBean[] doInBackground(MyTaskParams... myTaskParams) {
        CatBean[] catBeans = null;
        String url = myTaskParams[0].getUrl();
        MainActivity mainActivity = myTaskParams[0].getMainActivity();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(null, headers);
        try {
            catBeans = restTemplate.postForObject(url, request, CatBean[].class);
        } catch (Exception e) {
            Log.e("test", e.getMessage());
        }
        CatDao catDao = DataBase
                .getInstance(mainActivity)
                .getCatDao();

        if (catBeans == null) {
            catBeans = catDao.getAllCats();
            if (catBeans == null) {
                return null;
            } else {
                return catBeans;
            }
        } else {
            catDao.deleteTable();
            catDao.insert(catBeans);
            catBeans = catDao.getAllCats();
            return catBeans;
        }

    }

}
