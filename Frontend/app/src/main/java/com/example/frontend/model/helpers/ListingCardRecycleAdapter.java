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
import com.example.frontend.model.listing.Listing;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Locale;

public class ListingCardRecycleAdapter extends RecyclerView.Adapter<ListingCardRecycleAdapter.MyViewHolder> {

    private final ArrayList<Listing> listings;
    private final Context context;
    private final OnNoteListener onNoteListener;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public ListingCardRecycleAdapter(ArrayList<Listing> listings, Context context, OnNoteListener onNoteListener){
        this.listings = listings;
        this.context = context;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_card_layout, parent,false );
        return new MyViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.image_progress.setVisibility(View.VISIBLE);
        initImagerLoader();
        Listing l = listings.get(position);
        String name, age, type, description;
        name = String.format(Locale.getDefault(),"%s",l.getPetName());
        age = String.format(Locale.getDefault(),"%d",l.getPetAge());
        type = String.format(Locale.getDefault(),"%s",l.getPetType());
        description = String.format(Locale.getDefault(),"%s",l.getDescription());

        if(l.getPetName() == null){
            //name = "{No Name}";
        }
        if(l.getDescription() == null){
            //description = "{No Description}";
        }

        holder.petName.setText(name);
        holder.petAge.setText(age);
        holder.petType.setText(type);
        holder.petDescription.setText(description);
        if(listings.get(position).getPictureURL().isEmpty()){
            holder.profile_picture.setImageResource(R.mipmap.default_profile_picture);
            holder.image_progress.setVisibility(View.GONE);
        }else{
            String PetID = l.getPictureURL().get(0);
            String URL = "https://i.imgur.com/" + PetID + ".jpg";
            displayImage(holder.profile_picture, URL);
            holder.image_progress.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView petName, petAge, petType, petDescription;
        ImageView profile_picture;
        ProgressBar image_progress;
        OnNoteListener mOnNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            petName = itemView.findViewById(R.id.card_pet_name);
            petAge = itemView.findViewById(R.id.card_pet_age);
            petType = itemView.findViewById(R.id.card_pet_type);
            petDescription = itemView.findViewById(R.id.card_pet_description);
            profile_picture = itemView.findViewById(R.id.card_pet_picture);
            image_progress = itemView.findViewById(R.id.card_picture_progress);
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
