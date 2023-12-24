package com.mygy.studentbook.BooksTab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mygy.studentbook.Adapters.StudentGroupRecyclerAdapter;
import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.User;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;
import com.mygy.studentbook.SettingsTab.GroupSettingsFragment;

import java.util.List;
import java.util.stream.Collectors;


public class AdminBooksFragment extends Fragment {
    private User user;
    private StudentGroupRecyclerAdapter adapter;
    private List<StudentGroup> groupsToDisplay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_books, container, false);

        user = MainTabActivity.user;
        if(user != null){

            RecyclerView recycler = view.findViewById(R.id.adminBooks_groupsRecycler);
            groupsToDisplay = StudentGroup.getAllStudentGroups();
            adapter = new StudentGroupRecyclerAdapter(this.getContext(), groupsToDisplay, group -> {
                GroupBooksFragment.group = group;
                MainTabActivity.replaceFragment(this.getActivity(), new GroupBooksFragment());
            });
            recycler.setAdapter(adapter);

            EditText searchET = view.findViewById(R.id.adminBooks_search);
            searchET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String txt = searchET.getText().toString();
                    if(txt.length() == 0){
                        groupsToDisplay = StudentGroup.getAllStudentGroups();
                    }else{
                        groupsToDisplay = StudentGroup.getAllStudentGroups()
                                .stream()
                                .filter(g -> g.getName().toLowerCase().contains((CharSequence) txt.toLowerCase()))
                                .collect(Collectors.toList());
                    }
                    adapter.setSource(groupsToDisplay);
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });

        }else{
            throw  new RuntimeException();
        }

        return  view;
    }
}