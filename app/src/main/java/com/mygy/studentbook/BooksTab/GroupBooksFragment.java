package com.mygy.studentbook.BooksTab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.User;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;

public class GroupBooksFragment extends Fragment {
    private static User user;
    public static StudentGroup group;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_books, container, false);

        user = MainTabActivity.user;
        if(user != null){
            if(user.getUserType() == User.UserType.ADMIN){
                ImageButton backBtn = view.findViewById(R.id.groupBooks_backBtn);
                backBtn.setVisibility(View.VISIBLE);
                backBtn.setOnClickListener(v -> MainTabActivity.replaceFragment(this.getActivity(), new AdminBooksFragment()) );
            }

            if(group != null){
                TextView groupNameTV = view.findViewById(R.id.groupBooks_groupName);
                groupNameTV.setText(group.getName());

                Button daysBookBtn = view.findViewById(R.id.groupBooks_daysBookBtn);
                daysBookBtn.setOnClickListener(v -> {
                    DaysBookFragment.group = group;
                    MainTabActivity.replaceFragment(this.getActivity(),new DaysBookFragment());
                });

            }else{
                throw new RuntimeException();
            }
        }else{
            throw new RuntimeException();
        }

        return view;
    }
}