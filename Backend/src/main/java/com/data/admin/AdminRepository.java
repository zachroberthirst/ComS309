package com.data.admin;

import com.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AdminRepository extends JpaRepository<User, Long> {

    User findById(int id);
    User findByUsername(String username);

    @Transactional
    void deleteById(int id);
}
