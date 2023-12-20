package com.mygy.studentbook.SettingsTab;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mygy.studentbook.Data.Student;
import com.mygy.studentbook.Data.User;
import com.mygy.studentbook.MainTabActivity;
import com.mygy.studentbook.R;


public class StudentSettingsFragment extends Fragment {

    public static Student student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_settings, container, false);

        ImageButton backBtn = view.findViewById(R.id.studentSettings_backBtn);
        backBtn.setOnClickListener(v -> MainTabActivity.replaceFragment(getActivity(),new StudentsSettingsFragment()) );

        if(student != null){

            TextView fioTV = view.findViewById(R.id.studentSettings_fio);
            fioTV.setText(student.getFio());

            TextView ageTV = view.findViewById(R.id.studentSettings_ageInfo);
            ageTV.setText(student.getAge() + " лет");

            TextView groupTV = view.findViewById(R.id.studentSettings_groupName);
            groupTV.setText(student.getStudentGroup().getName());

            TextView idTV = view.findViewById(R.id.studentSettings_id);
            idTV.setText(student.getId());

            TextView passwdTV = view.findViewById(R.id.studentSettings_password);
            passwdTV.setText(student.getPassword());

            ImageView headmanStarIV = view.findViewById(R.id.studentSettings_HeadManStar);

            Button makeHeadmanBtn = view.findViewById(R.id.studentSettings_makeHeadmanBtn);
            if(student.getUserType() == User.UserType.HEADMAN){
                makeHeadmanBtn.setVisibility(View.GONE);
                headmanStarIV.setVisibility(View.VISIBLE);
            }
            makeHeadmanBtn.setOnClickListener(v -> {
                student.makeHeadman();
                headmanStarIV.setVisibility(View.VISIBLE);
                makeHeadmanBtn.setVisibility(View.GONE);
            });

            ImageButton copyLoginDataBtn = view.findViewById(R.id.studentSettings_copyBtn);
            copyLoginDataBtn.setOnClickListener(v -> {
                copyLoginData();
            });

            ImageButton shareLoginDataBtn = view.findViewById(R.id.studentSettings_shareBtn);
            shareLoginDataBtn.setOnClickListener(v -> {
                shareLoginData();
            });

        }else{
            throw new RuntimeException();
        }

        return view;
    }

    private void copyLoginData(){
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("данные для входа",getStudentLoginData());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this.getContext(),"Скопировано",Toast.LENGTH_SHORT).show();
    }
    private void shareLoginData(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //intent.setPackage(packageName); //имя пакета приложения

        intent.putExtra(Intent.EXTRA_TEXT, getStudentLoginData()); // текст отправки
        startActivity(Intent.createChooser(intent, "Поделиться с"));
    }
    private static String getStudentLoginData(){
        return student==null? null :
                String.format("Данные для входа в дневник посещений АмГУ для \"%s\":\nid: %s\nпароль: %s"
                        ,student.getFio(),student.getId(),student.getPassword());
    }

}