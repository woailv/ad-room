package com.xy.test;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends AndroidViewModel {
    private LiveData<List<User>> userLiveData ;
    private UserDao userDao;
    public UserViewModel(@NonNull Application application) {
        super(application);
        userDao = Room.databaseBuilder(application, UserDatabase.class, "test").build().userDao();
        try {
            userLiveData = new LoadAllAsyncTask(userDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<User>> getUserLiveData() {
        return userLiveData;
    }

    public void insert(User user) {
        new InsertAsyncTask(userDao).execute(user);
    }

    public void delete(User user) {
        new DeleteAsyncTask(userDao).execute(user);
    }

    private static class DeleteAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public DeleteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... voids) {
             userDao.delete(voids[0]);
             return null;
        }
    }

    private static class LoadAllAsyncTask extends AsyncTask<Void, Void, LiveData<List<User>>> {
        private UserDao userDao;

        public LoadAllAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected LiveData<List<User>> doInBackground(Void... voids) {
            return userDao.loadAll();
        }
    }


    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {
        UserDao userDao;

        public InsertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            for (User user : users) {
                userDao.insert(user);
            }
            return null;
        }
    }
}
