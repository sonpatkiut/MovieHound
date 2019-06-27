package com.example.icsssd358.ratingstar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MoviesListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MovieAdapter adapter;
    public static String TYPE = "";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        TYPE = getIntent().getStringExtra("TYPE");

        makeRequest();

        recyclerView = findViewById(R.id.movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest();
                //startActivity(new Intent(MoviesListActivity.this, MovieDetailsActivity.class));
            }
        });
    }

    private void makeRequest(){
        findViewById(R.id.movieView).setVisibility(View.GONE);
        findViewById(R.id.noData).setVisibility(View.VISIBLE);
        findViewById(R.id.loader).setVisibility(View.VISIBLE);
        findViewById(R.id.retry).setVisibility(View.GONE);
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/" + TYPE + "?api_key=77f129537f3188528722d22cc93226d4")
                .addHeaders("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3N2YxMjk1MzdmMzE4ODUyODcyMmQyMmNjOTMyMjZkNCIsInN1YiI6IjVjYzdkNTYwYzNhMzY4M2FkNTg0ZmNjNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.OK7ziMZIQUM7EqoUy77vU-EDdeEWhOmtul_S9z9UEBg")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response!=null){
                            findViewById(R.id.noData).setVisibility(View.GONE);
                            findViewById(R.id.movieView).setVisibility(View.VISIBLE);
                            try {
                                JSONArray jsonArray = response.getJSONArray("results");
                                Type type = new TypeToken<List<MovieResponse>>() {
                                }.getType();
                                List<MovieResponse> movieResponses = new Gson().fromJson(jsonArray.toString(), type);
                                adapter = new MovieAdapter(MoviesListActivity.this, movieResponses);
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            findViewById(R.id.noData).setVisibility(View.VISIBLE);
                            findViewById(R.id.loader).setVisibility(View.GONE);
                            findViewById(R.id.retry).setVisibility(View.VISIBLE);
                            findViewById(R.id.movieView).setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.noData).setVisibility(View.VISIBLE);
                        findViewById(R.id.loader).setVisibility(View.GONE);
                        findViewById(R.id.retry).setVisibility(View.VISIBLE);
                        findViewById(R.id.movieView).setVisibility(View.GONE);
                        Toast.makeText(MoviesListActivity.this, anError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
