package com.example.frontend.view.registrationScreen.shelterRegistrationScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.frontend.R;
import com.example.frontend.model.helpers.CheckInput;
import com.example.frontend.model.helpers.HashPassword;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.model.shelter.Shelter;
import com.example.frontend.model.user.User;
import com.example.frontend.model.user.UserType;
import com.example.frontend.presenter.shelterRegistration.IShelterRegistrationPresenter;
import com.example.frontend.presenter.shelterRegistration.ShelterRegistrationPresenter;
import com.example.frontend.view.bottomTabScreensShelter.listings.ShelterListingsActivity;
import com.example.frontend.view.bottomTabScreensShelter.shelterProfile.ShelterProfileActivity;
import com.example.frontend.view.logInScreen.LogInPageActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.Objects;

public class ShelterRegistrationActivity extends AppCompatActivity implements IShelterRegistrationView{
    private TextInputLayout nameField;
    private TextInputLayout dOBField;
    private TextInputLayout emailField;
    private TextInputLayout usernameField;
    private TextInputLayout passwordField;
    private TextInputLayout shelterNameField;
    private CheckBox agreeToTOS;

    private boolean nameIsValid;
    private boolean dOBIsValid;
    private boolean emailIsValid;
    private boolean passwordIsValid;
    private boolean tOSIsValid;
    private boolean shelterNameValid;

    private String username;
    private IShelterRegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_registration);

        Button registerButton = findViewById(R.id.AccountRegistrationPage_Register_Button);
        TextView tOS_text = findViewById(R.id.AccountRegistrationPage_TOS_Text);
        agreeToTOS = findViewById(R.id.AccountRegistrationPage_TOS_Checkbox);

        nameField = findViewById(R.id.AccountRegistrationPage_Name_Layout);
        dOBField = findViewById(R.id.AccountRegistrationPage_DOB_Layout);
        emailField = findViewById(R.id.AccountRegistrationPage_Email_Layout);
        usernameField = findViewById(R.id.AccountRegistrationPage_Username_Layout);
        passwordField = findViewById(R.id.AccountRegistrationPage_Password_Layout);
        shelterNameField = findViewById(R.id.AccountRegistrationPage_ShelterName_Layout);

        IServerRequest serverRequest = new ServerRequest();
        presenter = new ShelterRegistrationPresenter(this, serverRequest);

        registerButton.setOnClickListener(view -> {
            //check if information is entered correctly
            nameIsValid = checkName();
            dOBIsValid = checkDOB();
            emailIsValid = checkEmail();
            passwordIsValid = checkPassword();
            tOSIsValid = checkTOS();
            shelterNameValid = checkShelterName();

            //set errors if applicable
            String username = Objects.requireNonNull(usernameField.getEditText()).getText().toString().trim();
            if(!CheckInput.setError(CheckInput.userName(username), usernameField)){
                return;
            }

            presenter.hasShelter(username);
        });

        tOS_text.setOnClickListener(view -> {
            //goto terms of service TODO change LogInPageActivity to TOS
            Intent intent = new Intent(view.getContext(), LogInPageActivity.class);
            startActivity(intent);
        });
    }
    private boolean checkName(){
        //set errors if applicable
        String fullName = Objects.requireNonNull(nameField.getEditText()).getText().toString().trim();
        return CheckInput.setError(CheckInput.fullName(fullName), nameField);
    }

    private boolean checkDOB(){
        //set errors if applicable
        String date = Objects.requireNonNull(dOBField.getEditText()).getText().toString().trim();
        return CheckInput.setError(CheckInput.dOB(date), dOBField);
    }

    private boolean checkEmail(){
        //set errors if applicable
        String email = Objects.requireNonNull(emailField.getEditText()).getText().toString().trim();
        return CheckInput.setError(CheckInput.email(email), emailField);
    }

    private boolean checkPassword(){
        //set errors if applicable
        String password = Objects.requireNonNull(passwordField.getEditText()).getText().toString().trim();
        return CheckInput.setError(CheckInput.password(password), passwordField);
    }
    private boolean checkShelterName(){
        String shelterName = Objects.requireNonNull(shelterNameField.getEditText()).getText().toString().trim();
        return CheckInput.setError(CheckInput.shelterName(shelterName), shelterNameField);
    }
    //check if user agreed to TOS
    private boolean checkTOS(){
        return agreeToTOS.isChecked();
    }

    @Override
    public void usernameTaken() {
        usernameField.setError("Someone already has that username");
    }

    @Override
    public void usernameFree() {
        usernameField.setError(null);
        //check if an input fails
        if(nameIsValid && dOBIsValid && emailIsValid && passwordIsValid && shelterNameValid && tOSIsValid){
            //send User to DB
            presenter.sendShelter(createShelter());
        }
    }

    @Override
    public void nextPage() {
        Intent intent = new Intent(getApplicationContext(), ShelterListingsActivity.class);
        //finish is used to close current activity
        finish();
        startActivity(intent);
    }
    private Shelter createShelter() {
        //delimit full name by space char
        String fullName = Objects.requireNonNull(nameField.getEditText()).getText().toString().trim();
        String[] names = fullName.split(" ");

        //get date from string
        String[] dates = Objects.requireNonNull(dOBField.getEditText()).getText().toString().trim().split("/");
        int year = Integer.parseInt(dates[2]);
        int day = Integer.parseInt(dates[1]);
        int month = Integer.parseInt(dates[0]);

        String email = Objects.requireNonNull(emailField.getEditText()).getText().toString().trim();
        String username =  Objects.requireNonNull(usernameField.getEditText()).getText().toString().trim();
        String passwordSalt = HashPassword.newSalt();
        String hashedPassword = HashPassword.hash(Objects.requireNonNull(passwordField.getEditText()).getText().toString().trim(), passwordSalt);

        String shelterName = Objects.requireNonNull(shelterNameField.getEditText()).getText().toString().trim();
        shelterName = CheckInput.capitalize(shelterName);

        User u = new User(CheckInput.capitalize(names[0]), CheckInput.capitalize(names[1]), LocalDate.of(year, month, day).toString(), email, username, hashedPassword, passwordSalt, UserType.SHELTER);
        this.username = username;
        return new Shelter(u, shelterName);
    }
}