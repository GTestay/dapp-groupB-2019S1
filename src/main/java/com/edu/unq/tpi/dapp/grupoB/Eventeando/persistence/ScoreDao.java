package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreDao extends JpaRepository<Score, Long> {
    @Query(value = "select sum(rank) from Score where event.id=:eventId")
    Optional<Integer> eventScore(@Param("eventId") Long eventId);

    Optional<Score> findAllByEvent_IdAndAndUser_Id(@Param("eventId") Long eventId, @Param("userId") Long userId);
}
