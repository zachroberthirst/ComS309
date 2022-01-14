package com.example.frontend.view.registrationScreen.userRegistrationScreen;
import java.time.*;
import java.util.Objects;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.frontend.model.user.UserType;
import com.google.android.material.textfield.TextInputLayout;
import com.example.frontend.R;

import com.example.frontend.model.helpers.CheckInput;
import com.example.frontend.model.helpers.HashPassword;
import com.example.frontend.model.user.User;
import com.example.frontend.presenter.userRegistration.IRegistrationPresenter;
import com.example.frontend.presenter.userRegistration.RegistrationPresenter;
import com.example.frontend.view.bottomTabScreens.PetSwipingPageActivity;
import com.example.frontend.view.logInScreen.LogInPageActivity;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.server.ServerRequest;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AccountRegistrationPageActivity extends AppCompatActivity implements IRegistrationView {

    private TextInputLayout nameField;
    private TextInputLayout dOBField;
    private TextInputLayout emailField;
    private TextInputLayout usernameField;
    private TextInputLayout passwordField;
    private CheckBox agreeToTOS;
    private String username;
    private IRegistrationPresenter presenter;

    private boolean nameIsValid;
    private boolean dOBIsValid;
    private boolean emailIsValid;
    private boolean passwordIsValid;
    private boolean tOSIsValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_registration_page);

        Button registerButton = findViewById(R.id.AccountRegistrationPage_Register_Button);
        TextView tOS_text = findViewById(R.id.AccountRegistrationPage_TOS_Text);
        agreeToTOS = findViewById(R.id.AccountRegistrationPage_TOS_Checkbox);

        nameField = findViewById(R.id.AccountRegistrationPage_Name_Layout);
        dOBField = findViewById(R.id.AccountRegistrationPage_DOB_Layout);
        emailField = findViewById(R.id.AccountRegistrationPage_Email_Layout);
        usernameField = findViewById(R.id.AccountRegistrationPage_Username_Layout);
        passwordField = findViewById(R.id.AccountRegistrationPage_Password_Layout);

        IServerRequest serverRequest = new ServerRequest();
        presenter = new RegistrationPresenter(this, serverRequest);

        //check if input is correct
        registerButton.setOnClickListener(view -> {
            //check if information is entered correctly
            nameIsValid = checkName();
            dOBIsValid = checkDOB();
            emailIsValid = checkEmail();
            passwordIsValid = checkPassword();
            tOSIsValid = checkTOS();

            //set errors if applicable
            String username = Objects.requireNonNull(usernameField.getEditText()).getText().toString().trim();
            if(!CheckInput.setError(CheckInput.userName(username), usernameField)){
                return;
            }

            //ask DB if user exists returns userTaken() or userFree()
            presenter.hasUser(username);
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
    //check if user agreed to TOS
    private boolean checkTOS(){
        return agreeToTOS.isChecked();
    }

    @Override
    public void usernameTaken() {
        //set errors if applicable
        usernameField.setError("Someone already has that username");
    }

    @Override
    public void usernameFree() {
        //check if an input fails
        if(nameIsValid && dOBIsValid && emailIsValid && passwordIsValid && tOSIsValid){
            //send User to DB
            presenter.sendUser(createUser());
        }
    }

    private User createUser() {
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

        User u = new User(CheckInput.capitalize(names[0]), CheckInput.capitalize(names[1]), LocalDate.of(year, month, day).toString(), email, username, hashedPassword, passwordSalt, UserType.USER);
        this.username = username;
        return u;
    }
    @Override
    public void nextPage(){
        Intent intent = new Intent(getApplicationContext(), PetSwipingPageActivity.class);
        //intent.putExtra("activeUser", user);
        //finish is used to close current activity
        finish();
        startActivity(intent);
    }
}