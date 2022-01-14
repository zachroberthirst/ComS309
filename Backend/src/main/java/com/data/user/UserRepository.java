package com.data.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(int id);
    User findByUsername(String username);
    Boolean existsUsersByUsername(String username);

    @Transactional
    void deleteById(int id);

}
