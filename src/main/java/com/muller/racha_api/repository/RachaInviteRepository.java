package com.muller.racha_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muller.racha_api.model.RachaInvite;

@Repository
public interface RachaInviteRepository extends JpaRepository<RachaInvite, UUID> {

}
