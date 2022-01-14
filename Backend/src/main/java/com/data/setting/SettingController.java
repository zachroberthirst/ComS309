package com.data.setting;

import com.data.shelter.Shelter;
import com.data.user.User;
import com.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SettingController {

    @Autowired
    SettingRepository settingRepository;
    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @PutMapping("/setting")
    Setting updateSetting(@RequestBody Setting setting){
        if(setting == null) {return null;}
        Setting s = settingRepository.findById(setting.getId());
        User u = userRepository.findById(s.getUser().getId());
        u.setSetting(setting);
        userRepository.saveAndFlush(u);
        return settingRepository.saveAndFlush(setting);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/setting")
    Setting createSetting(@RequestBody Setting setting){
        if (setting == null) {
            return null;
        }
        Setting test = settingRepository.findByUser_Username(setting.getUser().getUsername());
        if(test == null) {
            Setting s = settingRepository.saveAndFlush(setting);
            User u = userRepository.findById(s.getUser().getId());
            u.setSetting(setting);
            userRepository.saveAndFlush(u);
            return s;
        }
        return test;
    }

    @GetMapping(path = "/setting/{id}")
    Setting getSettingsById(@PathVariable int id){
        return settingRepository.findById(id);
    }

    @GetMapping(path = "/setting/username/{username}")
    Setting getSettingsByUsername(@PathVariable String username){
        return settingRepository.findByUser_Username(username);
    }

    @DeleteMapping(path = "/setting/{id}")
    String deleteSettingById(@PathVariable int id){
        Setting s = settingRepository.findById(id);
        if(s != null) {
            User u = userRepository.findById(s.getUser().getId());
            u.setSetting(null);
            userRepository.saveAndFlush(u);

            settingRepository.deleteById(id);
            return success;
        }else {
            return failure;
        }
    }
}
