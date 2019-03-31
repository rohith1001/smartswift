package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Pcincident;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pcincident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PcincidentRepository extends JpaRepository<Pcincident, Long> {

}
