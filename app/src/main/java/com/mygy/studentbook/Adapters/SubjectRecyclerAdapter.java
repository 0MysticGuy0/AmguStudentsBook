package com.mygy.studentbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.Subject;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;
import com.mygy.studentbook.SettingsTab.GroupSettingsFragment;

import java.text.SimpleDateFormat;
import java.util.List;

public class SubjectRecyclerAdapter extends RecyclerView.Adapter<SubjectRecyclerAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private  List<Subject> subjects;
    public SubjectRecyclerAdapter(Context context, List<Subject> subjects) {
        this.subjects = subjects;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SubjectRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_subject, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectRecyclerAdapter.ViewHolder holder, int position) {
        Subject subject = subjects.get(position);

        holder.name.setText(subject.getName());
        SimpleDateFormat df = Constants.dateFormat;
        holder.dateInfo.setText(String.format("%s - %s",df.format(subject.getStartDate()),df.format(subject.getEndDate())));
        holder.groupName.setText(subject.getGroup().getName());

        holder.root.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
    public void setSource(List<Subject> subjects){
        this.subjects = subjects;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name, dateInfo, groupName;
        public final LinearLayout root;
        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.subject_name);
            dateInfo = view.findViewById(R.id.subject_dateInfo);
            groupName = view.findViewById(R.id.subject_groupName);
            root = view.findViewById(R.id.subject_root);
        }

    }
}
