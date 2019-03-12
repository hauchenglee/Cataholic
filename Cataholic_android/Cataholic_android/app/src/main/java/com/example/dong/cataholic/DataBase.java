package com.example.dong.cataholic;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.dong.cataholic.dao.CatDao;
import com.example.dong.cataholic.dao.MemberDao;
import com.example.dong.cataholic.dao.StatusDao;
import com.example.dong.cataholic.model.CatBean;
import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.model.StatusBean;


@Database(entities = {MemberBean.class, CatBean.class, StatusBean.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    private static final String DB_NAME = "UserDatabase.db";
    private static volatile DataBase instance;

    public static synchronized DataBase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public static DataBase create(final Context context) {
        return Room.databaseBuilder(
                context,
                DataBase.class,
                DB_NAME).build();
    }

    public abstract MemberDao getMemberDao();
    public abstract CatDao getCatDao();
    public abstract StatusDao getStatusDao();

}
