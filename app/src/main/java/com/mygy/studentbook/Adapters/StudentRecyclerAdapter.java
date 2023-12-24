package com.mygy.studentbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mygy.studentbook.Data.Student;
import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.User;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;
import com.mygy.studentbook.SettingsTab.GroupSettingsFragment;
import com.mygy.studentbook.SettingsTab.StudentSettingsFragment;
import com.mygy.studentbook.SettingsTab.StudentsSettingsFragment;

import java.text.SimpleDateFormat;
import java.util.List;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private  List<Student> students;
    public StudentRecyclerAdapter(Context context, List<Student> students) {
        this.students = students;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StudentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentRecyclerAdapter.ViewHolder holder, int position) {
        Student student = students.get(position);

        holder.name.setText(student.getFio());
        holder.ageInfo.setText(student.getAge()+" лет");
        holder.groupName.setText(student.getStudentGroup().getName());
        holder.number.setText((position+1)+".");
        if(student.getUserType() == User.UserType.HEADMAN)
            holder.headmanStar.setVisibility(View.VISIBLE);
        else
            holder.headmanStar.setVisibility(View.GONE);

        holder.root.setOnClickListener(v -> {
            StudentSettingsFragment.student = student;
            MainTabActivity.replaceFragment((FragmentActivity) inflater.getContext(),new StudentSettingsFragment());
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
    public void setSource(List<Student> students){
        this.students = students;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name, ageInfo, groupName, number;
        public final ImageView headmanStar;
        public final ConstraintLayout root;
        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.student_name);
            ageInfo = view.findViewById(R.id.student_ageInfo);
            groupName = view.findViewById(R.id.student_groupName);
            number = view.findViewById(R.id.student_number);
            headmanStar = view.findViewById(R.id.student_HeadManStar);
            root = view.findViewById(R.id.student_root);
        }

    }
}
