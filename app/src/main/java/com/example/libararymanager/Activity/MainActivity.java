package com.example.libararymanager.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.libararymanager.Fragments.MainActivity.Login;
import com.example.libararymanager.Fragments.MainActivity.Register;
import com.example.libararymanager.Fragments.MainActivity.SplashScreen;
import com.example.libararymanager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationMenu = findViewById(R.id.bottomMenuHome);
        getSupportFragmentManager().beginTransaction().replace(R.id.loginFrame,new SplashScreen()).commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser()==null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.loginFrame, new Login()).commit();
                    bottomNavigationMenu.setVisibility(View.VISIBLE);
                }else {
                    startActivity(new Intent(getApplicationContext(),Home.class));
                    finish();
                }
            }
        }, 3000);

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();

                if (menuItem.getItemId()==R.id.loginHome){
                    fragmentManager.replace(R.id.loginFrame,new Login());
                }else if (menuItem.getItemId()==R.id.registerHome){
                    fragmentManager.replace(R.id.loginFrame,new Register());
                }
                fragmentManager.commit();
                return true;
            }
        });

    }
}
