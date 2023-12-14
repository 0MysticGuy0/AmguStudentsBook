package com.mygy.studentbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mygy.studentbook.Data.User;
import com.mygy.studentbook.SettingsTab.AdminSettingsFragment;
import com.mygy.studentbook.SettingsTab.GroupSettingsFragment;

public class MainTabActivity extends AppCompatActivity {

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        if(user != null){
            BottomNavigationView bnv=findViewById(R.id.mainTab_bottomNav);
            bnv.setOnItemSelectedListener(item ->{
                if(item.getItemId()==R.id.settingsNavBtn) {
                    Fragment fragment = null;
                    switch (user.getUserType()){
                        case ADMIN: fragment = new AdminSettingsFragment(); break;
                        case HEADMAN: fragment = new GroupSettingsFragment(); break;
                    }
                    replaceFragment(this,fragment);
                }
                return true;
            });


        }else{
            throw new RuntimeException();
        }
    }

    public static void replaceFragment(FragmentActivity activity, Fragment newFragment){
        if(newFragment != null) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFragmentContainerView, newFragment);
            ft.commit();
        }
    }
}