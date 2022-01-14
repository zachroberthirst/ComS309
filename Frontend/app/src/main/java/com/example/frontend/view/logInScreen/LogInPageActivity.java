package com.example.frontend.view.logInScreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.frontend.R;
import com.example.frontend.model.MainApplication;
import com.example.frontend.model.helpers.CheckInput;
import com.example.frontend.presenter.logIn.ILogInPresenter;
import com.example.frontend.presenter.logIn.LogInPresenter;

import com.example.frontend.view.SafeCloseActivity;
import com.example.frontend.view.bottomTabScreensShelter.listings.ShelterListingsActivity;

import com.example.frontend.view.bottomTabScreens.PetSwipingPageActivity;
import com.example.frontend.screens.logInScreens.PasswordResetActivity;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.server.ServerRequest;

import com.example.frontend.view.registrationScreen.TypeOfActorActivity;

import com.google.android.material.textfield.TextInputLayout;




import java.util.Objects;


/**
 * Landing page for application
 *
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class LogInPageActivity extends AppCompatActivity implements ILogInView {

    private TextInputLayout usernameField;
    private TextInputLayout passwordField;

    private ILogInPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        Button logInButton = findViewById(R.id.logInPage_LogInButton);
        usernameField = findViewById(R.id.logInPage_Username_Layout);
        passwordField = findViewById(R.id.logInPage_Password_Layout);
        TextView resetPassword_text = findViewById(R.id.logInPage_ResetPassword_Text);
        TextView signUp_text = findViewById(R.id.logInPage_SignUp_Text);

        IServerRequest serverRequest = new ServerRequest();
        presenter = new LogInPresenter(this, serverRequest);

        logInButton.setOnClickListener(view -> {
            //Confirm Input
            checkCredentials();
        });

        signUp_text.setOnClickListener(view -> {
            //goto Sign Up Page TODO
            Intent intent = new Intent(view.getContext(), TypeOfActorActivity.class);
            startActivity(intent);
        });

        resetPassword_text.setOnClickListener(view -> {
            //goto Reset Password Page TODO
            Intent intent = new Intent(view.getContext(), PasswordResetActivity.class);
            startActivity(intent);
        });

    }

    private void checkCredentials(){
        String username = Objects.requireNonNull(usernameField.getEditText()).getText().toString().trim();
        boolean validUsername = CheckInput.setError(CheckInput.userName(username), usernameField);

        String password = Objects.requireNonNull(passwordField.getEditText()).getText().toString().trim();
        boolean validPassword =CheckInput.setError(CheckInput.password(password), passwordField);

        if (validUsername && validPassword){
            presenter.logIn(username);
        }

    }

    @Override
    protected void onDestroy() {
        MainApplication.cc.close();
        super.onDestroy();
    }

    @Override
    public void nextPage(){
        MainApplication.initWebSocket();
        //go to next screen TODO
        Intent intent = new Intent(getApplicationContext(), PetSwipingPageActivity.class);
        startActivity(intent);
    }

    @Override
    public void setErrors(String error) {
        usernameField.setError(error);
        passwordField.setError(error);
    }
    @Override
    public String getPassword() {
        return Objects.requireNonNull(passwordField.getEditText()).getText().toString().trim();
    }
    @Override
    public void shelterNextPage(){
        MainApplication.initWebSocket();
        Intent intent = new Intent(getApplicationContext(), ShelterListingsActivity.class);
        startActivity(intent);
    }

}