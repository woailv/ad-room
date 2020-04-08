package com.xy.test;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private LiveData<List<User>> userLiveData;
    private UserDao userDao;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userDao = Room.databaseBuilder(application, UserDatabase.class, "test").allowMainThreadQueries().build().userDao();
        userLiveData = userDao.loadAll();
    }

    public LiveData<List<User>> getUserLiveData() {
        return userLiveData;
    }

    public void insert(User user) {
        userDao.insert(user);
    }
}
