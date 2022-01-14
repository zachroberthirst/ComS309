package com.data.listing;

import com.data.match.Match;
import com.data.match.MatchRepository;
import com.data.shelter.Shelter;
import com.data.shelter.ShelterRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class ListingController {

    @Autowired
    ListingRepository listingRepository;
    @Autowired
    ShelterRepository shelterRepository;
    @Autowired
    MatchRepository matchRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @PutMapping("/listing/{id}")
    Listing updateListing(@PathVariable int id, @RequestBody Listing request)
    {
        Listing listing = listingRepository.findById(id);

        if(listing == null)
        {
            return null;
        }

        listingRepository.save(request);

        return listingRepository.findById(id);
    }
    @GetMapping("/listing")
    List<Listing> getAllListings(){
        return listingRepository.findAll();
    }

    @PostMapping("/listing")
    String createListing(@RequestBody Listing listing)
    {
        if(listing == null)
        {
            return failure;
        }

        listingRepository.save(listing);

        return success;
    }

    @GetMapping("/listing/{id}")
    Listing getListingById(@PathVariable int id) {return listingRepository.findById(id); }

    @GetMapping("/listing/shelter/username/{username}")
    List<Listing> getListingsByShelterUsername(@PathVariable String username) {return listingRepository.findAllByShelter_User_Username(username); }

    @PutMapping("/listing/shelter/filters/{num}")
    List<Listing> getListingsByFilters(@PathVariable int num, @RequestBody List<PetType> petTypes) {
        List<Listing> out = new ArrayList<>();
        Random r = new Random();
        int numPerType = num / petTypes.size();
        for (PetType t: petTypes) {
            List<Listing> l = listingRepository.findListingsByPetTypeOrderById(t);
            for(int i = 0; i< numPerType; i++){
                if(!l.isEmpty()){
                    out.add(l.get(r.nextInt(l.size())));
                }
            }
        }
        return out;
    }

    @PutMapping("/listing/{listingId}/shelter/{ShelterId}")
    String assignListingToShelter(@PathVariable int listingId, @PathVariable int ShelterId)
    {
        Listing listing = listingRepository.findById(listingId);
        Shelter shelter = shelterRepository.findById(ShelterId);

        if(listing == null || shelter == null) {return failure;}

        listing.setShelter(shelter);
        listingRepository.save(listing);
        //shelter.setListings(listing);

        return success;
    }


    @DeleteMapping("/listing/{id}")
    String deleteListing(@PathVariable int id)
    {
        List<Match> matches = matchRepository.findAllByListing_IdOrderByTimestampDesc(id);
        for (Match m:matches) {
            matchRepository.deleteById(m.getId());
        }

        listingRepository.deleteById(id);

        return success;
    }

}
