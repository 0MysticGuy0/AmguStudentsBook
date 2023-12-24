package com.mygy.studentbook.SettingsTab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mygy.studentbook.Data.Student;
import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.User;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;

public class GroupSettingsFragment extends Fragment {

    private static User user;
    public static StudentGroup group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_settings, container, false);

        user = MainTabActivity.user;

        if(user != null){

            if(user.getUserType() == User.UserType.ADMIN){
                ImageButton backBtn = view.findViewById(R.id.groupSettings_backBtn);
                backBtn.setVisibility(View.VISIBLE);
                backBtn.setOnClickListener(v -> MainTabActivity.replaceFragment(this.getActivity(),new AdminSettingsFragment()));
            }else{
                group = ((Student) user).getStudentGroup();
            }

            if(group != null){

                TextView nameTV = view.findViewById(R.id.groupSettings_groupName);
                nameTV.setText(group.getName());

                Button studentsBtn = view.findViewById(R.id.groupSettings_studentsBtn);
                studentsBtn.setOnClickListener(v -> {
                    StudentsSettingsFragment.group = group;
                    MainTabActivity.replaceFragment(this.getActivity(),new StudentsSettingsFragment());
                });

                Button subjectsBtn = view.findViewById(R.id.groupSettings_subjectsBtn);
                subjectsBtn.setOnClickListener(v -> {
                    SubjectsSettingsFragment.group = group;
                    MainTabActivity.replaceFragment(this.getActivity(),new SubjectsSettingsFragment());
                });
            }
        }
        return view;
    }
}