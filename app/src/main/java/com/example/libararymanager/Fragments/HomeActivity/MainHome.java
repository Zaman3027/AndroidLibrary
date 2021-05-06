package com.example.libararymanager.Fragments.HomeActivity;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.libararymanager.Adapter.GenreAdapter;
import com.example.libararymanager.Adapter.NewArrivalAdapter;
import com.example.libararymanager.Adapter.TopRatedAdapter;
import com.example.libararymanager.Model.BookModel;
import com.example.libararymanager.R;
import com.example.libararymanager.StaticData.CallBookItem;
import com.example.libararymanager.StaticData.CallGenreFiler;
import com.example.libararymanager.StaticData.FirebaseSaticData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHome extends Fragment implements CallBookItem {



    public MainHome() {
        // Required empty public constructor
    }
    RecyclerView newArrivalRec,genreRec , topRated ;
    public static NewArrivalAdapter newArrivalAdapter;
    public static  TopRatedAdapter topRatedAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newArrivalRec = getView().findViewById(R.id.home_new_arrival_recycler);
        newArrivalRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newArrivalAdapter =  new NewArrivalAdapter(FirebaseSaticData.BookModelList,this);
        newArrivalRec.setAdapter(newArrivalAdapter);

        genreRec = getView().findViewById(R.id.home_genre_recycler);
        genreRec.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));
        genreRec.setAdapter(new GenreAdapter(new CallGenreFiler() {
            @Override
            public void callGenreFiler(String genre) {
                Log.i("FilterGenre",genre);
                getFragmentManager().beginTransaction().replace(R.id.main_frame,new GenreFilter(genre)).addToBackStack("MainHome").commit();
            }
        }));

        topRated = getView().findViewById(R.id.home_top_rated_recycler);
        topRated.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        topRatedAdapter = new TopRatedAdapter(FirebaseSaticData.BookModelList,this);
        topRated.setAdapter(topRatedAdapter);
    }

    @Override
    public void callShowBook(BookModel bookModel) {
        getFragmentManager().beginTransaction().replace(R.id.main_frame,new ShowBookDetails(bookModel)).addToBackStack("MainHome").commit();
    }
}
