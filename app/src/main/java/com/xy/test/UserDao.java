package com.xy.test;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM user")
    LiveData<List<User>> loadAll();

    @Delete
    void delete(User user);
}
