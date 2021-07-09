package com.example.simpleinstagram.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.simpleinstagram.R;
import com.example.simpleinstagram.adapters.ProfileAdapter;
import com.example.simpleinstagram.models.Post;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    public static final int NUM_COLUMNS = 3;
    List<Post> posts;
    TextView tvUsername;
    ImageView ivProfileImage;
    RecyclerView rvPosts;
    ProfileAdapter adapter;
    ParseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        posts = new ArrayList<>();
        user = ParseUser.getCurrentUser();

        tvUsername = view.findViewById(R.id.tvUsername);
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        setUserInfo();

        rvPosts = view.findViewById(R.id.rvPosts);
        adapter = new ProfileAdapter(getContext(), posts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new GridLayoutManager(getContext(), NUM_COLUMNS));

        queryPosts();
    }

    private void setUserInfo() {
        tvUsername.setText(user.getUsername());
        ParseFile profileImage = user.getParseFile("profile_photo");
        if (profileImage != null) {
            Glide.with(getContext())
                    .load(profileImage.getUrl())
                    .circleCrop()
                    .into(ivProfileImage);
        } else {
            Toast.makeText(getContext(), "what the hyuck", Toast.LENGTH_SHORT).show();
        }
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, user);
        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Problem with querying posts ", e);
                    return;
                }
                Log.i(TAG, "Success querying posts");
                adapter.clear();
                // save received posts to list and notify adapter of new data
                posts.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }
}