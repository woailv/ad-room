package com.xy.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserDatabase test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "test").build();

        final UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.insert(new User("userDef"));
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final UserListAdapter userListAdapter = new UserListAdapter(userViewModel.getUserLiveData().getValue());
        recyclerView.setAdapter(userListAdapter);
        userViewModel.getUserLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userListAdapter.setUsers(users);
                userListAdapter.notifyDataSetChanged();
            }
        });
    }
}
