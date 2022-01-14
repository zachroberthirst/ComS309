package com.example.frontend.view.bottomTabScreens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.frontend.R;
import com.example.frontend.model.listing.Listing;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;


public class SwipingListingFragment extends Fragment implements IPetSwipingListingFragmentView{
    private TextView petName, petAge, petType, petDescription;
    private ImageView petPicture;
    private ProgressBar pictureProgress;
    private Listing listing;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public SwipingListingFragment(Listing listing) {
        // Required empty public constructor
    }

    public SwipingListingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_swiping_listing, container, false);
        petName = view.findViewById(R.id.pet_name);
        petAge = view.findViewById(R.id.pet_age);
        petType = view.findViewById(R.id.pet_type);
        petDescription = view.findViewById(R.id.pet_description);
        petPicture = view.findViewById(R.id.pet_picture);
        pictureProgress = view.findViewById(R.id.picture_progress);
        pictureProgress.setVisibility(View.VISIBLE);
        initImagerLoader();
        if(getArguments() != null) {
            setName(requireArguments().getString("name"));
            setAge(requireArguments().getInt("age"));
            setDescription(requireArguments().getString("description"));
            setType(requireArguments().getString("type"));
            setImage(requireArguments().getStringArrayList("images"));
        }

        return view;
    }

    @Override
    public void setName(String name) {
        if(name == null){
            //name = "{petName}";
        }
        petName.setText(String.format("%s", name));
    }

    @Override
    public void setAge(int age) {
        if(age < 0){
            age = 0;
        }
        petAge.setText(String.format("%d", age));
    }

    @Override
    public void setType(String type) {
        if(type == null){
            //petType.setText("{pet_type}");
        }
        petType.setText(String.format("%s", type));
    }

    @Override
    public void setDescription(String description) {
        if(description == null){
            //petDescription.setText("{description}");
        }
        petDescription.setText(String.format("%s", description));
    }

    @Override
    public void setImage(ArrayList<String> urls) {
        if(urls.isEmpty()){
            petPicture.setImageResource(R.mipmap.default_profile_picture);
            pictureProgress.setVisibility(View.GONE);
        }
        if(urls.size() > 0){
            String ImageID = urls.get(0);
            Log.d("PLEASE", ImageID);
            String URL = "https://i.imgur.com/" + ImageID + ".jpg";
            displayImage(petPicture, URL);
            pictureProgress.setVisibility(View.GONE);
        }

    }
    private void initImagerLoader(){
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity().getApplicationContext()));
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