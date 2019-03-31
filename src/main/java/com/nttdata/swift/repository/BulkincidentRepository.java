package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Bulkincident;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bulkincident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BulkincidentRepository extends JpaRepository<Bulkincident, Long> {

}
