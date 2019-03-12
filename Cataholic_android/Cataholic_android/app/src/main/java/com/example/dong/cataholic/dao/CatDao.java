package com.example.dong.cataholic.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dong.cataholic.model.CatBean;
import com.example.dong.cataholic.model.MemberBean;

import java.util.List;

@Dao
public interface CatDao {

    @Query("SELECT * FROM catbean")
    CatBean[] getAllCats();

    @Query("SELECT * FROM catbean WHERE memberKey=:memberKey")
    CatBean[] getCatsByMemberKey(Integer memberKey);

    @Insert
    void insert(CatBean... catBeans);

    @Update
    void update(CatBean... catBeans);

    @Delete
    void delete(CatBean... catBeans);

    @Query("DELETE FROM catbean")
    void deleteTable();
}
