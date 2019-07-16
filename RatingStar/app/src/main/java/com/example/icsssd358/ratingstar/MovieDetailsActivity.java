package com.example.icsssd358.ratingstar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView desc;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        recyclerView = findViewById(R.id.recyclerview);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        desc = findViewById(R.id.desc);
        desc.setText(getIntent().getStringExtra("Desc"));
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra("title"));

        Glide.with(this)
                .load(MoviesListActivity.IMAGE_BASE_URL + getIntent().getStringExtra("IMAGEPATH"))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Drawable drawable = new BitmapDrawable(MovieDetailsActivity.this.getResources(), resource);
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                            collapsingToolbarLayout.setBackground(drawable);
                        }
                    }
                });
    }

    private void makeRequest(){
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/" + getIntent().getIntExtra("ID", 0) + "/videos?api_key=77f129537f3188528722d22cc93226d4")
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
                                Type type = new TypeToken<List<TrailersResponse>>() {
                                }.getType();
                                List<TrailersResponse> trailersResponses = new Gson().fromJson(jsonArray.toString(), type);
                                /*adapter = new MovieAdapter(MoviesListActivity.this, movieResponses);
                                recyclerView.setAdapter(adapter);*/
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
