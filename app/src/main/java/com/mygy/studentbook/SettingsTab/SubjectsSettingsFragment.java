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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.mygy.studentbook.Adapters.StudentRecyclerAdapter;
import com.mygy.studentbook.Adapters.SubjectRecyclerAdapter;
import com.mygy.studentbook.Data.Student;
import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.StudentGroupCreator;
import com.mygy.studentbook.Data.Subject;
import com.mygy.studentbook.Data.SubjectCreator;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SubjectsSettingsFragment extends Fragment {

    public static StudentGroup group;
    private SubjectRecyclerAdapter adapter;
    private List<Subject> subjectsToShow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subjects_settings, container, false);

        ImageButton backBtn = view.findViewById(R.id.subjectsSettings_backBtn);
        backBtn.setOnClickListener(v -> MainTabActivity.replaceFragment(getActivity(),new GroupSettingsFragment()));

        if(group != null){
            subjectsToShow = group.getSubjects();

            RecyclerView recycler = view.findViewById(R.id.subjectsSettings_subjectsRecycler);
            adapter = new SubjectRecyclerAdapter(this.getContext(),subjectsToShow);
            recycler.setAdapter(adapter);

            ImageButton addBtn = view.findViewById(R.id.subjectsSettings_addBtn);
            addBtn.setOnClickListener(v -> {
                showAddSubjectWindow();
            });

            EditText searchET = view.findViewById(R.id.subjectsSettings_search);
            searchET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String txt = searchET.getText().toString().toLowerCase();
                    if(txt.length() == 0){
                        subjectsToShow = group.getSubjects();
                    }else{
                        subjectsToShow = group.getSubjects()
                                .stream()
                                .filter(sbjct -> sbjct.getName().toLowerCase().contains(txt))
                                .collect(Collectors.toList());
                    }
                    adapter.setSource(subjectsToShow);
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

    private void showAddSubjectWindow(){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this.getContext());
        final View addSubjectView = getLayoutInflater().inflate(R.layout.dialog_add_subject,null);

        EditText nameET = addSubjectView.findViewById(R.id.addSubject_name);

        TextView groupTV = addSubjectView.findViewById(R.id.addSubject_groupName);
        groupTV.setText(group.getName());

        Date[] selectedDates = new Date[2];
        selectedDates[0] = new Date();
        selectedDates[1] = new Date();
        TextView startDateTxt = addSubjectView.findViewById(R.id.addSubject_startDate);
        TextView endDateTxt = addSubjectView.findViewById(R.id.addSubject_endDate);

        startDateTxt.setText(Constants.dateFormat.format(selectedDates[0]));
        endDateTxt.setText(Constants.dateFormat.format(selectedDates[1]));

        ImageButton startDateBtn = addSubjectView.findViewById(R.id.addSubject_startDateBtn);
        startDateBtn.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog dpd = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    selectedDates[0] = new Date(year - 1900, month, dayOfMonth);
                    startDateTxt.setText(Constants.dateFormat.format(selectedDates[0]));
                }
            }, year, month, day);
            dpd.show();
        });
        ImageButton endDateBtn = addSubjectView.findViewById(R.id.addSubject_endDateBtn);
        endDateBtn.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog dpd = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    selectedDates[1] = new Date(year - 1900, month, dayOfMonth);
                    endDateTxt.setText(Constants.dateFormat.format(selectedDates[1]));
                }
            }, year, month, day);
            dpd.show();
        });

        NumberPicker[] daysCountNP = new NumberPicker[6];
        daysCountNP[0] = addSubjectView.findViewById(R.id.addSubject_dayCount_1);
        daysCountNP[1] = addSubjectView.findViewById(R.id.addSubject_dayCount_2);
        daysCountNP[2] = addSubjectView.findViewById(R.id.addSubject_dayCount_3);
        daysCountNP[3] = addSubjectView.findViewById(R.id.addSubject_dayCount_4);
        daysCountNP[4] = addSubjectView.findViewById(R.id.addSubject_dayCount_5);
        daysCountNP[5] = addSubjectView.findViewById(R.id.addSubject_dayCount_6);

        for(NumberPicker np:daysCountNP){
            np.setMinValue(0);
            np.setMaxValue(5);
            np.setWrapSelectorWheel(false);
        }

        ImageButton cancel = addSubjectView.findViewById(R.id.addSubject_cancelBtn);
        ImageButton add = addSubjectView.findViewById(R.id.addSubject_addBtn);

        a_builder.setView(addSubjectView);
        AlertDialog dialog = a_builder.create();
        dialog.show();

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        add.setOnClickListener(v -> {
            String name = nameET.getText().toString();
            Integer[] nums = new Integer[6];
            for(int i = 0; i < daysCountNP.length; i++){
                nums[i] = daysCountNP[i].getValue();
            }

            if( nameET.length()==0 ) {
                Toast.makeText(this.getContext(),"Введите название предмета!",Toast.LENGTH_SHORT).show();
                return;
            }

            SubjectCreator.createSubject(name,nums,selectedDates[0],selectedDates[1],group);
            adapter.notifyDataSetChanged();
            Toast.makeText(this.getContext(),"Группа успешно создана",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }
}