package com.data.profile;

import com.data.setting.Setting;
import com.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

   Profile findById(int id);
   Profile findByUser_Username(String username);

   @Transactional
   void deleteById(int id);
}
