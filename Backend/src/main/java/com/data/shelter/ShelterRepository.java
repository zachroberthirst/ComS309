package com.data.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    Shelter findById(int Id);
    Shelter findByUser_Username(String username);

    @Transactional
    void deleteById(int id);
}
