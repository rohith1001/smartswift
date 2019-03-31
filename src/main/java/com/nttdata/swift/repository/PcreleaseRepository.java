package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Pcrelease;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pcrelease entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PcreleaseRepository extends JpaRepository<Pcrelease, Long> {

}
