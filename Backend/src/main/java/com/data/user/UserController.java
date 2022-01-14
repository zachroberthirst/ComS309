package com.data.user;

import com.data.match.Match;
import com.data.match.MatchRepository;
import com.data.profile.Profile;
import com.data.profile.ProfileRepository;
import com.data.setting.Setting;
import com.data.setting.SettingRepository;
import com.data.shelter.Shelter;
import com.data.shelter.ShelterRepository;
import com.data.websocket.WebSocketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class
UserController {

    @Autowired
    UserRepository userRepository;
    SettingRepository settingRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ShelterRepository shelterRepository;
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    WebSocketRepository webSocketRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/welcome")
    @ResponseBody
    public String welcome()
    {
        return "Welcome Test!";
    }

    @GetMapping(path = "/users/create/dummy")
    public String createDummyData() {
       User u1 = new User("Zach", "Hirst", LocalDate.of(2000, 11, 25).toString(), "zach_hirst@live.com", "zrhirst", "asidbug", "aisdbuv");
       User u2 = new User("Ian", "J", LocalDate.of(2004, 5, 9).toString(), "urmom@live.com", "hello", "akijsdvh", "aiasdfvbkv");
       userRepository.save(u1);
       userRepository.save(u2);
        return "Successfully created dummy data";
    }

    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id){
        return userRepository.findById(id);
    }

    @GetMapping(path = "/users/username/{username}")
    User getUserByUsername(@PathVariable String username){
        User u =userRepository.findByUsername(username);
        return u;
    }

    @GetMapping(path = "/users/username/has/{username}")
    Boolean getUserByUsernameExists(@PathVariable String username){
        return userRepository.existsUsersByUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/users")
    User createUser(@RequestBody User user){
        //System.out.println(user);
        if (user == null) {
            return null;
        }
        userRepository.saveAndFlush(user);
        return userRepository.findByUsername(user.getUsername());
    }

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable int id, @RequestBody User request){
        User user = userRepository.findById(id);
        if(user == null) {
            return null;
        }
        userRepository.saveAndFlush(request);
        return userRepository.findById(id);
    }

    @PutMapping("/users/{userId}/setting/{settingId}")
    String assignSettingToUser(@PathVariable int userId,@PathVariable int settingId) {
        User user = userRepository.findById(userId);
        Setting setting = settingRepository.findById(settingId);

        if(user == null || setting == null) {return failure;}
        //setting.setUser(user);
        user.setSetting(setting);

        userRepository.saveAndFlush(user);

        return success;
    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id){

        User u = userRepository.findById(id);
        Profile p = profileRepository.findByUser_Username(u.getUsername());
        if(p != null){
            profileRepository.deleteById(p.getId());
        }

        if(u.getType() == UserType.SHELTER){
            Shelter s = shelterRepository.findByUser_Username(u.getUsername());
            if(s != null){
                shelterRepository.deleteById(s.getId());
            }
        }
        if(!(u.getMatches().size() < 1)){
            for (Match m: u.getMatches()) {
                webSocketRepository.deleteAllByMatchId(m.getId());
                matchRepository.deleteById(m.getId());
            }

        }
        userRepository.deleteById(id);
        return success;
    }
}

