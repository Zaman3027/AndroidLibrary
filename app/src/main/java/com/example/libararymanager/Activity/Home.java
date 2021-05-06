package com.example.libararymanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.libararymanager.Fragments.HomeActivity.History;
import com.example.libararymanager.Fragments.HomeActivity.MainHome;
import com.example.libararymanager.Fragments.HomeActivity.Profile;
import com.example.libararymanager.Model.BookModel;
import com.example.libararymanager.Model.UserModel;
import com.example.libararymanager.R;
import com.example.libararymanager.StaticData.FirebaseSaticData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CardView cardView;
    ProgressDialog progressDialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseSaticData.BookModelList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait till we do things for you");
        progressDialog.show();
        bottomNavigationView = findViewById(R.id.bottomMenuMain);
        cardView = findViewById(R.id.adminPanelcard);
        db = FirebaseFirestore.getInstance();
        fetchUserDetail();


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminActivity.class));
            }
        });
        fetchBook();




        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new MainHome()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (menuItem.getItemId()==R.id.main_home){
                    fragmentTransaction.replace(R.id.main_frame,new MainHome());

                }else if (menuItem.getItemId()==R.id.main_history){
                    fragmentTransaction.replace(R.id.main_frame,new History());
                }else if (menuItem.getItemId()==R.id.main_user){
                    fragmentTransaction.replace(R.id.main_frame,new Profile());
                }

                fragmentTransaction.commit();

                return true;
            }
        });
    }

    private void fetchUserDetail() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseSaticData.cuid = auth.getUid();
        db.collection(FirebaseSaticData.user)
                .document(FirebaseSaticData.cuid)
                .collection(FirebaseSaticData.userData)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot : task.getResult()){
                        FirebaseSaticData.userModel = documentSnapshot.toObject(UserModel.class);
                        if (FirebaseSaticData.userModel!=null){
                            if (FirebaseSaticData.userModel.isAdmin()){
                                cardView.setVisibility(View.VISIBLE);
                            }else {
                                cardView.setVisibility(View.GONE);
                            }
                        }

                    }
                }
            }
        });
    }

    void fetchBook() {
        db.collection(FirebaseSaticData.BOOK)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.i("BOOKDATABASE", task.getResult().size() + " Size of book");
                    if (FirebaseSaticData.BookModelList == null) {
                        FirebaseSaticData.BookModelList = new ArrayList<>();
                    } else {
                        FirebaseSaticData.BookModelList.clear();
                    }
                    for (QueryDocumentSnapshot dataSnapsort : task.getResult()) {
                        BookModel bookModel = dataSnapsort.toObject(BookModel.class);
                        Log.i("BOOKDATABASE", bookModel.getBookName());
                        FirebaseSaticData.BookModelList.add(bookModel);


                    }
                    MainHome.newArrivalAdapter.notifyDataSetChanged();
                    MainHome.topRatedAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount()>0){
           getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
