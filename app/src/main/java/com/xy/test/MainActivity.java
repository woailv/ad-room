package com.xy.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserDatabase test;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "test").build();
        textView = findViewById(R.id.textView);
        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new insertAsyncTask(test.userDao()).execute(new User("a"));
            }
        });
        findViewById(R.id.button_list_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new loadAllAsyncTask(test.userDao(),textView).execute();
            }
        });
    }

    private static class loadAllAsyncTask extends AsyncTask<Void, Void, List<User>> {
        UserDao userDao;
        TextView textView;

        public loadAllAsyncTask(UserDao userDao, TextView textView) {
            this.userDao = userDao;
            this.textView = textView;
        }

        public loadAllAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.loadAll();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            String s = "";
            for (User user : users) {
                s += "," + user.name;
            }
            textView.setText(s);
        }
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        UserDao userDao;

        public insertAsyncTask(UserDao userDao) {
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
