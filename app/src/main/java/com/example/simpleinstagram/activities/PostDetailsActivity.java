package com.example.simpleinstagram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.simpleinstagram.models.Post;
import com.example.simpleinstagram.databinding.ActivityPostDetailsBinding;
import com.example.simpleinstagram.fragments.PostsFragment;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class PostDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostDetailsBinding binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(PostsFragment.KEY_CLICKED_POST));
        ParseUser user = post.getUser();
        binding.tvUsername.setText(user.getUsername());
        binding.tvUsername2.setText(user.getUsername());
        binding.tvCreatedAt.setText(String.format("%s ago", Post.calculateTimeAgo(post.getCreatedAt())));
        binding.tvDescription.setText(post.getDescription());
        ParseFile profileImage = user.getParseFile("profile_photo");
        if (profileImage != null) {
            Glide.with(this)
                    .load(profileImage.getUrl())
                    .circleCrop()
                    .into(binding.ivProfileImage);
        }
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(binding.ivImage);
        }
    }
}