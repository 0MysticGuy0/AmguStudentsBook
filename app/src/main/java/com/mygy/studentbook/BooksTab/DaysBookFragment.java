package com.mygy.studentbook.BooksTab;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.common.util.ArrayUtils;
import com.mygy.studentbook.Data.Note;
import com.mygy.studentbook.Data.NoteCreator;
import com.mygy.studentbook.Data.Student;
import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.Subject;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.Data.Utilites.DataBaseHelper;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class DaysBookFragment extends Fragment {

    public static StudentGroup group;
    private LocalDate selectedDate;
    private Button dateBtn;
    private TableLayout table;
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy\nEEE", new Locale("ru","RU"));
    private Date selectedDate_DATE ;
    private HashMap<Subject, Integer> emptySubjectsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_days_book, container, false);

        if(group != null){

            ImageButton backBtn = view.findViewById(R.id.daysBook_backBtn);
            backBtn.setOnClickListener(v -> MainTabActivity.replaceFragment(this.getActivity(), new GroupBooksFragment()));

            selectedDate = LocalDate.now();
            selectedDate_DATE = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            emptySubjectsList = new HashMap<>(0);

            TextView groupNameTV = view.findViewById(R.id.daysBook_groupName);
            groupNameTV.setText(group.getName());

            dateBtn = view.findViewById(R.id.daysBook_date);
            table = view.findViewById(R.id.daysBook_table);

            dateBtn.setOnClickListener(v -> {
                showSelectDateWindow();
            });

            ImageButton nextDayBtn = view.findViewById(R.id.daysBook_nextDay);
            nextDayBtn.setOnClickListener(v -> {
                selectedDate = selectedDate.plusDays(1);
                selectedDate_DATE = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                updateData();
            });

            ImageButton lastDayBtn = view.findViewById(R.id.daysBook_lastDay);
            lastDayBtn.setOnClickListener(v -> {
                selectedDate = selectedDate.minusDays(1);
                selectedDate_DATE = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                updateData();
            });

            updateData();

        }else{
            throw new RuntimeException();
        }

        return view;
    }

    private void updateData(){
        dateBtn.setText(dateFormat.format(selectedDate));
        updateTableData();
    }
    private void updateTableData(){
        table.removeAllViews();

        int dayOfWeekIndx = selectedDate.getDayOfWeek().ordinal();

        //получаю спискок предметов в выбранный день и кол-во каждого
        Map<Subject,Integer> subjects = (dayOfWeekIndx == 6) ? emptySubjectsList : group.getSubjects()
                        .stream()
                .filter(s -> !s.getStartDate().after(selectedDate_DATE) && !s.getEndDate().before(selectedDate_DATE))
                .filter(s -> s.getCountPerDays()[dayOfWeekIndx] > 0)
                .collect(Collectors.toMap(e -> e, e -> e.getCountPerDays()[dayOfWeekIndx]));

        //строка с предметами
        TableRow subjectsRow = new TableRow(this.getContext());
        TextView emptyTV = new TextView(this.getContext());
        emptyTV.setText("");
        emptyTV.setBackgroundResource(R.drawable.table_element);
        subjectsRow.addView(emptyTV,new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));

        for(Map.Entry<Subject,Integer> sbjct : subjects.entrySet()){
            TableRow.LayoutParams prms = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
            prms.span = sbjct.getValue();

            TextView sbjctNameTV = new TextView(this.getContext());
            sbjctNameTV.setText(sbjct.getKey().getName());
            sbjctNameTV.setTypeface(Typeface.DEFAULT_BOLD);
            sbjctNameTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            sbjctNameTV.setBackgroundResource(R.drawable.table_element);

            subjectsRow.addView(sbjctNameTV, prms);
        }
        table.addView(subjectsRow);

        //строки со студентами
        TableRow.LayoutParams namesLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
        for(Student student:group.getStudents()){
            TableRow row = new TableRow(this.getContext());

            TextView stNameTv = new TextView(this.getContext());
            stNameTv.setText(student.getFio());
            stNameTv.setTypeface(Typeface.DEFAULT_BOLD);
            stNameTv.setBackgroundResource(R.drawable.table_element);
            stNameTv.setLayoutParams(namesLayoutParams);

            row.addView(stNameTv);
            for(Map.Entry<Subject,Integer> sbjct : subjects.entrySet()){
                for(int i = 0; i < sbjct.getValue(); i++){
                    row.addView(createStudentMarkSpinner(student,sbjct.getKey(),i+1));
                }
            }

            table.addView(row);
        }
    }

    private Spinner createStudentMarkSpinner(Student student, Subject subject, int subjectNum){
        Spinner spinner = (Spinner) getLayoutInflater().inflate(R.layout.item_student_mark,null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),android.R.layout.simple_spinner_item, Note.MARKS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final Note note = NoteCreator.createNote(Note.MARKS[0], student,subject,subjectNum,selectedDate_DATE, Constants.IgnoringDatabaseActionListener);

        for(int i = 0; i < Note.MARKS.length; i++){
            if(Note.MARKS[i].equals(note.getMark())) {
                spinner.setSelection(i);
                break;
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!note.getMark().equals(Note.MARKS[position])) {
                    note.setMark(Note.MARKS[position]);
                    if (note.getId() != null)
                        DataBaseHelper.updateNoteDataInBase(note, Constants.IgnoringDatabaseActionListener);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return spinner;
    }
    private void showSelectDateWindow(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dpd = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate =  LocalDate.of(year,month+1,dayOfMonth);
                updateData();
            }
        }, year, month, day);
        dpd.show();
    }
}