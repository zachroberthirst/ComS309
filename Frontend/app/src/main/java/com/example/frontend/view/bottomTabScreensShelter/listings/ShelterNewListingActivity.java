package com.example.frontend.view.bottomTabScreensShelter.listings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.listing.ListingController;
import com.example.frontend.model.listing.PetType;
import com.example.frontend.model.profile.Profile;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.model.shelter.Shelter;
import com.example.frontend.model.user.User;
import com.example.frontend.presenter.shelterListings.IShelterAddListingPresenter;
import com.example.frontend.presenter.shelterListings.ShelterAddListingPresenter;
import com.example.frontend.view.bottomTabScreensShelter.ShelterTabSwitchingActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShelterNewListingActivity extends ShelterTabSwitchingActivity implements AdapterView.OnItemSelectedListener, IShelterNewListingVIew {

    private Spinner spinner;
    private TextInputLayout petName;
    private TextInputLayout petAge;
    private TextInputLayout petDescription;
    private String Text;
    private IShelterAddListingPresenter presenter;
    private String sImage;
    private RequestQueue queue;
    private String ImageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_add_listings_page);
        setupBottomTab();
        spinner = findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pet_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        petName =  findViewById(R.id.add_listing_petname);
        petAge = findViewById(R.id.add_listing_petage);
        petDescription = findViewById(R.id.add_listing_petdescription);

        IServerRequest serverRequest = new ServerRequest();
        presenter = new ShelterAddListingPresenter(this, serverRequest);

        Button picture = findViewById(R.id.add_listing_uploadpic);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        Button add = findViewById(R.id.add_listing_submitbtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean canPublish = true;
                Listing l = new Listing();
                l.setPetName(petName.getEditText().getText().toString().trim());

                String ageText = petAge.getEditText().getText().toString().trim();
                if(ageText.equals("")){
                    canPublish = false;
                }else{
                    l.setPetAge(Integer.parseInt(ageText));
                }
                l.setDescription(petDescription.getEditText().getText().toString().trim());
                Shelter s = MainApplication.getActiveShelter();
                l.setShelter(s);
                if(Text.equals("DOG")){
                    l.setPetType(PetType.DOG);
                }
                if(Text.equals("CAT")){
                    l.setPetType(PetType.CAT);
                }
                if(Text.equals("BIRD")){
                    l.setPetType(PetType.BIRD);
                }
                if(Text.equals("FISH")){
                    l.setPetType(PetType.FISH);
                }
                if(Text.equals("FERRET")){
                    l.setPetType(PetType.FERRET);
                }
                if(Text.equals("REPTILE")){
                    l.setPetType(PetType.REPTILE);
                }
                if(Text.equals("RABBIT")){
                    l.setPetType(PetType.RABBIT);
                }
                List<String> Image = new ArrayList<>();
                Image.add(ImageID);
                if(Image.get(0) != null){
                    Log.d("test", Image.get(0));
                }
                l.setPictureURL(Image);
                if(canPublish && !(l.getPetName().equals(""))) {
                    presenter.uploadListing(l);
                }
            }
        });
    }

    private void setupBottomTab(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        setNavSwitcher(bottomNavigationView);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Text = spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void success() {
        String success = "Posted";
        Toast toast = Toast.makeText(this, success, Toast.LENGTH_SHORT);
        toast.show();
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
                ImageView profile = findViewById(R.id.add_listing_image);
                byte[] b = android.util.Base64.decode(sImage, android.util.Base64.DEFAULT);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(b, 0, b.length);
                profile.setImageBitmap(bitmap1);
                uploadImage();
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
