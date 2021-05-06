package com.example.libararymanager.Adapter;

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
import com.example.libararymanager.Model.BookModel;
import com.example.libararymanager.R;
import com.example.libararymanager.StaticData.CallBookItem;

import java.util.ArrayList;

public class NewArrivalAdapter extends RecyclerView.Adapter<NewArrivalAdapter.MyviewHolder> {
    ImageView bookImage;
    TextView bookName;
    ArrayList<BookModel> bookModels;
    Context context;
    LinearLayout linearLayout;
    CallBookItem callBookItem;

    public NewArrivalAdapter(ArrayList<BookModel> bookModels, CallBookItem callBookItem) {
        this.bookModels = bookModels;
        this.callBookItem = callBookItem;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_arrival_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int position) {
        Glide.with(context)
                .load(bookModels.get(position).getImageLink())
                .centerCrop()
                .placeholder(R.drawable.gliderec)
                .into(bookImage);
        bookName.setText(bookModels.get(position).getBookName());
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

    class MyviewHolder extends RecyclerView.ViewHolder{

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.new_arrival_bookName);
            bookImage = itemView.findViewById(R.id.new_arrival_image);
            linearLayout = itemView.findViewById(R.id.parent_new_arrival);

        }
    }
}
