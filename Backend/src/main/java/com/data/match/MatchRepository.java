package com.data.match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Match findById(int id);
    List<Match> findAllByUser_UsernameOrderByTimestampDesc(String username);
    List<Match> findAllByListing_Shelter_User_UsernameOrderByTimestampDesc(String username);
    List<Match> findAllByListing_IdOrderByTimestampDesc(int id);

    @Transactional
    void deleteById(int id);
}