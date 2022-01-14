package com.data.shelter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShelterController {
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    ShelterRepository shelterRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/shelters")
    Shelter createShelter(@RequestBody Shelter shelter){
        //System.out.println(user);
        if (shelter == null) {
            return null;
        }
        shelterRepository.saveAndFlush(shelter);
        return shelterRepository.findByUser_Username(shelter.getUser().getUsername());
    }
    @GetMapping(path = "/shelters")
    List<Shelter> getAllShelters(){
        return shelterRepository.findAll();
    }

    @GetMapping(path = "/shelters/{id}")
    Shelter getShelterById(@PathVariable int id){
        return shelterRepository.findById(id);
    }
    @GetMapping(path = "/shelters/username/{username}")
    Shelter getShelterByUsername(@PathVariable String username){
        Shelter s =shelterRepository.findByUser_Username(username);
        return s;
    }
    @PutMapping("/shelters/{id}")
    Shelter updateShelter(@PathVariable int id, @RequestBody Shelter request){
        Shelter shelter = shelterRepository.findById(id);
        if(shelter == null) {
            return null;
        }
        shelterRepository.saveAndFlush(request);
        return shelterRepository.findById(id);
    }
    @DeleteMapping(path = "/shelters/{id}")
    String deleteUser(@PathVariable int id){
        shelterRepository.deleteById(id);
        return success;
    }
}
