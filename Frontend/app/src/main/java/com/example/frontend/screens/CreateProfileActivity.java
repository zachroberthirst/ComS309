/**
 * @Author BWG
 */

package com.example.frontend.screens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.frontend.R;
import com.example.frontend.model.MainApplication;
import com.example.frontend.model.profile.Profile;
import com.example.frontend.model.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.json.JSONObject;
import org.json.JSONArray;

public class CreateProfileActivity extends AppCompatActivity {


    private TextInputLayout tagLine;
    private TextInputLayout bio;
    private Activity returnClass;
    private String sImage;
    private RequestQueue queue;
    private String ImageID;

    /**
     * onCreate constructor for Android Studio, loads the default screen for the CreateProfileActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        tagLine = findViewById(R.id.CreateProfile_Tagline);
        bio = findViewById(R.id.CreateProfile_Bio);


        if(getIntent().hasExtra("returnClass")){
            Intent i =  getIntent();
            Bundle b = i.getExtras();
            String className = b.getString("returnClass");
            try {
                Class newClass = Class.forName(className);
                returnClass = (Activity) newClass.newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }


;
        Button gallery = findViewById(R.id.CreateProfile_PicButton);
        gallery.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick listener for the picture selection button
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }


        });

        Button create = findViewById(R.id.CreateProfile_SubmitButton);
        create.setOnClickListener(new View.OnClickListener() {
            /**
             * onCLick listener for the profile creation button, collects the users inputted values for bio, tagline, and username
             * @param view
             */
            @Override
            public void onClick(View view) {
                String Bio = bio.getEditText().getText().toString();
                String Tagline = tagLine.getEditText().getText().toString();
                User userObj = MainApplication.getActiveUser();
                Log.d("Bio", Bio);
                Log.d("Tagline", Tagline);
                Log.d("User", userObj.toString());
                uploadImage();


                //Intent intent = new Intent(getApplicationContext(), ActorsProfilePageActivity.class);
                //startActivity(intent);
            }
        });
    }

    private void uploadImage() {
        queue = Volley.newRequestQueue(this);
        String url =  "https://api.imgur.com/3/upload/";
        StringRequest uploadRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "finished image upload");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ImageID =  jsonObject.getJSONObject("data").getString("id");
                    Log.d("Image ID", ImageID);
                    String Bio = bio.getEditText().getText().toString();
                    String Tagline = tagLine.getEditText().getText().toString();
                    User userObj = MainApplication.getActiveUser();
                    uploadProfile(new Profile(userObj, Tagline, Bio, ImageID));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.toString());
                Log.e("TAG","finish/error upload");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Client-ID " + "98ae34e6474398e");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("image", sImage);
                return params;
            }
        };
       uploadRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(uploadRequest);
    }

    /**
     * Function that stores the results of the gallery view
     * @param requestCode Request code from the gallery function
     * @param resultCode Result code from the operation
     * @param data Picture data that has been uploaded
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] bytes = stream.toByteArray();
                sImage = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
                ImageView profile = findViewById(R.id.CreateProfile_Image);
                byte[] b = android.util.Base64.decode(sImage, android.util.Base64.DEFAULT);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(b, 0, b.length);
                profile.setImageBitmap(bitmap1);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * Uploads the users profile to the database based on the information collected.
     * @param profile profile to be uploaded
     */
    public void uploadProfile(Profile profile){ //todo Map username from login
        String postURL = MainApplication.baseUrl + "profiles";
        //String postURL = "https://reqres.in/api/users";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();

        ObjectMapper mapper = new ObjectMapper();
        try {
            postData = new JSONObject(mapper.writeValueAsString(profile));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postURL, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
               if(returnClass != null) {
                    Intent intent = new Intent(getApplicationContext(), returnClass.getClass());
                    startActivity(intent);
                }

                finish();
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