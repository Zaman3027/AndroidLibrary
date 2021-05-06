package com.example.libararymanager.Fragments.HomeActivity;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.libararymanager.Adapter.TopRatedAdapter;
import com.example.libararymanager.Model.BookModel;
import com.example.libararymanager.R;
import com.example.libararymanager.StaticData.CallBookItem;
import com.example.libararymanager.StaticData.FirebaseSaticData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenreFilter extends Fragment implements  CallBookItem{

    RecyclerView recyclerView;
    ArrayList<BookModel> bookModels;
    String boolGenre ;

    public GenreFilter(String boolGenre) {
        this.boolGenre = boolGenre;
    }

    public GenreFilter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bookModels = new ArrayList<>();

        return inflater.inflate(R.layout.fragment_genre_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.genreList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        TopRatedAdapter topRatedAdapter  = new TopRatedAdapter(bookModels, this);
        recyclerView.setAdapter(topRatedAdapter);

        for (BookModel bookModel : FirebaseSaticData.BookModelList){
            if (bookModel.getGenre().equals(boolGenre)){
                bookModels.add(bookModel);

            }
        }
        topRatedAdapter.notifyDataSetChanged();


    }

    @Override
    public void callShowBook(BookModel bookModel) {
        getFragmentManager().beginTransaction().replace(R.id.main_frame,new ShowBookDetails(bookModel)).addToBackStack("BackToGenre").commit();
    }
}
