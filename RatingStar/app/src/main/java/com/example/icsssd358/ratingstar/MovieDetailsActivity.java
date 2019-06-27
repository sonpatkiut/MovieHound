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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class MovieDetailsActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
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
}
