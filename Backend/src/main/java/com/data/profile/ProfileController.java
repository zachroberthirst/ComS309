package com.data.profile;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProfileController {

    @Autowired
    ProfileRepository profileRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    @GetMapping(path = "/profiles")
    List<Profile> getAllProfiles(){
        return profileRepository.findAll();
    }

    @GetMapping(path = "/profiles/{id}")
    Profile getProfileByID(@PathVariable int id){
        return profileRepository.findById(id);
    }

    @GetMapping(path = "/profiles/username/{username}")
    Profile getProfileByUsername(@PathVariable String username){
        return profileRepository.findByUser_Username(username);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/profiles")
    String createProfile(@RequestBody Profile profile){
        //System.out.println(body);
        if (profile == null) {
            return failure;
        }
        if(profileRepository.findByUser_Username(profile.getUser().getUsername()) != null){
            return failure;
        }
        profileRepository.save(profile);
        return success;
    }
    @RequestMapping(method = RequestMethod.PUT, path = "/profiles")
    Profile updateProfile(@RequestBody Profile profile){
        //System.out.println(profile);
        if (profile == null) {
            return null;
        }
        return profileRepository.saveAndFlush(profile);
    }

    @DeleteMapping(path = "/profiles/{id}")
    String getDeleteMatch(@PathVariable int id){
        profileRepository.deleteById(id);
        return success;
    }
}
