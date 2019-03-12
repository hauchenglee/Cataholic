package com.example.dong.cataholic;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {
    @TypeConverter
    public String fromSmallCatList(List<SmallCat> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<SmallCat>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public List<SmallCat> toSmallCatList(String smallCatString) {
        if (smallCatString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<SmallCat>>() {
        }.getType();
        List<SmallCat> productCategoriesList = gson.fromJson(smallCatString, type);
        return productCategoriesList;
    }

}
