package com.example.libararymanager.Fragments.MainActivity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.libararymanager.Activity.Home;
import com.example.libararymanager.Model.UserModel;
import com.example.libararymanager.R;
import com.example.libararymanager.StaticData.FirebaseSaticData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {


    public Register() {
        // Required empty public constructor
    }

    ProgressDialog progressDialog;
    Button registerButton;
    TextInputEditText emailText , passwordText , nameText,phoneText;
    FirebaseAuth firebaseAuth;
    TextInputLayout nameInput,emailInput,passwordInput,phoneInput;
    FirebaseFirestore db;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getContext());
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerButton = getView().findViewById(R.id.registerButton);
        emailText = getView().findViewById(R.id.registerEmail);
        passwordText = getView().findViewById(R.id.registerPassword);
        nameText = getView().findViewById(R.id.registerName);
        nameInput = getView().findViewById(R.id.registerNameInput);
        emailInput = getView().findViewById(R.id.registerEmailInput);
        passwordInput = getView().findViewById(R.id.registerPasswordInput);
        phoneText = getView().findViewById(R.id.registerPhone);
        phoneInput = getView().findViewById(R.id.registerPhoneInput);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameText.getText().toString().isEmpty()){
                    nameInput.setError("Enter Name");
                }
                else if(phoneText.getText().toString().isEmpty()){
                    phoneInput.setError("Enter Phone Number");

                } else if (emailText.getText().toString().isEmpty()){
                    emailInput.setError("Enter Email");
                }else if (passwordText.getText().toString().isEmpty()){
                    passwordInput.setError("Enter Password");
                }else {
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(nameText.getText().toString())
                                                .build();
                                        firebaseAuth.getCurrentUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    db.collection(FirebaseSaticData.user)
                                                            .document(firebaseAuth.getUid())
                                                            .collection(FirebaseSaticData.userData)
                                                            .add(new UserModel(
                                                                    firebaseAuth.getUid(),
                                                                    nameText.getText().toString(),
                                                                    phoneText.getText().toString(),
                                                                    Timestamp.now(),
                                                                    false
                                                            )).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                                            if (task.isSuccessful()){
                                                                progressDialog.dismiss();
                                                                startActivity(new Intent(getContext(), Home.class));
                                                            }
                                                        }
                                                    });


                                                }
                                            }
                                        });

                                    }else {
                                        progressDialog.dismiss();
                                        new AlertDialog.Builder(getContext())
                                                .setMessage("Failed")
                                                .create()
                                                .show();
                                    }
                                }
                            });

                }
            }
        });


        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameInput.isErrorEnabled()){
                    nameInput.setErrorEnabled(false);
                }
            }
        });
        emailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailInput.isErrorEnabled()){
                    emailInput.setErrorEnabled(false);
                }
            }
        });

        passwordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordInput.isErrorEnabled()){
                    passwordInput.setErrorEnabled(false);
                }
            }
        });



    }
}
