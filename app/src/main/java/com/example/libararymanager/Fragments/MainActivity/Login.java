package com.example.libararymanager.Fragments.MainActivity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.libararymanager.Activity.Home;
import com.example.libararymanager.Activity.MainActivity;
import com.example.libararymanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {


    public Login() {
        // Required empty public constructor
    }
    Button button;
    TextInputEditText emailEdit , passwordEdit;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextInputLayout emailInput,passwordInput;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getContext());
        firebaseAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = getView().findViewById(R.id.loginBitton);
        emailEdit = getView().findViewById(R.id.emailLogin);
        passwordEdit = getView().findViewById(R.id.passwordLogin);
        emailInput = getView().findViewById(R.id.emailTextInputLogin);
        passwordInput = getView().findViewById(R.id.textInputPasswordLogin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailEdit.getText().toString().isEmpty()){
                   emailInput.setError("Enter Email");
                }else if (passwordEdit.getText().toString().isEmpty()){
                    passwordInput.setError("Enter Password");
                }else {
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(emailEdit.getText().toString(),passwordEdit.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                              progressDialog = new ProgressDialog(getContext());
                              if (task.isSuccessful()){
                                  startActivity(new Intent(getContext(), Home.class));
                                  getActivity().finish();
                                    }else {
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

        emailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailInput.isErrorEnabled()){
                    emailInput.setErrorEnabled(false);
                }
            }
        });

        passwordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordInput.isErrorEnabled()){
                    emailInput.setErrorEnabled(false);
                }
            }
        });

    }
}
