package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Severity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Severity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeverityRepository extends JpaRepository<Severity, Long> {

}
