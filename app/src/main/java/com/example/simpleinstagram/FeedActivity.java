package com.example.simpleinstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import com.example.simpleinstagram.databinding.ActivityFeedBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    public static final String TAG = "FeedActivity";
    List<Post> posts;
    RecyclerView rvPosts;
    PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFeedBinding binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        posts = new ArrayList<>();
        rvPosts = binding.rvPosts;
        adapter = new PostsAdapter(this, posts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.addItemDecoration(new DividerItemDecoration(binding.rvPosts.getContext(), DividerItemDecoration.VERTICAL));

        queryPosts();
    }

    private void queryPosts() {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        // limit query to latest 20 items
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
                // save received posts to list and notify adapter of new data
                Log.i(TAG, "Success querying posts");
                posts.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }
}