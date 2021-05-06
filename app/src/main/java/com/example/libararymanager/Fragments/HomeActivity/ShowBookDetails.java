package com.example.libararymanager.Fragments.HomeActivity;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.libararymanager.Model.BookModel;
import com.example.libararymanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowBookDetails extends Fragment {


    public ShowBookDetails() {
        // Required empty public constructor
    }
    TextView bookName,author,publisher,rating,genre,desc;
    ImageView bookImage;
    BookModel bookModel;

    public ShowBookDetails(BookModel bookModel) {
        this.bookModel = bookModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_book_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookImage = getView().findViewById(R.id.about_book_image);
        bookName = getView().findViewById(R.id.about_book_name);
        author = getView().findViewById(R.id.about_book_author);
        publisher = getView().findViewById(R.id.about_book_publisher);
        rating = getView().findViewById(R.id.about_book_rating);
        genre  = getView().findViewById(R.id.about_book_genre);
        desc = getView().findViewById(R.id.about_book_desc);

        Glide.with(getActivity())
                .load(bookModel.getImageLink())
                .centerCrop()
                .placeholder(R.drawable.gliderec)
                .into(bookImage);
        bookName.setText(bookModel.getBookName());
        author.setText(bookModel.getAuthor());
        publisher.setText(bookModel.getPublisher());
        rating.setText(bookModel.getRating());
        genre.setText(bookModel.getGenre());
        desc.setText(bookModel.getBookBescription());
    }
}
