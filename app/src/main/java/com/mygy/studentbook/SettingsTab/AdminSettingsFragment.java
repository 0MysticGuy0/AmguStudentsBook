package com.mygy.studentbook.SettingsTab;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mygy.studentbook.Adapters.StudentGroupRecyclerAdapter;
import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.StudentGroupCreator;
import com.mygy.studentbook.Data.User;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AdminSettingsFragment extends Fragment {

    private User user;
    private StudentGroupRecyclerAdapter adapter;
    private List<StudentGroup> groupsToDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_settings, container, false);

        user = MainTabActivity.user;
        if (user != null) {

            ImageButton addBtn = view.findViewById(R.id.adminSettings_addBtn);
            addBtn.setOnClickListener(v -> {
                showAddGroupWindow();
            });


            RecyclerView recycler = view.findViewById(R.id.adminSettings_groupsRecycler);
            groupsToDisplay = StudentGroup.getAllStudentGroups();
            adapter = new StudentGroupRecyclerAdapter(this.getContext(), groupsToDisplay, group -> {
                GroupSettingsFragment.group = group;
                MainTabActivity.replaceFragment(this.getActivity(), new GroupSettingsFragment());
            });
            recycler.setAdapter(adapter);

            EditText searchET = view.findViewById(R.id.adminSettings_search);
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

        } else {
            throw new RuntimeException();
        }

        return view;
    }

private void showAddGroupWindow(){
    AlertDialog.Builder a_builder = new AlertDialog.Builder(this.getContext());
    final View addGroupView = getLayoutInflater().inflate(R.layout.dialog_add_group,null);

    EditText nameET = addGroupView.findViewById(R.id.addGroup_name);

    Date[] selectedDates = new Date[2];
    selectedDates[0] = new Date();
    selectedDates[1] = new Date();
    TextView startDateTxt = addGroupView.findViewById(R.id.addGroup_startDate);
    TextView endDateTxt = addGroupView.findViewById(R.id.addGroup_endDate);

    startDateTxt.setText(Constants.dateFormat.format(selectedDates[0]));
    endDateTxt.setText(Constants.dateFormat.format(selectedDates[1]));

    ImageButton startDateBtn = addGroupView.findViewById(R.id.addGroup_startDateBtn);
    startDateBtn.setOnClickListener(v -> {
        DatePickerDialog dpd = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDates[0] = new Date(year - 1900, month, dayOfMonth);
                startDateTxt.setText(Constants.dateFormat.format(selectedDates[0]));
            }
        }, selectedDates[0].getYear()+1900,selectedDates[0].getMonth(), selectedDates[0].getDay());
        dpd.show();
    });
    ImageButton endDateBtn = addGroupView.findViewById(R.id.addGroup_endDateBtn);
    endDateBtn.setOnClickListener(v -> {
        DatePickerDialog dpd = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDates[1] = new Date(year - 1900, month, dayOfMonth);
                endDateTxt.setText(Constants.dateFormat.format(selectedDates[1]));
            }
        }, selectedDates[1].getYear()+1900,selectedDates[1].getMonth(), selectedDates[1].getDay());
        dpd.show();
    });

    ImageButton cancel = addGroupView.findViewById(R.id.addGroup_cancelBtn);
    ImageButton add = addGroupView.findViewById(R.id.addGroup_addBtn);

    a_builder.setView(addGroupView);
    AlertDialog dialog = a_builder.create();
    dialog.show();

    cancel.setOnClickListener(v -> {
        dialog.dismiss();
    });

    add.setOnClickListener(v -> {
        String name = nameET.getText().toString();

        if( nameET.length()==0 ) {
            Toast.makeText(this.getContext(),"Введите все данные!",Toast.LENGTH_SHORT).show();
            return;
        }

        StudentGroupCreator.createStudentGroup(name,selectedDates[0],selectedDates[1]);
        adapter.notifyDataSetChanged();
        Toast.makeText(this.getContext(),"Группа успешно создана",Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    });
}
}