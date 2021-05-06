package com.example.libararymanager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.libararymanager.Activity.Home;
import com.example.libararymanager.Model.BookModel;
import com.example.libararymanager.R;
import com.example.libararymanager.StaticData.CallBookItem;

import java.util.ArrayList;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.MyViewHolder> {
    ArrayList<BookModel> bookModels;
    ImageView bookImage;
    TextView bookName,authorNAme,genre,publisher,rating;
    Context context;
    LinearLayout linearLayout;
    CallBookItem callBookItem;

    public TopRatedAdapter(ArrayList<BookModel> bookModels, CallBookItem callBookItem) {
        this.bookModels = bookModels;
        this.callBookItem = callBookItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.top_rated_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Glide.with(context)
                .load(bookModels.get(position).getImageLink())
                .centerCrop()
                .placeholder(R.drawable.gliderec)
                .into(bookImage);
        bookName.setText(bookModels.get(position).getBookName());
        authorNAme.setText(bookModels.get(position).getAuthor());
        genre.setText(bookModels.get(position).getGenre());
        publisher.setText(bookModels.get(position).getPublisher());
        rating.setText(bookModels.get(position).getRating());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBookItem.callShowBook(bookModels.get(position));
            }
        });



    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.top_rated_image);
            bookName = itemView.findViewById(R.id.top_rated_book_name);
            authorNAme = itemView.findViewById(R.id.top_rated_author);
            genre = itemView.findViewById(R.id.top_rated_genre);
            publisher = itemView.findViewById(R.id.top_rated_publisher);
            rating = itemView.findViewById(R.id.top_rated_rating);
            linearLayout = itemView.findViewById(R.id.parent_top_rated);
        }
    }


}
