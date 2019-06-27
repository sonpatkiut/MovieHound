package com.example.icsssd358.ratingstar;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FilterAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
                init();
            }
        }, 3000);
    }

    private void init(){
        List<String> filter = new ArrayList<>();
        filter.add("popular");
        filter.add("top_rated");
        filter.add("In Theatres");
        filter.add("Kids");
        filter.add("Drama");

        recyclerView = findViewById(R.id.filterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new FilterAdapter(this, filter);
        recyclerView.setAdapter(adapter);
    }
}
