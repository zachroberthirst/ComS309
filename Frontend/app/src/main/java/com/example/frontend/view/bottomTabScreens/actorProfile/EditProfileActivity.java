package com.example.frontend.view.bottomTabScreens.actorProfile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.frontend.R;
import com.example.frontend.model.MainApplication;
import com.example.frontend.model.profile.Profile;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.model.user.User;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.presenter.actorProfile.ActorEditProfilePresenter;
import com.example.frontend.presenter.actorProfile.IActorEditProfilePresenter;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class EditProfileActivity extends AppCompatActivity implements IVolleyListener, IActorEditProfileView {

    private TextInputLayout Tagline;
    private TextInputLayout Bio;
    private IActorEditProfilePresenter presenter;
    private Profile currentProfile;
    private Activity returnClass;
    private String sImage;
    private RequestQueue queue;
    private String ImageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Button update = findViewById(R.id.EditProfile_SaveButton);
        Button picture = findViewById(R.id.EditProfile_ImageUpload);
        Tagline = findViewById(R.id.EditProfile_Tagline);
        Bio = findViewById(R.id.EditProfile_Bio);

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


        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        IServerRequest serverRequest = new ServerRequest();
        presenter = new ActorEditProfilePresenter(this, serverRequest);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentProfile != null) {
                    uploadImage();
                }
            }
        });
        presenter.getUserProfile();
    }
    private void updateProfile(String TagLine, String Biography, String imageID) {
        Profile p = new Profile();
        p.setId(currentProfile.getId());
        p.setUser(currentProfile.getUser());
        p.setBiography(Biography);
        p.setTagline(TagLine);
        p.setImage(imageID);
        presenter.updateProfile(p);
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {

    }

    @Override
    public void onErrorJson(String tag, String response) {

    }

    @Override
    public void onSuccessString(String tag, String response) {

    }

    @Override
    public void onErrorString(String tag, String response) {

    }

    @Override
    public void onSuccessArray(String tag, JSONArray response) {

    }

    @Override
    public void onErrorArray(String tag, String response) {

    }

    @Override
    public void gotProfile(Profile profile) {
        currentProfile = profile;
        Tagline.getEditText().setText(profile.getTagline());
        Bio.getEditText().setText(profile.getBiography());
    }

    @Override
    public void close() {
        if(returnClass != null) {
            Intent intent = new Intent(getApplicationContext(), returnClass.getClass());
            startActivity(intent);
        }
        finish();
    }

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
                ImageView profile = findViewById(R.id.EditProfile_Image);
                byte[] b = android.util.Base64.decode(sImage, android.util.Base64.DEFAULT);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(b, 0, b.length);
                profile.setImageBitmap(bitmap1);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
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
                    String TagLine = Tagline.getEditText().getText().toString().trim();
                    String Biography = Bio.getEditText().getText().toString().trim();
                    updateProfile(TagLine, Biography, ImageID);

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
}
