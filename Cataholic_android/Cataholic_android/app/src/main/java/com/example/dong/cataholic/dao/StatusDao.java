package com.example.dong.cataholic.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dong.cataholic.model.CatBean;
import com.example.dong.cataholic.model.StatusBean;

@Dao
public interface StatusDao {

    @Query("SELECT * FROM statusbean WHERE memberKey=:memberKey")
    StatusBean getStatusByMemberKey(Integer memberKey);

    @Insert
    void insert(StatusBean... statusBeans);

    @Update
    void update(StatusBean... statusBeans);

    @Delete
    void delete(StatusBean... statusBeans);

    @Query("DELETE FROM statusbean")
    void deleteTable();
}
