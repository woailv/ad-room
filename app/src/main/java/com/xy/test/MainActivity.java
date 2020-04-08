package com.xy.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.os.Bundle;
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
        final UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new insertAsyncTask(test.userDao()).execute(new User("a"));
                userViewModel.insert(new User("userDef"));
            }
        });
        userViewModel.getUserLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                String s = "";
                for (User user : users) {
                    s += "," + user.name;
                }
                textView.setText(String.valueOf(s.length()));
            }
        });
    }
}
