/**
 * @Author BWG
 */
package com.example.frontend.screens.logInScreens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.frontend.R;
import com.example.frontend.model.helpers.HashPassword;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class PasswordResetActivity extends AppCompatActivity {

    private TextInputLayout usernameField;
    private TextInputLayout emailField;
    private TextInputLayout birthdayField;
    private TextInputLayout passwordField;
    private String baseUrl = "http://coms-309-008.cs.iastate.edu:8080/";
    private JSONObject getResponse;

    /**
     * onCreate constructor for Android Studio, loads the default screen for the PasswordResetActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        Button resetButton = findViewById(R.id.resetPage_Reset_Button);
        usernameField = findViewById(R.id.resetPage_Username_Layout);
        emailField = findViewById(R.id.resetPage_Email_Layout);
        birthdayField = findViewById(R.id.resetPage_Birthday_Layout);
        passwordField = findViewById(R.id.resetPage_Password_Layout);

        resetButton.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick listener for the reset button
             * @param v
             */
            @Override
            public void onClick(View v) {
                String username = usernameField.getEditText().getText().toString().trim();
                String email = emailField.getEditText().getText().toString().trim();
                String birthday = birthdayField.getEditText().getText().toString().trim();
                String password =  passwordField.getEditText().getText().toString();
                checkUser(username, email, birthday, password);



            }
        });


    }

    /**
     * Compares the users provided information to the information available on the database
     * @param user The username of the user
     * @param email The email of the user
     * @param birthday The birthday of the user
     * @param password The new password the user wants to set
     */
    private void checkUser(String user, String email, String birthday, String password) {
            String getUrl = baseUrl + "users/username/" + user;
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    getResponse = response;
                    System.out.println(response);


                    String userEmail = null;
                    String userDOB = null;

                    try {
                        userEmail = (String) getResponse.getString("email");
                        userDOB = (String) getResponse.getString("birthday");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if ((email.equals(userEmail)) && (birthday.equals(userDOB))){
                        System.out.println("Matched");
                        resetPassword(password, user);

                    }
                    else{
                        usernameField.setError("Invalid username, email, or DOB");
                        emailField.setError("Invalid username, email, or DOB");
                        birthdayField.setError("Invalid username, email, or DOB");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            requestQueue.add(jsonObjectRequest);

    }


    /**
     * Creates a new salt for user and hashes their new password, updates user information on database
     * @param password The new password the user has selected
     * @param user The username of the user who is having their password reset
     */
    private void resetPassword(String password, String user) {
        System.out.println(password);
        String newSalt = HashPassword.newSalt();
        String hashedPassword = HashPassword.hash(password, newSalt);
        System.out.println(hashedPassword);

        String putUrl = baseUrl + "users/username/" + user;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject putData = new JSONObject();
        try {
            putData.put("salt", newSalt);
            putData.put("password", hashedPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, putUrl, putData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Successfully Updated");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}