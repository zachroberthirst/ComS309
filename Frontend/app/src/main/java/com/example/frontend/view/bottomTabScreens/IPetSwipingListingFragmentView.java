package com.example.frontend.view.bottomTabScreens;

import com.example.frontend.model.listing.PetType;

import java.util.ArrayList;
import java.util.List;

public interface IPetSwipingListingFragmentView {
    void setName(String name);
    void setAge(int age);
    void setType(String type);
    void setDescription(String description);
    void setImage(ArrayList<String> urls);
}
