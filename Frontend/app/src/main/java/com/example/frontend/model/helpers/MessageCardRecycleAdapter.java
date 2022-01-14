package com.example.frontend.model.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontend.R;
import com.example.frontend.model.MainApplication;
import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.match.Match;
import com.example.frontend.model.user.User;
import com.example.frontend.model.user.UserMessageCard;
import com.example.frontend.model.user.UserType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class MessageCardRecycleAdapter extends RecyclerView.Adapter<MessageCardRecycleAdapter.MyViewHolder> {

    private final ArrayList<UserMessageCard> cards;
    private final Context context;
    private final OnNoteListener onNoteListener;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;


    public MessageCardRecycleAdapter(ArrayList<UserMessageCard> cards, Context context, OnNoteListener onNoteListener){
        this.cards = cards;
        this.context = context;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_card_layout, parent,false );
        return new MyViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.image_progress.setVisibility(View.VISIBLE);
        initImagerLoader();
        Match m = cards.get(position).getMatch();
        Listing l = m.getListing();
        User u = m.getUser();
        String display = String.format("%s: %s",l.getPetName(), l.getPetType());
        if (l.getPictureURL().size() > 0){
            String ImageID = l.getPictureURL().get(0);
            String URL = "https://i.imgur.com/" + ImageID + ".jpg";
            displayImage(holder.profile_picture, URL);
            holder.image_progress.setVisibility(View.GONE);
        }
        if(MainApplication.getActiveUser().getType() == UserType.SHELTER){
            display = String.format("%s %s",u.getFirstName(), u.getLastName());
        }
        holder.title_Text.setText(display);
        if(cards.get(position).getImageUrl() == null){
            holder.profile_picture.setImageResource(R.mipmap.default_profile_picture);
            holder.image_progress.setVisibility(View.GONE);
        }

        holder.adorable_match.setVisibility(View.GONE);
        if(m.getAdorableAction()){
            holder.adorable_match.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title_Text;
        ImageView profile_picture, adorable_match;
        ProgressBar image_progress;
        OnNoteListener mOnNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            title_Text = itemView.findViewById(R.id.message_profile_name);
            profile_picture = itemView.findViewById(R.id.profile_picture);
            image_progress = itemView.findViewById(R.id.profile_picture_progress);
            adorable_match = itemView.findViewById(R.id.adorable_match);
            mOnNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnNoteListener.OnNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void OnNoteClick(int position);
    }
    private void initImagerLoader(){
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this.context));
        options = new DisplayImageOptions.Builder()
                // below image will be displayed when the image url is empty
                .showImageForEmptyUri(R.drawable.ic_launcher_background)
                // build will build the view for displaying image..
                .build();
    }
    private void displayImage(ImageView img, String imageURL){
        imageLoader.displayImage(imageURL, img, options, null);

    }
}
