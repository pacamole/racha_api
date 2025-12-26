package com.muller.racha_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muller.racha_api.model.RachaParticipant;

@Repository
public interface RachaParticipantRepository extends JpaRepository<RachaParticipant, UUID> {

}
