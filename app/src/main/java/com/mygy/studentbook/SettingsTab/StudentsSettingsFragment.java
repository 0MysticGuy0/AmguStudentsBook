package com.mygy.studentbook.SettingsTab;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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

import com.mygy.studentbook.Adapters.StudentRecyclerAdapter;
import com.mygy.studentbook.Data.Student;
import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.StudentGroupCreator;
import com.mygy.studentbook.Data.UserCreator;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.Data.Utilites.DataBaseHelper;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class StudentsSettingsFragment extends Fragment {

    public static StudentGroup group;
    private StudentRecyclerAdapter adapter;
    private List<Student> studentsToShow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_students_settings, container, false);

        ImageButton backBtn = view.findViewById(R.id.studentsSettings_backBtn);
        backBtn.setOnClickListener(v -> MainTabActivity.replaceFragment(getActivity(),new GroupSettingsFragment()));

        if(group != null){
            studentsToShow = group.getStudents();

            RecyclerView recycler = view.findViewById(R.id.studentsSettings_studentsRecycler);
            adapter = new StudentRecyclerAdapter(this.getContext(),studentsToShow);
            recycler.setAdapter(adapter);

            ImageButton addBtn = view.findViewById(R.id.studentsSettings_addBtn);
            addBtn.setOnClickListener(v -> {
                showAddStudentWindow();
            });

            EditText searchET = view.findViewById(R.id.studentsSettings_search);
            searchET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String txt = searchET.getText().toString();
                    if(txt.length() == 0){
                        studentsToShow = group.getStudents();
                    }else{
                        studentsToShow = group.getStudents()
                                .stream()
                                .filter(st -> st.getFio().toLowerCase().contains((CharSequence) txt.toLowerCase()))
                                .collect(Collectors.toList());
                    }
                    adapter.setSource(studentsToShow);
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });

        }else{
            throw new RuntimeException();
        }
        return view;
    }

    private void showAddStudentWindow(){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this.getContext());
        final View addStudentView = getLayoutInflater().inflate(R.layout.dialog_add_student,null);

        EditText fioET = addStudentView.findViewById(R.id.addStudent_FIO);

        TextView groupNameTV = addStudentView.findViewById(R.id.addStudent_groupName);
        groupNameTV.setText(group.getName());

        Date[] selectedDate = new Date[1];
        selectedDate[0] = new Date();
        TextView birthDateTV = addStudentView.findViewById(R.id.addStudent_birthDate);
        birthDateTV.setText(Constants.dateFormat.format(selectedDate[0]));

        ImageButton birthDateBtn = addStudentView.findViewById(R.id.addStudent_birthDateBtn);
        birthDateBtn.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog dpd = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    selectedDate[0] = new Date(year - 1900, month, dayOfMonth);
                    birthDateTV.setText(Constants.dateFormat.format(selectedDate[0]));
                }
            }, year, month, day);
            dpd.show();
        });

        ImageButton cancel = addStudentView.findViewById(R.id.addStudent_cancelBtn);
        ImageButton add = addStudentView.findViewById(R.id.addStudent_addBtn);

        a_builder.setView(addStudentView);
        AlertDialog dialog = a_builder.create();
        dialog.show();

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        add.setOnClickListener(v -> {
            String fio = fioET.getText().toString();

            if( fio.length()==0 ) {
                Toast.makeText(this.getContext(),"Введите ФИО!",Toast.LENGTH_SHORT).show();
                return;
            }

            UserCreator.createStudent(fio,selectedDate[0],group);
            adapter.notifyDataSetChanged();
            Toast.makeText(this.getContext(),"Студент успешно создан",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }
}