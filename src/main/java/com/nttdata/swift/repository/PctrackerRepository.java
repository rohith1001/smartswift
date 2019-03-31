package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Pctracker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pctracker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PctrackerRepository extends JpaRepository<Pctracker, Long> {

}
