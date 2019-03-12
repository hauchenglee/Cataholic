package com.example.dong.cataholic.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dong.cataholic.model.MemberBean;

import java.util.List;

@Dao
public interface MemberDao {

    @Query("SELECT * FROM memberbean")
    List<MemberBean> getAllMembers();

    @Query("SELECT * FROM memberbean WHERE memberId=:id")
    MemberBean getMember(String id);

    @Insert
    void insert(MemberBean... memberBeans);

    @Update
    void update(MemberBean... memberBeans);

    @Delete
    void delete(MemberBean... memberBeans);

    @Query("DELETE FROM memberbean")
    public void deleteTable();
}
