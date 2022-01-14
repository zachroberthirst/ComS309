package com.example.frontend.view.bottomTabScreens.actorProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.frontend.R;
import com.example.frontend.model.listing.PetType;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.model.setting.Setting;
import com.example.frontend.presenter.settings.ISettingsPresenter;
import com.example.frontend.presenter.settings.SettingsPresenter;

import java.util.ArrayList;
import java.util.List;

public class SettingsPageActivity extends AppCompatActivity implements ISettingsPageView{

    private ISettingsPresenter presenter;
    private CheckBox dogBox, catBox, birdBox, reptileBox, rabbitBox, fishBox, ferretBox;
    private Button submitButton, deleteButton;
    private Setting currentSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        IServerRequest serverRequest = new ServerRequest();
        presenter = new SettingsPresenter(this, serverRequest);

        dogBox = findViewById(R.id.dog_box);
        catBox = findViewById(R.id.cat_box);
        birdBox = findViewById(R.id.bird_box);
        reptileBox = findViewById(R.id.reptile_box);
        rabbitBox = findViewById(R.id.rabbit_box);
        fishBox = findViewById(R.id.fish_box);
        ferretBox = findViewById(R.id.ferret_box);

        submitButton = findViewById(R.id.submit_account_button);
        deleteButton = findViewById(R.id.delete_account_button);

        submitButton.setOnClickListener(view -> {
            if(currentSetting == null){
                createSetting();
            }else{
                updateSetting();
            }
        });

        deleteButton.setOnClickListener(view -> {
            presenter.deleteUser();
        });

        presenter.getSetting();
    }

    private void createSetting() {
        Setting s = new Setting();
        s.setPetPreferences(getChecked());
        presenter.createSetting(s);
    }
    private void updateSetting() {
        Setting s = currentSetting;
        s.setPetPreferences(getChecked());
        presenter.updateSetting(s);
    }

    private List<PetType> getChecked(){
        List<PetType> preferences = new ArrayList<>();
        if(dogBox.isChecked()){
            preferences.add(PetType.DOG);
        }
        if(catBox.isChecked()){
            preferences.add(PetType.CAT);
        }
        if(birdBox.isChecked()){
            preferences.add(PetType.BIRD);
        }
        if(reptileBox.isChecked()){
            preferences.add(PetType.REPTILE);
        }
        if(rabbitBox.isChecked()){
            preferences.add(PetType.RABBIT);
        }
        if(fishBox.isChecked()){
            preferences.add(PetType.FISH);
        }
        if(ferretBox.isChecked()){
            preferences.add(PetType.FERRET);
        }
        return preferences;
    }
    @Override
    public void gotSetting(Setting setting) {
        if(setting == null){
            noSetting();
        }else{
            currentSetting = setting;
            List<PetType> petTypes =  setting.getPetPreferences();

            dogBox.setChecked(petTypes.contains(PetType.DOG));
            catBox.setChecked(petTypes.contains(PetType.CAT));
            birdBox.setChecked(petTypes.contains(PetType.BIRD));
            reptileBox.setChecked(petTypes.contains(PetType.REPTILE));
            rabbitBox.setChecked(petTypes.contains(PetType.RABBIT));
            fishBox.setChecked(petTypes.contains(PetType.FISH));
            ferretBox.setChecked(petTypes.contains(PetType.FERRET));

        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void noSetting() {
        currentSetting = null;
        dogBox.setChecked(false);
        catBox.setChecked(false);
        birdBox.setChecked(false);
        reptileBox.setChecked(false);
        rabbitBox.setChecked(false);
        fishBox.setChecked(false);
        ferretBox.setChecked(false);
    }

    @Override
    public void deleted() {
        this.finishAffinity();
    }
}