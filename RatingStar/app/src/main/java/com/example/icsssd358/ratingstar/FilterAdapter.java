package com.example.icsssd358.ratingstar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder>{

    private Context context;
    private List<String> list;

    public FilterAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_list_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.attr.setText(list.get(i));
        if(i<=1){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MoviesListActivity.class);
                    intent.putExtra("TYPE", myViewHolder.attr.getText());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView attr ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            attr = itemView.findViewById(R.id.attribute);
        }
    }
}
