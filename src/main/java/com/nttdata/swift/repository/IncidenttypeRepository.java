package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Incidenttype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Incidenttype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidenttypeRepository extends JpaRepository<Incidenttype, Long> {

}
