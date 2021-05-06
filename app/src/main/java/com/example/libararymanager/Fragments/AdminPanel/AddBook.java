package com.example.libararymanager.Fragments.AdminPanel;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.libararymanager.Activity.AdminActivity;
import com.example.libararymanager.Model.BookModel;
import com.example.libararymanager.R;
import com.example.libararymanager.StaticData.FirebaseSaticData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBook extends Fragment {

    private static final int PICK_IMAGE = 501;
    EditText bookNameText, authorNameText,publisherNameText,quantityText,descriptionText;
    Spinner spinnerGenre;
    ImageView bookImage;
    Button addBookSubmit;
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;
    private StorageReference mStorageRef;
    String uploadUri = null;

    final static String[] genre = {"Select Genre","CSE", "ME","EI","EE","ECE","IT","CE","COMMON"};
    private Uri filePath;


    public AddBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookNameText = getView().findViewById(R.id.addBookName);
        authorNameText = getView().findViewById(R.id.addBookAuthor);
        publisherNameText = getView().findViewById(R.id.addbookPublisher);
        quantityText = getView().findViewById(R.id.addbookQuatity);
        spinnerGenre = getView().findViewById(R.id.spinnerGnere);
        addBookSubmit = getView().findViewById(R.id.addBookSubmit);
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getContext());
        descriptionText = getView().findViewById(R.id.bookBescription);
        bookImage = getView().findViewById(R.id.addbookImage);
        progressDialog.setMessage("Hold On");
        progressDialog.setCanceledOnTouchOutside(false);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        ArrayAdapter<String > spinnerAdapter = new ArrayAdapter<String >(getContext(),android.R.layout.simple_list_item_1,android.R.id.text1,genre);

        spinnerGenre.setAdapter(spinnerAdapter);

        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imagePick = new Intent();
                imagePick.setType("image/*");
                imagePick.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imagePick, "Select Picture"), PICK_IMAGE);
            }
        });



        addBookSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookNameText.getText().toString().isEmpty()){
                    bookNameText.setError("Enter Book Name");
                }else  if (authorNameText.getText().toString().isEmpty()){
                    authorNameText.setError("Enter Author Name");
                }else  if (publisherNameText.getText().toString().isEmpty()){
                    publisherNameText.setError("Enter Publisher Name");
                }else if (descriptionText.getText().toString().isEmpty()){
                    descriptionText.setError("Enter Description");

                }else if (quantityText.getText().toString().isEmpty()){
                    quantityText.setError("Enter Quantity");
                }else if (spinnerGenre.getSelectedItemPosition()==0){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Select Genre")
                            .create()
                            .show();
                }else if (uploadUri==null){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Upload Image")
                            .create()
                            .show();
                }
                else {
                    progressDialog.show();

                    firestore.collection(FirebaseSaticData.BOOK)
                            .add( new BookModel(
                                    bookNameText.getText().toString(),
                                    authorNameText.getText().toString(),
                                    publisherNameText.getText().toString(),
                                    genre[spinnerGenre.getSelectedItemPosition()],

                                    "4.5",
                                    quantityText.getText().toString(),
                                    uploadUri,
                                    descriptionText.getText().toString()
                            )).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                getFragmentManager().beginTransaction().replace(R.id.adminFrame,new AdminFragment()).commit();
                            }
                        }
                    });
                }

            }
        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                bookImage.setImageBitmap(bitmap);
                uploadImage(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImage(Uri filePath){
        progressDialog.show();
        String name = "images/"+ Timestamp.now().toString()+" "+FirebaseSaticData.cuid;
        final StorageReference riversRef = mStorageRef.child(name);
        riversRef.putFile(filePath)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                   riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            uploadUri = uri.toString();
                        }
                    });
                }
            }
        });
    }
}
