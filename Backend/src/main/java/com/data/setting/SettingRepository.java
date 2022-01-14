package com.data.setting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SettingRepository extends JpaRepository<Setting, Long> {

   Setting findById(int id);
   Setting findByUser_Username(String username);
   @Transactional
   void deleteById(int id);
}
