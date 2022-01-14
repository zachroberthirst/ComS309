package com.data.match;

import com.data.listing.Listing;
import com.data.listing.ListingRepository;
import com.data.user.User;
import com.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatchController {

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ListingRepository listingRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @RequestMapping(method = RequestMethod.GET, path = "/matches")
    List<Match> getAllMatches(){
        return matchRepository.findAll();
    }
    @RequestMapping(method = RequestMethod.GET, path = "/matches/user/{username}")
    List<Match> getUserMatchesByUsername(@PathVariable String username){
        return matchRepository.findAllByUser_UsernameOrderByTimestampDesc(username);
    }
    @RequestMapping(method = RequestMethod.GET, path = "/matches/shelter/{username}")
    List<Match> getListingMatchesByUsername(@PathVariable String username){
        return matchRepository.findAllByListing_Shelter_User_UsernameOrderByTimestampDesc(username);
        //return matchRepository.findAllByShelter_User_UsernameOrderByTimestampDesc(username); -- use when we have Shelter Class
    }
    @RequestMapping(method = RequestMethod.GET, path = "/matches/listing/{id}")
    List<Match> getListingMatchesById(@PathVariable int id){
        return matchRepository.findAllByListing_IdOrderByTimestampDesc(id);
        //return matchRepository.findAllByShelter_User_UsernameOrderByTimestampDesc(username); -- use when we have Shelter Class
    }
    @RequestMapping(method = RequestMethod.GET, path = "/matches/{id}")
    Match getMatch(@PathVariable int id){
        return matchRepository.findById(id);
    }
    @RequestMapping(method = RequestMethod.POST, path = "/matches")
    Match updateMatch(@RequestBody Match match){
        if (match == null) {
            return null;
        }
        User u = userRepository.findById(match.getUser().getId());
        Listing l = listingRepository.findById(match.getListing().getId());
        if(u != null && l != null){
            return matchRepository.saveAndFlush(match);
        }

        return null;
    }
    @RequestMapping(method = RequestMethod.PUT, path = "/matches/{user_id}/{listing_id}/match/{match_id}")
    String assignMatchToUsers(@PathVariable int user_id, @PathVariable int listing_id, @PathVariable int match_id){
        User user = userRepository.findById(user_id);
        Listing listing = listingRepository.findById(listing_id);
        Match match = matchRepository.findById(match_id);
        if(user == null || listing == null || match == null){
            return failure;
        }
        //update match
        match.setUser(user);
        match.setListing(listing);
        matchRepository.saveAndFlush(match);
        return success;
    }
    @DeleteMapping(path = "/matches/{id}")
    String getDeleteMatch(@PathVariable int id){
        matchRepository.deleteById(id);
        return success;
    }

}
