package com.example.frontend.view.bottomTabScreens.actorProfile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.frontend.R;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.presenter.actorProfile.ActorProfilePresenter;
import com.example.frontend.presenter.actorProfile.IActorProfilePresenter;
import com.example.frontend.screens.CreateProfileActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class ActorProfileFragment extends Fragment implements IActorProfileFragmentView{

    private TextView tagline, bio, name, toolBarText;
    private ImageView imageView, profileMenu;
    private Button button;
    private Toolbar toolbar;
    private RelativeLayout data;
    private ProgressBar pageProgress;

    private Context context;
    private IActorProfilePresenter presenter;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public ActorProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IServerRequest serverRequest = new ServerRequest();
        presenter = new ActorProfilePresenter(this, serverRequest);
        initImagerLoader();
        //presenter.initInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_actor_profile, container, false);
        tagline = view.findViewById(R.id.textView16);
        bio = view.findViewById(R.id.textView18);
        name = view.findViewById(R.id.textView14);
        imageView = view.findViewById(R.id.imageView);
        button = view.findViewById(R.id.button);
        toolbar = view.findViewById(R.id.profile_toolbar);
        toolBarText = view.findViewById(R.id.profile_toolbar_text);
        profileMenu = view.findViewById(R.id.profile_menu);
        pageProgress = view.findViewById(R.id.page_progress);
        data = view.findViewById(R.id.relative_layout2);
        context = getActivity();

        button.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, EditProfileActivity.class);
            intent.putExtra("returnClass", getActivity().getClass().getCanonicalName());
            Log.d("hewwo", getActivity().getClass().getCanonicalName());
            startActivity(intent);
            getActivity().finish();
        });
        data.setVisibility(View.GONE);

        setUpToolBar();
        presenter.initInfo();
        return view;
    }
    private void setUpToolBar(){
        //((ActorsProfilePageActivity)getActivity()).setSupportActionBar(toolbar);
        profileMenu.setOnClickListener(view -> {
            Intent intent = new Intent(context, SettingsPageActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void setName(String firstName, String lastName) {
        if(firstName == null){
            firstName = "{firstName}";
        }
        if(lastName.isEmpty()){
            lastName = "{lastName}";
        }
        name.setText(String.format("%s %s", firstName, lastName));
    }

    @Override
    public void setPicture(String url) {
        String imageURL = "https://i.imgur.com/" + url + ".jpg";

        if(url == null){
            imageView.setImageResource(R.mipmap.default_profile_picture);
        }
        else{
            displayImage(imageView, imageURL);
        }
    }

    @Override
    public void setTagline(String tagline) {
        if(tagline == null){
            tagline = "{tagline}";
        }
        this.tagline.setText(String.format("%s",tagline));
    }

    @Override
    public void setBio(String bio) {
        if(bio == null){
            bio = "{biography}";
        }
        this.bio.setText(String.format("%s",bio));
    }

    @Override
    public void setToolBar(String username) {
        toolBarText.setText(String.format("%s",username));
    }

    @Override
    public void noProfile() {
        Intent intent = new Intent(context, CreateProfileActivity.class);
        intent.putExtra("returnClass", getActivity().getClass().getCanonicalName());
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void progressComplete() {
        pageProgress.setVisibility(View.GONE);
        data.setVisibility(View.VISIBLE);
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