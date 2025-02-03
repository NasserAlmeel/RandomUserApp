package com.example.randomuserapp;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_USER = 0;
    private static final int ITEM_TYPE_LOADING = 1;

    private Context context;
    private List<UserModel> userList;
    private boolean isLoadingAdded = false;

    public UserAdapter(Context context, List<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == userList.size()) ? ITEM_TYPE_LOADING : ITEM_TYPE_USER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_USER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            UserModel user = userList.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.userName.setText(user.getName().getFullName());

            // Load image with Glide and handle errors
            Glide.with(context)
                    .load(user.getPicture().getLarge())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder_image)  // While loading
                    .error(R.drawable.error_image)  // If fails
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Toast.makeText(context, "Failed to load image for " + user.getName().getFullName(), Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(userViewHolder.profileImageView);
        }
    }

    @Override
    public int getItemCount() {
        return isLoadingAdded ? userList.size() + 1 : userList.size();
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        notifyItemInserted(userList.size());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        notifyItemRemoved(userList.size());
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView userName;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            userName = itemView.findViewById(R.id.userName);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}