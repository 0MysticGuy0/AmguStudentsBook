package com.mygy.studentbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mygy.studentbook.Data.Student;
import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.User;
import com.mygy.studentbook.Data.UserCreator;
import com.mygy.studentbook.Data.Utilites.DataBaseHelper;
import com.mygy.studentbook.Data.Utilites.PasswordGenerator;
import com.mygy.studentbook.Data.Utilites.UserPasswordGenerator;

public class MainActivity extends AppCompatActivity {
    //5pF7WuixBQQaXp1JW7vl

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        PasswordGenerator passwordGenerator = new UserPasswordGenerator();
        for(int i = 0; i< 10; i++){
            System.out.println(passwordGenerator.generate(6));
        }*/

        Button loginBtn = findViewById(R.id.main_loginBtn);
        loginBtn.setVisibility(View.GONE);

        DataBaseHelper.getAllGroupsFromBase(success -> {//подгружаю группы из базы
            if(success) {
                DataBaseHelper.getAllUsersFromBase(successU -> {//когда родгрузил группы, подгружаю всех рользователей
                    if(successU) {
                        System.out.println("=================\nЗагрузил " + User.getAllUsers().size() + " пользователей\n===========");

                        DataBaseHelper.getAllSubjectsFromBase(successS -> {
                            if(successS){
                                int sum = 0;
                                for(StudentGroup gr:StudentGroup.getAllStudentGroups()){
                                    sum += gr.getSubjects().size();
                                }
                                System.out.println("=================\nЗагрузил " + sum + " предметов\n===========");
                                DataBaseHelper.getAllNotesFromBase(successN -> {
                                    if(successN){
                                        int sumN = 0;
                                        for(Student student:Student.getAllStudents()){
                                            sumN += student.getNotes().size();
                                        }
                                        System.out.println("=================\nЗагрузил " + sumN + " записей\n===========");
                                    }else{
                                        throw new RuntimeException();
                                    }
                                });
                            }else{
                                throw new RuntimeException();
                            }
                        });

                        loginBtn.setVisibility(View.VISIBLE);
                        loginBtn.setOnClickListener(v -> {
                            showLoginWindow();
                        });
                    }else{
                        throw new RuntimeException();
                    }
                });
            }
            else{
                throw new RuntimeException();
            }
        });

    }
    private void showLoginWindow(){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        final View loginView = getLayoutInflater().inflate(R.layout.dialog_login,null);

        EditText idET = loginView.findViewById(R.id.login_id);
        idET.setText("5pF7WuixBQQaXp1JW7vl");
        EditText passwdET = loginView.findViewById(R.id.login_password);

        ImageButton cancel = loginView.findViewById(R.id.login_cancelBtn);
        ImageButton add = loginView.findViewById(R.id.login_loginBtn);

        a_builder.setView(loginView);
        AlertDialog dialog = a_builder.create();
        dialog.show();

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        add.setOnClickListener(v -> {
            String id = idET.getText().toString();
            String passwd = passwdET.getText().toString();

            if( id.length()==0 || passwd.length() == 0) {
                Toast.makeText(getApplicationContext(),"Введите все данные!",Toast.LENGTH_SHORT).show();
                return;
            }

            for(User u:User.getAllUsers()){
                if(u.getId().equals(id) && u.getPassword().equals(passwd)){
                    Toast.makeText(getApplicationContext(),"Успешно вошел в систему",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(this, MainTabActivity.class);
                            MainTabActivity.user = u;
                            startActivity(intent);

                    dialog.dismiss();
                    return;
                }
            }
            Toast.makeText(getApplicationContext(),"Такого пользователя нет в системе!",Toast.LENGTH_SHORT).show();
        });
    }
}