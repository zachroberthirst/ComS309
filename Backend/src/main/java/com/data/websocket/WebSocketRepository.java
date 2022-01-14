package com.data.websocket;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface WebSocketRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByMatchIdOrderBySentAsc(int matchId);
    Message findById(int id);

    @Transactional
    void deleteById(int id);
    @Transactional
    void deleteAllByMatchId(int matchId);
}
