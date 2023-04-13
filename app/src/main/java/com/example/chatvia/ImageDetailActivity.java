package com.example.chatvia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatvia.databinding.ActivityImageDetailBinding;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.FileOutputStream;

public class ImageDetailActivity extends AppCompatActivity {

    private ActivityImageDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        binding = ActivityImageDetailBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.imageDetail_toolbar);
        PhotoView photoView = findViewById(R.id.imageDetail_photoView);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back_icon);
        actionBar.setTitle(intent.getStringExtra("name"));

        Glide.with(this).load(intent.getStringExtra("href")).fitCenter().into(photoView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_imagedetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.imageDetail_download:
                downloadImage(getBaseContext());
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void downloadImage(Context context) {
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("href");
        String fileName = intent.getStringExtra("name");
        Picasso.get()
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        // tải xuống ảnh thành công, lưu vào bộ nhớ
                        try {
                            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.close();
                            Toast.makeText(context, "Đã tải xuống ảnh thành công", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Lỗi khi tải xuống ảnh", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        // tải xuống ảnh thất bại
                        Toast.makeText(context, "Lỗi khi tải xuống ảnh", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        // đang tải xuống ảnh
                    }
                });
    }
}