package com.data.listing;


import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {


    Listing findById(int id);
    List<Listing> findAllByShelter_User_Username(String username);
    List<Listing> findListingsByPetTypeOrderById(PetType type);
    @Transactional
    void deleteById(int id);
}
