package com.example.libararymanager.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libararymanager.R;
import com.example.libararymanager.StaticData.CallGenreFiler;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> {
    LinearLayout linearLayout;
    TextView genreName;
    CallGenreFiler genreFiler;

    public GenreAdapter(CallGenreFiler genreFiler) {
        this.genreFiler = genreFiler;
    }

    final static String[] colorCode = {"#ef5350" , "#ec407a","#ab47bc","#7E57C2","#29B6F6","#66BB6A","#F23458","#FF7043"};
    final static String[] genre = {"CSE", "ME","EI","EE","ECE","IT","CE","COMMON"};

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        linearLayout.setBackgroundColor(Color.parseColor(colorCode[position%7]));
        genreName.setText(genre[position]);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genreFiler.callGenreFiler(genre[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  genre.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.genre_linerlayout);
            genreName = itemView.findViewById(R.id.genreText);
        }
    }
}
