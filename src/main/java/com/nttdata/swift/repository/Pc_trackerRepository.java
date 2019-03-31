package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Pc_tracker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pc_tracker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Pc_trackerRepository extends JpaRepository<Pc_tracker, Long> {

}
