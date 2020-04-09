package com.xy.test;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private List<User> users;
    private UserViewModel userViewModel;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public UserListAdapter(List<User> users, UserViewModel userViewModel) {
        this.users = users;
        this.userViewModel = userViewModel;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        holder.textView.setText(users.get(position).name);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.delete(users.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (users == null) {
            return 0;
        }
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_user_name);
        }
    }
}
