package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Bulkrelease;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bulkrelease entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BulkreleaseRepository extends JpaRepository<Bulkrelease, Long> {

}
