package com.example.libararymanager.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libararymanager.Fragments.AdminPanel.AdminFragment;
import com.example.libararymanager.R;

public class AdminActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportFragmentManager().beginTransaction().replace(R.id.adminFrame,new AdminFragment()).commit();

    }

}
