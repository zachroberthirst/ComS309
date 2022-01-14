package com.data.admin;

import com.data.user.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/admins")
    List<User> getAllAdmins(){
        return adminRepository.findAll();
    }

    @GetMapping(path = "/admins/username/{username}")
    String getAdminByUsername(@PathVariable String username){
        JSONObject newObj = new JSONObject();
        User u = adminRepository.findByUsername(username);
        try {
            newObj.put("id",u.getId());
            newObj.put("firstName",u.getFirstName());
            newObj.put("lastName",u.getLastName());
            newObj.put("birthday",u.getBirthday());
            newObj.put("email",u.getEmail());
            newObj.put("username",u.getUsername());
            newObj.put("password",u.getPassword());
            newObj.put("salt",u.getSalt());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newObj.toString();
    }

    @PutMapping("/admins/{id}")
    User updateAdmin(@PathVariable int id, @RequestBody User request){
        User user = adminRepository.findById(id);
        if(user == null) {
            return null;
        }
        adminRepository.save(request);
        return adminRepository.findById(id);
    }

    @DeleteMapping(path = "/admins/{id}")
    String deleteAdmin(@PathVariable int id){
        adminRepository.deleteById(id);
        return success;
    }
}
