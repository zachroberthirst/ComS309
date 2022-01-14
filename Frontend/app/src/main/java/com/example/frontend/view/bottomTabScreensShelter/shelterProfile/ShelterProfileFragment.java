package com.example.frontend.view.bottomTabScreensShelter.shelterProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.frontend.R;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.presenter.shelterProfile.IShelterProfilePresenter;
import com.example.frontend.presenter.shelterProfile.ShelterProfilePresenter;
import com.example.frontend.view.bottomTabScreens.actorProfile.EditProfileActivity;

public class ShelterProfileFragment extends Fragment implements IShelterProfileFragmentView{

    private Button button;
    private TextView name;
    private RelativeLayout data;
    private Context context;
    private IShelterProfilePresenter presenter;
    private ProgressBar pageProgress;

    public ShelterProfileFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IServerRequest serverRequest = new ServerRequest();
        presenter = new ShelterProfilePresenter(this, serverRequest);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shelter_profile, container, false);
        button = view.findViewById(R.id.edit_shelter_button);
        name = view.findViewById(R.id.shelter_name);
        pageProgress = view.findViewById(R.id.page_progress);
        data = view.findViewById(R.id.relative_layout);
        context = getActivity();

        button.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, EditProfileActivity.class);
            startActivity(intent);
        });
        data.setVisibility(View.GONE);

        presenter.initInfo();
        return view;
    }

    @Override
    public void setShelterName(String name) {
        if(name == null){
            name = "{shelter_name}";
        }
        this.name.setText(String.format("%s",name));
    }

    @Override
    public void progressComplete() {
        pageProgress.setVisibility(View.GONE);
        data.setVisibility(View.VISIBLE);
    }
}
