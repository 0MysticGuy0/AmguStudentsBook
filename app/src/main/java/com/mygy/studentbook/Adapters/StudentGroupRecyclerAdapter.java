package com.mygy.studentbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;
import com.mygy.studentbook.SettingsTab.GroupSettingsFragment;

import java.text.SimpleDateFormat;
import java.util.List;

public class StudentGroupRecyclerAdapter extends RecyclerView.Adapter<StudentGroupRecyclerAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private  List<StudentGroup> groups;
    public StudentGroupRecyclerAdapter(Context context, List<StudentGroup> groups) {
        this.groups = groups;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StudentGroupRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentGroupRecyclerAdapter.ViewHolder holder, int position) {
        StudentGroup group = groups.get(position);

        holder.name.setText(group.getName());
        SimpleDateFormat df = Constants.dateFormat;
        holder.dateInfo.setText(String.format("%s - %s",df.format(group.getStartDate()),df.format(group.getEndDate())));

        holder.root.setOnClickListener(v -> {
            GroupSettingsFragment.group = group;
            MainTabActivity.replaceFragment((MainTabActivity)(inflater.getContext()),new GroupSettingsFragment());
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
    public void setSource(List<StudentGroup> groups){
        this.groups = groups;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name, dateInfo;
        public final LinearLayout root;
        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.group_name);
            dateInfo = view.findViewById(R.id.group_dateInfo);
            root = view.findViewById(R.id.group_root);
        }

    }
}
