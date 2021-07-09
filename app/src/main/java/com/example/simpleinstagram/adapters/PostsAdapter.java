package com.example.simpleinstagram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.simpleinstagram.models.Post;
import com.example.simpleinstagram.R;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private OnPostListener onPostListener;

    public PostsAdapter(Context context, List<Post> posts, OnPostListener onPostListener) {
        this.context = context;
        this.posts = posts;
        this.onPostListener = onPostListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view, onPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivProfileImage;
        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private OnPostListener onPostListener;
        private TextView tvUsername2;
        private TextView tvTimeAgo;

        public ViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUsername2  = itemView.findViewById(R.id.tvUsername2);
            tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo);
            this.onPostListener = onPostListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            ParseUser user = post.getUser();
            ParseFile profileImage = user.getParseFile("profile_photo");
            if (profileImage != null) {
                Glide.with(context)
                        .load(profileImage.getUrl())
                        .circleCrop()
                        .into(ivProfileImage);
            }
            tvDescription.setText(post.getDescription());
            tvUsername.setText(user.getUsername());
            tvUsername2.setText(user.getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            tvTimeAgo.setText(String.format("%s ago", Post.calculateTimeAgo(post.getCreatedAt())));
        }

        @Override
        public void onClick(View v) {
            onPostListener.onPostClicked(getAdapterPosition());
        }
    }

    public interface OnPostListener {
        void onPostClicked(int position);
    }
}
