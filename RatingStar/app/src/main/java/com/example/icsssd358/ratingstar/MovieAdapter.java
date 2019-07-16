package com.example.icsssd358.ratingstar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context context;
    private List<MovieResponse> list;
    public MovieAdapter(Context context, List<MovieResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.name.setText(list.get(i).getOriginalTitle());
        myViewHolder.date.setText("Date : " + list.get(i).getDate());
        myViewHolder.ratings.setText(list.get(i).getRatings());
        myViewHolder.ratings.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.staricon, 0);

        Glide.with(context)
                .load(MoviesListActivity.IMAGE_BASE_URL + list.get(i).getPosterPath())
                .placeholder(R.drawable.load)
                .into(myViewHolder.poster);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("IMAGEPATH", list.get(i).getBackPath());
                intent.putExtra("title", list.get(i).getOriginalTitle());
                intent.putExtra("Desc", list.get(i).getOverview());
                intent.putExtra("ID", list.get(i).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView date;
        public TextView ratings;
        public ImageView poster;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.movieInfo);
            poster = itemView.findViewById(R.id.poster);
            date = itemView.findViewById(R.id.date);
            ratings = itemView.findViewById(R.id.rating);
        }
    }
}
