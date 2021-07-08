package com.example.simpleinstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpleinstagram.databinding.ActivityPostDetailsBinding;

public class PostDetailsActivity extends AppCompatActivity {

    TextView tvUsername;
    ImageView ivImage;
    TextView tvCreatedAt;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostDetailsBinding binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Toast.makeText(this, "Post details!", Toast.LENGTH_SHORT).show();
    }
}